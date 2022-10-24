package com.rmmcosta.consumeanapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    @GetMapping("{term}")
    public CompletableFuture<ResponseEntity<String>> getMovies(@PathVariable String term) {
        CompletableFuture<ResponseEntity<String>> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            WebClient client = WebClient.builder()
                    .baseUrl("https://i-m-d-b.herokuapp.com")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build();
            Mono<String> response = client.post()
                    .uri("/")
                    .body(BodyInserters.fromFormData("q", term))
                    .retrieve()
                    .bodyToMono(String.class);
            completableFuture.complete(new ResponseEntity<>(response.block(), HttpStatus.OK));
            return null;
        });
        return completableFuture;
    }
}
