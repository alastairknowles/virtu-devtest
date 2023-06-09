package com.thevirtugroup.postitnote.service;

import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_ID;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @PostConstruct
    public void populateDemoNotes() {
        noteRepository.createNote(DEFAULT_USER_ID, new Note("Note 1"));
        noteRepository.createNote(DEFAULT_USER_ID, new Note("Note 2"));
    }

    public Note createNote(long userId, Note note) {
        return noteRepository.createNote(userId, note);
    }

    public Notes getNotes(long userId) {
        List<Note> items = new ArrayList<>();
        new LinkedList<>(noteRepository.getNotes(userId).values())
                .descendingIterator()
                .forEachRemaining(items::add);

        return new Notes(items);
    }

    public Note updateNote(long userId, UUID noteId, Note update) {
        return noteRepository.updateNote(userId, noteId, update);
    }

}