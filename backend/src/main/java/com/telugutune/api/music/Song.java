package com.telugutune.api.music;

public record Song(
        long id,
        String title,
        String artist,
        String album,
        int year,
        String mood,
        String source,
        String streamUrl,
        boolean trending,
        boolean latest) {
}
