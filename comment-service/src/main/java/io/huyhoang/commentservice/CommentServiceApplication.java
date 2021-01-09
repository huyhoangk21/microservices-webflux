package io.huyhoang.commentservice;

import io.huyhoang.commentservice.entity.Comment;
import io.huyhoang.commentservice.repository.CommentRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@EnableR2dbcAuditing
public class CommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner demo(CommentRepository repository) {

        return args -> {
            repository.saveAll(Arrays.asList(new Comment(UUID.randomUUID(), UUID.randomUUID(), "hello"),
                    new Comment(UUID.randomUUID(), UUID.randomUUID(), "hello1"),
                    new Comment(UUID.randomUUID(), UUID.randomUUID(), "hello2"),
                    new Comment(UUID.randomUUID(), UUID.randomUUID(), "hello3"),
                    new Comment(UUID.randomUUID(), UUID.randomUUID(), "hello4")))
                    .blockLast(Duration.ofSeconds(10));
        };
    }


}
