package com.telugutune.api;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "service", "telugutune-backend",
                "timestamp", Instant.now().toString());
    }

    @GetMapping("/recommendations")
    public Map<String, Object> recommendations() {
        return Map.of(
                "note", "Placeholder endpoint. Wire OpenAI or Claude ranking in service layer.",
                "legal", true);
    }
}
