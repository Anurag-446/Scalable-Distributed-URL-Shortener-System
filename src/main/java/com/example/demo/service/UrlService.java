package com.example.demo.service;

import com.example.demo.model.Url;
import com.example.demo.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository repo;

    private final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();

    public UrlService(UrlRepository repo) {
        this.repo = repo;
    }

    private String generateShortCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public String shortenUrl(String longUrl) {
        String code = generateShortCode();

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortCode(code);

        repo.save(url);
        return code;
    }

    public String getLongUrl(String code) {
        return repo.findByShortCode(code)
                .map(Url::getLongUrl)
                .orElse(null);
    }
}