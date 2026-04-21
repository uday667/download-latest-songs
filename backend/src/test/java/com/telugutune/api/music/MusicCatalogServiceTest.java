package com.telugutune.api.music;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MusicCatalogServiceTest {

    private final MusicCatalogService musicCatalogService = new MusicCatalogService();

    @Test
    void searchReturnsResultsWhenQueryMatches() {
        assertFalse(musicCatalogService.search("Sid").isEmpty());
    }

    @Test
    void latestReturnsOnlyLatestSongs() {
        assertTrue(musicCatalogService.latest().stream().allMatch(Song::latest));
    }

    @Test
    void byIdFindsKnownSong() {
        assertTrue(musicCatalogService.byId(1).isPresent());
    }
}
