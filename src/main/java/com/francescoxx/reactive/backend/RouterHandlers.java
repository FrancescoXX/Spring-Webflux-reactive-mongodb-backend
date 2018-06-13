package com.francescoxx.reactive.backend;

import com.francescoxx.reactive.backend.player.Player;
import com.francescoxx.reactive.backend.player.PlayerEvent;
import com.francescoxx.reactive.backend.player.PlayerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class RouterHandlers {

    private PlayerRepository playerRepository;

    public RouterHandlers(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(playerRepository.findAll(), Player.class);
    }

    public Mono<ServerResponse> getId(ServerRequest serverRequest) {
        String playerId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .body(playerRepository.findById(playerId), Player.class);
    }

    public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
        String playerId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(playerRepository.findById(playerId)
                        .flatMapMany(player -> {
                            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                            Flux<PlayerEvent> playerEventFlux = Flux.fromStream(
                                    Stream.generate(() -> new PlayerEvent(player, new Date()))
                            );
                            return Flux.zip(interval, playerEventFlux)
                                    .map(Tuple2::getT2);
                        }), PlayerEvent.class
                );
    }
}
