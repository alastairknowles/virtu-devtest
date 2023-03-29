package com.thevirtugroup.postitnote.config;

import static org.mockito.Mockito.mock;

import com.thevirtugroup.postitnote.service.NoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public NoteService noteService() {
        return mock(NoteService.class);
    }

}