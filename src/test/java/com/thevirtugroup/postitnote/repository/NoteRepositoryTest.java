package com.thevirtugroup.postitnote.repository;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.within;

import com.thevirtugroup.postitnote.exception.NotFoundException;
import com.thevirtugroup.postitnote.model.Note;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.Test;

public class NoteRepositoryTest {

    private final NoteRepository noteRepository = new NoteRepository();

    @Test
    public void shouldCreateNote() {
        // Given
        Note note = new Note("name", "text");

        // When
        Note created = noteRepository.createNote(1, note);

        // Then
        assertThat(created)
                .usingRecursiveComparison(
                        RecursiveComparisonConfiguration.builder()
                                .withIgnoredFields("id", "createdTimestamp")
                                .build())
                .isEqualTo(note);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getCreatedTimestamp()).isCloseTo(now(), within(1, SECONDS));
    }

    @Test
    public void shouldGetNotes() {
        // Given
        Note note1 = noteRepository.createNote(1, new Note("name1", "text1"));
        Note note2 = noteRepository.createNote(1, new Note("name2", "text2"));
        Note note3 = noteRepository.createNote(2, new Note("name3", "text3"));

        // When
        Map<UUID, Note> user1Notes = noteRepository.getNotes(1);
        Map<UUID, Note> user2Notes = noteRepository.getNotes(2);
        Map<UUID, Note> user3Notes = noteRepository.getNotes(3);

        // Then
        assertThat(user1Notes.values()).containsExactly(note1, note2);
        assertThat(user2Notes.values()).containsExactly(note3);
        assertThat(user3Notes.values()).isEmpty();
    }

    @Test
    public void shouldUpdateNote() {
        // Given
        Note created = noteRepository.createNote(1, new Note("name1", "text1"));

        // When
        Note updated = noteRepository.updateNote(1, created.getId(), new Note("name2", "text2"));

        // Then
        assertThat(updated)
                .usingRecursiveComparison(
                        RecursiveComparisonConfiguration.builder()
                                .withIgnoredFields("updatedTimestamp")
                                .build())
                .isEqualTo(created);

        Instant now = now();
        assertThat(created.getCreatedTimestamp()).isCloseTo(now, within(1, SECONDS));
        assertThat(updated.getUpdatedTimestamp()).isCloseTo(now, within(1, SECONDS));
        assertThat(updated.getUpdatedTimestamp()).isAfter(updated.getCreatedTimestamp());

        Map<UUID, Note> notes = noteRepository.getNotes(1);
        assertThat(notes.values()).containsExactly(updated);
    }

    @Test
    public void shouldNotUpdateNoteWhenUserHasNoNotes() {
        // Given
        UUID noteId = randomUUID();

        // When
        Throwable thrown = catchThrowable(() -> noteRepository.updateNote(1, noteId, new Note()));

        // Then
        assertThat(thrown)
                .isExactlyInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("type", Note.class)
                .hasFieldOrPropertyWithValue("id", noteId);

        assertThat(noteRepository.getNotes(1)).isEmpty();
    }

    @Test
    public void shouldNotUpdateNoteWhenNotOwnedByUser() {
        // Given
        Note note1 = noteRepository.createNote(1, new Note("name1", "text1"));
        Note note2 = noteRepository.createNote(2, new Note("name2", "text2"));

        // When
        Throwable thrown = catchThrowable(() -> noteRepository.updateNote(2, note1.getId(), new Note()));

        // Then
        assertThat(thrown)
                .isExactlyInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("type", Note.class)
                .hasFieldOrPropertyWithValue("id", note1.getId());

        assertThat(noteRepository.getNotes(1).values()).containsExactly(note1);
        assertThat(noteRepository.getNotes(2).values()).containsExactly(note2);
    }

}