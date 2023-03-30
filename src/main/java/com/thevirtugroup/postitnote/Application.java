package com.thevirtugroup.postitnote;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.lang.System.setProperty;
import static org.springframework.boot.SpringApplication.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thevirtugroup.postitnote.config.MvcConfig;
import com.thevirtugroup.postitnote.config.WebSecurityConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Import({MvcConfig.class, WebSecurityConfig.class})
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        setProperty("spring.devtools.restart.enabled", "true");
        run(Application.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}