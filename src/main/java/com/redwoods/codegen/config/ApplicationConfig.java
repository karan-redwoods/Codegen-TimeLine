package com.redwoods.codegen.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
    @Value("${application.packageName}")
    private String packageName;

    @Value("${application.service}")
    private String service;

    @Value("${application.serviceImpl}")
    private String serviceImpl;

    @Value("${application.repository}")
    private String repository;

    @Value("${application.responseDto}")
    private String responseDto;

    @Value("${application.responseDto}")
    private String requestDto;

    @Value("${application.base_path}")
    private String base_path;
}
