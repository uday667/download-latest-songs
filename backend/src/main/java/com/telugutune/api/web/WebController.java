package com.telugutune.api.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.telugutune.api.music.MusicCatalogService;

@Controller
public class WebController {

    private final MusicCatalogService musicCatalogService;

    public WebController(MusicCatalogService musicCatalogService) {
        this.musicCatalogService = musicCatalogService;
    }

    @GetMapping({"/", "/app"})
    public String appHome(Model model) {
        model.addAttribute("appName", "Next-Gen Music Discovery Platform");
        model.addAttribute("moods", List.of("Happy", "Chill", "Workout", "Romantic", "Focus", "Party"));
        model.addAttribute("trending", musicCatalogService.trending());
        model.addAttribute("latest", musicCatalogService.latest());
        return "index";
    }
}
