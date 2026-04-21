package com.telugutune.api.music;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
public class MusicApiController {

    private final MusicCatalogService musicCatalogService;

    public MusicApiController(MusicCatalogService musicCatalogService) {
        this.musicCatalogService = musicCatalogService;
    }

    @GetMapping("/search")
    public List<Song> search(@RequestParam(name = "q", required = false) String query) {
        return musicCatalogService.search(query);
    }

    @GetMapping("/trending")
    public List<Song> trending() {
        return musicCatalogService.trending();
    }

    @GetMapping("/latest")
    public List<Song> latest() {
        return musicCatalogService.latest();
    }

    @GetMapping("/{id}/download")
    public Map<String, Object> download(@PathVariable long id) {
        Song song = musicCatalogService.byId(id).orElseThrow(NotFoundException::new);
        return Map.of(
                "songId", song.id(),
                "title", song.title(),
                "provider", song.source(),
                "downloadable", false,
                "message", "Direct file downloads are disabled. Use this legal provider link.",
                "legalLink", song.streamUrl());
    }

    @GetMapping("/recommendations")
    public Map<String, Object> recommendations(@RequestParam(name = "mood", required = false) String mood) {
        return Map.of(
                "mood", mood == null || mood.isBlank() ? "mixed" : mood,
                "songs", musicCatalogService.byMood(mood),
                "explanation", "Recommendations are currently rule-based and can be upgraded to OpenAI/Claude ranking.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class NotFoundException extends RuntimeException {
    }
}
