# TeluguTune AI (Legal Music Discovery Platform)

This repository is a **legal, production-oriented starter** for a music discovery app in the Telugu music domain.

## What this project does

- Uses **legal integrations** (Spotify / YouTube links) for playback handoff.
- Provides secure user login with JWT/Firebase roadmap.
- Supports search, recommendations, and favorites endpoints.
- Includes a Spring Boot backend scaffold and **attractive responsive Thymeleaf UI**.

## What this project does **not** do

- No piracy sources.
- No unauthorized song downloads.
- No bypassed login/security.

## Stack

- Backend: Spring Boot 3 (Java 21)
- Web Frontend: Thymeleaf + modern CSS + vanilla JS
- Database: PostgreSQL
- AI Provider: OpenAI or Claude (pluggable)
- Mobile app: React Native (roadmap for OpenADK/Android packaging)

## Run locally

```bash
cd backend
mvn spring-boot:run
```

Open UI: `http://localhost:8080/`

Health check:

```bash
curl http://localhost:8080/api/health
```

## Docker

```bash
docker compose up --build
```

## React Native APK roadmap (for store submission)

1. Create RN app (Expo recommended).
2. Connect to this backend APIs.
3. Configure Android package id + app signing.
4. Build APK/AAB using EAS Build.
5. Upload to target store (OpenADK/Play Console as applicable).

> Note: This repository currently ships the web frontend (Thymeleaf) and backend; RN client is the next deliverable.
