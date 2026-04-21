package com.telugutune.api.music;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class MusicCatalogService {

    private static final List<Song> SONGS = List.of(
            new Song(1, "Samajavaragamana", "Sid Sriram", "Ala Vaikunthapurramuloo", 2020, "Romantic", "Spotify", "https://open.spotify.com", true, false),
            new Song(2, "Butta Bomma", "Armaan Malik", "Ala Vaikunthapurramuloo", 2020, "Happy", "YouTube", "https://music.youtube.com", true, false),
            new Song(3, "Ramuloo Ramulaa", "Anurag Kulkarni", "Ala Vaikunthapurramuloo", 2020, "Party", "YouTube", "https://music.youtube.com", false, false),
            new Song(4, "Inkem Inkem Inkem Kaavaale", "Sid Sriram", "Geetha Govindam", 2018, "Chill", "Spotify", "https://open.spotify.com", false, false),
            new Song(5, "Kurchi Madathapetti", "Sri Krishna", "Guntur Kaaram", 2024, "Workout", "Spotify", "https://open.spotify.com", true, true),
            new Song(6, "Oh My Baby", "Shilpa Rao", "Guntur Kaaram", 2024, "Focus", "YouTube", "https://music.youtube.com", false, true),
            new Song(7, "Adhento Gaani Vunnapaatuga", "Anirudh Ravichander", "Jersey", 2025, "Chill", "Spotify", "https://open.spotify.com", true, true),
            new Song(8, "Fear Song", "Anirudh Ravichander", "Devara", 2024, "Workout", "YouTube", "https://music.youtube.com", true, true));

    public List<Song> all() {
        return SONGS;
    }

    public Optional<Song> byId(long id) {
        return SONGS.stream().filter(song -> song.id() == id).findFirst();
    }

    public List<Song> search(String query) {
        if (query == null || query.isBlank()) {
            return SONGS;
        }

        String normalized = query.toLowerCase(Locale.ROOT);
        return SONGS.stream()
                .filter(song -> song.title().toLowerCase(Locale.ROOT).contains(normalized)
                        || song.artist().toLowerCase(Locale.ROOT).contains(normalized)
                        || song.album().toLowerCase(Locale.ROOT).contains(normalized))
                .toList();
    }

    public List<Song> trending() {
        return SONGS.stream().filter(Song::trending).toList();
    }

    public List<Song> latest() {
        return SONGS.stream()
                .filter(Song::latest)
                .sorted(Comparator.comparingInt(Song::year).reversed())
                .toList();
    }

    public List<Song> byMood(String mood) {
        if (mood == null || mood.isBlank()) {
            return SONGS;
        }

        String normalized = mood.toLowerCase(Locale.ROOT);
        return SONGS.stream()
                .filter(song -> song.mood().toLowerCase(Locale.ROOT).equals(normalized))
                .toList();
    }
}
