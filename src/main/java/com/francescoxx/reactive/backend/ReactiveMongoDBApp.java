package com.francescoxx.reactive.backend;

import com.francescoxx.reactive.backend.player.Player;
import com.francescoxx.reactive.backend.player.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMongoDBApp {

    @Bean
    CommandLineRunner players(PlayerRepository playerRepository) {

        return args -> playerRepository
                //Clear database
                .deleteAll()

                //Insert Mock records
                .subscribe(null, null, () -> Stream.of(
                        new Player(UUID.randomUUID().toString(), "AAA", 1200L, 100L, 10L),
                        new Player(UUID.randomUUID().toString(), "ABB", 1300L, 0L, 300L),
                        new Player(UUID.randomUUID().toString(), "FRA", 2000000L, 233L, 215125L),
                        new Player(UUID.randomUUID().toString(), "XXX", 100000L, 3213L, 10L))
                        .forEach(player -> playerRepository
                                .save(player)
                                .subscribe(System.out::println))
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMongoDBApp.class, args);
    }
}
