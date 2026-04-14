package com.example.demo.controller;

import com.example.demo.service.UrlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public String shorten(@RequestBody String longUrl) {
        String code = service.shortenUrl(longUrl);
        return "http://localhost:8085/" + code;
    }

    // ✅ FIXED HERE
    @GetMapping("/{code:[a-zA-Z0-9]{6}}")
    public ResponseEntity<?> redirect(@PathVariable String code) {

        String longUrl = service.getLongUrl(code);

        if (longUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(302)
                .header("Location", longUrl)
                .build();
    }
}