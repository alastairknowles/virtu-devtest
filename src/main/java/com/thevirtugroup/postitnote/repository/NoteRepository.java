package com.thevirtugroup.postitnote.repository;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import com.thevirtugroup.postitnote.exception.NotFoundException;
import com.thevirtugroup.postitnote.model.Note;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository {

    private final Map<Long, Map<UUID, Note>> notes = new ConcurrentHashMap<>();

    public Note createNote(long userId, Note note) {
        UUID noteId = randomUUID();
        note.setId(noteId);
        note.setCreatedTimestamp(now());
        notes.putIfAbsent(userId, new LinkedHashMap<>());
        notes.get(userId).put(noteId, note);
        return note;
    }

    public Map<UUID, Note> getNotes(long userId) {
        return notes.getOrDefault(userId, new HashMap<>());
    }

    public Note updateNote(long userId, UUID noteId, Note update) {
        Map<UUID, Note> userNotes = notes.get(userId);
        if (userNotes == null) {
            throw new NotFoundException(Note.class, noteId);
        }

        return userNotes.compute(noteId, (key, value) -> {
            if (value == null) {
                throw new NotFoundException(Note.class, noteId);
            }

            value.setText(update.getText());
            value.setUpdatedTimestamp(now());
            return value;
        });
    }

}