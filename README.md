# Next-Gen Music Discovery Platform

A modern, fast, and intuitive music platform starter for discovering, streaming, and organizing songs using legal APIs.

## Core vision

Create a clean alternative to cluttered music apps by combining speed, simplicity, and smart discovery.

## Implemented in this repository

- Spring Boot backend with APIs for search, trending, latest, recommendations, and legal download-link handoff.
- Responsive dark-themed Thymeleaf UI with clearer sections and improved search feedback.
- Playlist creation and JSON export for quick user workflow demo.
- Legal streaming/download-link handoff only (Spotify / YouTube links).

## API endpoints

- `GET /api/health`
- `GET /api/music/search?q=`
- `GET /api/music/trending`
- `GET /api/music/latest`
- `GET /api/music/recommendations?mood=`
- `GET /api/music/{id}/download` (returns legal provider link; no direct copyrighted file download)

## Run locally

```bash
cd backend
mvn spring-boot:run
```

Then open: `http://localhost:8080/`

## Docker

```bash
docker compose up --build
```
