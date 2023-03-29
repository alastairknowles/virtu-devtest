package com.thevirtugroup.postitnote;

import static java.lang.System.setProperty;
import static org.springframework.boot.SpringApplication.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.config.MvcConfig;
import com.thevirtugroup.postitnote.config.WebSecurityConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Import({MvcConfig.class, WebSecurityConfig.class})
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        setProperty("spring.devtools.restart.enabled", "true");
        run(Application.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}