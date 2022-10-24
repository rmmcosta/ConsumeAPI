package com.rmmcosta.consumeanapi.entity;

import lombok.Data;

@Data
public class Joke {
    private Long id;
    private String type;
    private String setup;
    private String punchline;
}
