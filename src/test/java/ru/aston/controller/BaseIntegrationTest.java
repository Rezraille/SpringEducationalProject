package ru.aston.controller;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EntityScan("ru.aston.entity")
@AutoConfigureMockMvc
@DirtiesContext
@Testcontainers


public class BaseIntegrationTest {

    @Container
    protected static PostgreSQLContainer postgreContainer = new PostgreSQLContainer("postgres:13");

    protected static RestTemplate restTemplate;

    @LocalServerPort
    protected Integer port;


    @DynamicPropertySource
    static void registerDynamicProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreContainer::getUsername);
        registry.add("spring.datasource.password", postgreContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreContainer::getDriverClassName);
        registry.add("spring.jpa.properties.hibernate.flushMode", () -> "MANUAL");

        registry.add("spring.jpa.properties.hibernate.hbm2ddl.auto", () -> "update");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.use_sql_comments", () -> "true");
        registry.add("logging.level.org.hibernate.type.descriptor.sql", () -> "TRACE");
    }

    @BeforeAll
    protected static void setup() {
        restTemplate = new RestTemplate();
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.setRequestFactory(requestFactory);
    }

    @AfterAll
    protected static void tearDown() {
        postgreContainer.stop();
    }
}
