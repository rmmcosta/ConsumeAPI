package com.rmmcosta.consumeanapi.controller;

import com.rmmcosta.consumeanapi.entity.Joke;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/jokes")
public class JokesController {
    @GetMapping
    public CompletableFuture<ResponseEntity<Joke>> getJoke() {
        CompletableFuture<ResponseEntity<Joke>> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(()->{
            WebClient client = WebClient.builder()
                    .baseUrl("https://official-joke-api.appspot.com")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build();
            Mono<Joke> response = client.get()
                    .uri("/random_joke")
                    .retrieve()
                    .bodyToMono(Joke.class);
            completableFuture.complete(new ResponseEntity<>(response.block(), HttpStatus.OK));
            return null;
        });
        return completableFuture;
    }
}
