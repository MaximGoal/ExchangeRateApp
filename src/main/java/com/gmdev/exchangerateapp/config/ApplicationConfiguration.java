package com.gmdev.exchangerateapp.config;

import com.gmdev.exchangerateapp.service.CBRParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CBRParser cbrParser () {return new CBRParser();}
}
