package com.rmmcosta.consumeanapi.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IMDBSearch {
    private String q;
    private String tt;
    private String s;
    private String l;
}
