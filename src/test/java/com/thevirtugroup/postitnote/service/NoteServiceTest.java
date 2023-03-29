package com.thevirtugroup.postitnote.service;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import java.util.LinkedHashMap;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    @Before
    public void setUp() {
        noteService = new NoteService(noteRepository);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(noteRepository);
    }

    @Test
    public void shouldCreateNote() {
        // Given
        Note note = new Note();

        // When
        noteService.createNote(1, note);

        // Then
        verify(noteRepository).createNote(1, note);
    }

    @Test
    public void shouldGetNotes() {
        // Given
        Note note1 = new Note(randomUUID());
        Note note2 = new Note(randomUUID());

        LinkedHashMap<UUID, Note> notes = new LinkedHashMap<>();
        notes.put(note1.getId(), note1);
        notes.put(note2.getId(), note2);

        when(noteRepository.getNotes(1)).thenReturn(notes);

        // When
        Notes result = noteService.getNotes(1);

        // Then
        assertThat(result.getItems())
                .containsExactly(note2, note1);
        verify(noteRepository).getNotes(1);
    }

    @Test
    public void shouldUpdateNote() {
        // Given
        UUID noteId = randomUUID();
        Note update = new Note();

        // When
        noteService.updateNote(1, noteId, update);

        // Then
        verify(noteRepository).updateNote(1L, noteId, update);
    }

}