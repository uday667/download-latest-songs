package com.telugutune.api.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping({"/", "/app"})
    public String appHome(Model model) {
        model.addAttribute("appName", "TeluguTune AI");
        model.addAttribute("moods", List.of("Happy", "Chill", "Workout", "Romantic", "Focus", "Party"));
        model.addAttribute("trending", List.of(
                Map.of("title", "Butta Bomma", "artist", "Armaan Malik", "source", "YouTube"),
                Map.of("title", "Samajavaragamana", "artist", "Sid Sriram", "source", "Spotify"),
                Map.of("title", "Inkem Inkem Inkem Kaavaale", "artist", "Sid Sriram", "source", "YouTube")));
        return "index";
    }
}
