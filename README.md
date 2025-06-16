# 🎬 Movie Recommendation Engine with Spark, Java & Spring Boot

## 📌 Project Summary

This is a lightweight side project implementing a **collaborative filtering movie recommender system** using the [MovieLens dataset](https://grouplens.org/datasets/movielens/) and **Apache Spark**. It demonstrates the use of **Java 21**, **Spring Boot**, and **Spark MLlib** in a practical setting — generating and serving movie recommendations.  

This project avoids unnecessary complexity and external rate limits, making it ideal for local experimentation or showcasing architecture and data pipeline design patterns.

---

## 🧠 Motivation

Built as a developer-led project for the sake of learning and hands-on application, it demonstrates:

- Spark-based batch machine learning using ALS (Alternating Least Squares)
- Backend integration of ML output using a clean Java/Spring stack
- HTML-first, lightweight UI using HTMX and Pico.css (no JS frameworks)
- Hourly retraining to simulate a production-like environment without external APIs

---

## 🧰 Tech Stack

| Layer            | Tech                            |
|------------------|----------------------------------|
| Language         | Java 21                          |
| Build Tool       | Gradle (Kotlin DSL)              |
| Backend Framework| Spring Boot 3.x                  |
| ML Engine        | Apache Spark 4.0                 |
| ML Algorithm     | ALS (Matrix Factorization)       |
| Data Source      | MovieLens 100k (local CSV)       |
| Storage          | SQLite (local, file-based)       |
| API              | REST (JSON/HTML)                 |
| Frontend         | Static HTML + HTMX + Pico.css    |
| Templating       | None (HTML is static)            |
| Testing          | JUnit 5                          |
| Job Scheduler    | Spring Scheduled Tasks (hourly)  |

---

## 🗂️ Project Structure

```
project-root/
├── build.gradle.kts
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── App.java
│   │   │   ├── controller/RecommendationController.java
│   │   │   ├── service/RecommendationService.java
│   │   │   └── spark/RecommendationJob.java
│   │   └── resources/
│   │       ├── static/index.html
│   │       └── application.yml
│   └── test/
│       └── java/... (JUnit 5 test cases)
├── data/
│   └── movielens/ratings.csv
├── model/
│   └── recs/user_recommendations.json
└── README.md
```

---

## ⚙️ How It Works

1. **Training (Scheduled Hourly)**  
   Spark runs an ALS training job on `ratings.csv`, producing per-user top-N recommendations stored as JSON and persisted to SQLite for quick access.

2. **Serving**  
   A Spring Boot API exposes endpoints like:

   ```
   GET /recommendations?userId=123
   ```

   This returns either JSON or a small HTML snippet for HTMX.

3. **Frontend**  
   Static HTML (served via Spring) uses HTMX to fetch recommendations dynamically. Styling is handled via Pico.css (CDN), with no frontend build tooling required.

---

## ✅ Features

- ALS-based collaborative filtering using Spark
- Hourly retraining using Spring’s `@Scheduled` annotation
- SQLite persistence for serving precomputed recommendations
- Fast and modern frontend UX via HTMX (no JS frameworks)
- Minimalistic, readable styling with Pico.css
- Fully testable via JUnit 5 with clear separation of logic
- No reliance on Docker, cloud services, or frontend build systems

---

## 🔗 References

- [Apache Spark MLlib ALS](https://spark.apache.org/docs/latest/ml-collaborative-filtering.html)
- [MovieLens Dataset](https://grouplens.org/datasets/movielens/)
- [HTMX](https://htmx.org)
- [Pico.css](https://picocss.com)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc)

---

## 🙌 Who This Is For

This project is for:
- Java developers exploring Spark MLlib or ALS
- Backend engineers looking to understand ML integration patterns
- Engineers curious about building without React, NPM, or Docker
- LLM tools or assistants needing a self-contained Spark+Spring demo

---

Built with practical curiosity, modern tools, and just enough ceremony.
