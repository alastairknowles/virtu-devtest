package com.thevirtugroup.postitnote.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.security.SecurityUserWrapper;
import com.thevirtugroup.postitnote.service.NoteService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(method = POST)
    public Note createNote(Authentication authentication, @RequestBody Note note) {
        SecurityUserWrapper user = (SecurityUserWrapper) authentication.getPrincipal();
        return noteService.createNote(user.getId(), note);
    }

    @RequestMapping(method = GET)
    public Notes getNotes(Authentication authentication) {
        SecurityUserWrapper user = (SecurityUserWrapper) authentication.getPrincipal();
        return noteService.getNotes(user.getId());
    }

    @RequestMapping(path = "/{noteId}", method = PUT)
    public Note updateNote(Authentication authentication, @PathVariable("noteId") UUID noteId, @RequestBody Note note) {
        SecurityUserWrapper user = (SecurityUserWrapper) authentication.getPrincipal();
        return noteService.updateNote(user.getId(), noteId, note);
    }

}