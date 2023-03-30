package com.thevirtugroup.postitnote.rest;

import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_ID;
import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_PASSWORD;
import static com.thevirtugroup.postitnote.Constants.DEFAULT_USER_USERNAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.Application;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.service.NoteService;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class NoteControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoteService noteService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc =
                webAppContextSetup(webApplicationContext)
                        .apply(springSecurity())
                        .build();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void shouldCreateNote() throws Exception {
        // Given
        Note note = new Note("test");

        // When
        ResultActions resultActions =
                mockMvc.perform(
                        post("/api/notes")
                                .with(httpBasic(DEFAULT_USER_USERNAME, DEFAULT_USER_PASSWORD))
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(note)));

        // Then
        resultActions.andExpect(status().isOk());
        verify(noteService).createNote(DEFAULT_USER_ID, note);
    }

    @Test
    public void shouldNotCreateNoteWhenNotAuthenticated() throws Exception {
        // Given
        Note note = new Note("test");

        // When
        ResultActions resultActions =
                mockMvc.perform(
                        post("/api/notes")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(note)));

        // Then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGetNotes() throws Exception {
        // Given
        Notes notes = new Notes(new Note("test"));
        when(noteService.getNotes(DEFAULT_USER_ID)).thenReturn(notes);

        // When
        ResultActions resultActions =
                mockMvc.perform(
                        get("/api/notes")
                                .with(httpBasic(DEFAULT_USER_USERNAME, DEFAULT_USER_PASSWORD)));

        // Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().string(objectMapper.writeValueAsString(notes)));
        verify(noteService).getNotes(DEFAULT_USER_ID);
    }

    @Test
    public void shouldNotGetNotesWhenNotAuthenticated() throws Exception {
        // When
        ResultActions resultActions =
                mockMvc.perform(
                        get("/api/notes"));

        // Then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldUpdateNote() throws Exception {
        // Given
        Note update = new Note("test");

        // When
        ResultActions resultActions =
                mockMvc.perform(
                        put("/api/notes/0e57460d-7187-4675-a270-e243e574f387")
                                .with(httpBasic(DEFAULT_USER_USERNAME, DEFAULT_USER_PASSWORD))
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(update)));

        // Then
        resultActions.andExpect(status().isOk());
        verify(noteService).updateNote(DEFAULT_USER_ID, UUID.fromString("0e57460d-7187-4675-a270-e243e574f387"), update);
    }

    @Test
    public void shouldUpdateNoteWhenNotAuthenticated() throws Exception {
        // Given
        Note update = new Note("test");

        // When
        ResultActions resultActions =
                mockMvc.perform(
                        put("/api/notes/0e57460d-7187-4675-a270-e243e574f387")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(update)));

        // Then
        resultActions.andExpect(status().isUnauthorized());
    }

}