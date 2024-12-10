package org.example.handler;



import org.example.model.Greeting;
import org.example.repository.GreetingRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Component
public class GreetingHandler {

    private final GreetingRepository repository;

    public GreetingHandler(GreetingRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> createGreeting(ServerRequest request) {
        return request.bodyToMono(Greeting.class)
                .flatMap(repository::save)
                .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }

    public Mono<ServerResponse> getAllGreetings(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Greeting.class);
    }

    public Mono<ServerResponse> getGreetingById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(greeting -> ServerResponse.ok().bodyValue(greeting))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> streamRandomNumbers(ServerRequest request) {
        Random random = new Random();
        Flux<Integer> randomNumbers = Flux.interval(Duration.ofSeconds(1)) // Emite un dato cada segundo
                .map(tick -> random.nextInt(100)); // Genera un número aleatorio entre 0 y 99

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // Indica que la respuesta es un flujo SSE
                .body(randomNumbers, Integer.class); // Devuelve el flujo como cuerpo
    }


    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return repository.findById(id).
                flatMap(existingGreeting ->
                        repository.delete(existingGreeting).
                        then(ServerResponse.noContent().build())).
                switchIfEmpty(ServerResponse.notFound().build());
    }
}


