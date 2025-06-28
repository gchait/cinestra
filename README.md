# Cinestra

**🎬 Movie Recommendation Engine with Spark, Java & Spring Boot**

## 📌 Project Summary

This is a **learning-focused movie recommendation engine** that demonstrates production ML patterns using **Apache Spark ALS**, **Spring Boot**, and **SQLite**. It processes the MovieLens 1M dataset (1 million real movie ratings) and simulates continuous user activity through synthetic data generation.

**Key Learning Goals:**

- Understand collaborative filtering with Spark MLlib
- See how ML models integrate with web applications
- Experience realistic data pipeline timing and growth patterns
- Build without external dependencies or complex infrastructure

The project prioritizes educational value and architectural clarity over production scale.

---

## 🧠 Architecture Highlights

- **Ephemeral ML Models**: ALS matrices exist only during training
- **Pre-computed Serving**: Fast recommendations via database queries
- **Synthetic Data Growth**: Simulates realistic production patterns
- **Single File Deployment**: All data persists in one SQLite file
- **Zero Dependencies**: No Docker, servers, or external services

---

## 🧰 Tech Stack

| Technology           | Benefits                                                                                            |
| -------------------- | --------------------------------------------------------------------------------------------------- |
| **Java 21**          | Virtual threads for concurrency • Strong typing for ML data • Mature Spark ecosystem                |
| **Spring Boot 3**    | Auto-configuration reduces boilerplate • Built-in scheduling for ML jobs • Production-ready metrics |
| **Apache Spark 4.0** | Distributed ALS algorithm • In-memory processing speed • Handles millions of ratings efficiently    |
| **SQLite**           | Zero configuration setup • Single file deployment • Embedded with no server overhead                |
| **HTMX**             | Dynamic UIs without JavaScript • Server-side rendering simplicity • Progressive enhancement         |
| **Pico.css**         | No build system required • Semantic HTML styling • Tiny footprint (10KB)                            |

---

## 🗂️ Project Structure

```
cinestra/
├── build.gradle.kts
├── gradle/libs.versions.toml
├── src/
│   ├── main/
│   │   ├── java/at/guyc/cinestra/
│   │   │   ├── CinestraApplication.java
│   │   │   ├── controller/RecommendationController.java
│   │   │   ├── service/RecommendationService.java
│   │   │   ├── service/DataLoaderService.java
│   │   │   ├── model/User.java, Movie.java, Rating.java, Recommendation.java
│   │   │   ├── repository/UserRepository.java, MovieRepository.java, RatingRepository.java, RecommendationRepository.java
│   │   │   └── spark/RecommendationJob.java
│   │   └── resources/
│   │       ├── static/index.html
│   │       └── application.yml
│   └── test/java/at/guyc/cinestra/
│       └── CinestraApplicationTests.java
├── cinestra.db (generated on first run)
└── README.md
```

## 🗄️ Database Schema

**SQLite Tables (based on MovieLens 1M format):**

```sql
users            -- User demographics from users.dat
├── user_id      (INTEGER PRIMARY KEY)  -- Range: 1-6040
├── gender       (TEXT)                 -- 'M' or 'F'
├── age          (INTEGER)              -- Age groups: 1, 18, 25, 35, 45, 50, 56
├── occupation   (INTEGER)              -- Occupation codes: 0-20
└── zip_code     (TEXT)                 -- User-provided ZIP codes

movies           -- Movie catalog from movies.dat
├── movie_id     (INTEGER PRIMARY KEY)  -- Range: 1-3952 (some gaps exist)
├── title        (TEXT)                 -- IMDB titles with year: "Toy Story (1995)"
└── genres       (TEXT)                 -- Pipe-separated: "Animation|Children's|Comedy"

ratings          -- Core training data from ratings.dat + synthetic
├── user_id      (INTEGER)              -- References users.user_id (1-6040)
├── movie_id     (INTEGER)              -- References movies.movie_id (1-3952)
├── rating       (INTEGER)              -- Whole stars only: 1, 2, 3, 4, or 5
└── timestamp    (INTEGER)              -- Unix timestamp (seconds since epoch)

recommendations  -- Pre-computed ML output (the "stored model")
├── user_id      (INTEGER)              -- User to recommend for
├── movie_id     (INTEGER)              -- Recommended movie
├── score        (REAL)                 -- ALS prediction score
└── generated_at (TIMESTAMP)            -- When recommendation was generated
```

**MovieLens 1M Specifications:**

- **Users**: 6,040 users (IDs 1-6040), each with 20+ ratings
- **Movies**: 3,952 movies (IDs 1-3952, some gaps due to test entries)
- **Ratings**: 1M ratings, whole stars only (1-5), with Unix timestamps
- **Genres**: 18 predefined genres (Action, Adventure, Animation, etc.)
- **Age Groups**: 7 ranges from "Under 18" to "56+"
- **Occupations**: 21 categories from "other" to "writer"

---

## ⚙️ How It Works

### **Data Flow Architecture**

```
SQLite → Spark (in-memory) → SQLite
   ↑                           ↓
Synthetic Data ←→ Pre-computed Recommendations
```

### **Data Growth Simulation**

**Smart Initialization (checks database state):**

- **If `cinestra.db` exists and is populated**: Skip download, use existing data
- **If `cinestra.db` is missing or empty**:
  - Download `ml-1m.zip` from https://files.grouplens.org/datasets/movielens/ml-1m.zip
  - Extract and parse three `.dat` files (delimiter: `::`)
  - Load users.dat → `users` table (6,040 users with demographics)
  - Load movies.dat → `movies` table (3,952 movies with titles/genres)
  - Load ratings.dat → `ratings` table (1M ratings with timestamps)
  - **Data flows directly from memory to SQLite** - no intermediate files saved to filesystem
- **Result**: Persistent SQLite database that survives app restarts

**Continuous Growth (Every 3-8 minutes):**

- Pick 1-4 existing users randomly (from user_id 1-6040)
- Each user rates 1-3 movies they haven't rated yet (from movie_id 1-3952)
- Random whole-star ratings (1, 2, 3, 4, or 5) added to database
- Uses current Unix timestamp for realistic temporal progression
- Simulates ongoing user activity within dataset constraints

**Training Schedule:**

- Every hour: Process all available ratings (original + synthetic)
- New ratings continue during training (realistic production behavior)

### **ML Pipeline (Hourly)**

1. **Data Loading**

   - Load ALL ratings (original + synthetic) from SQLite into Spark DataFrame
   - Dataset grows continuously, making each training cycle meaningful

2. **Model Training**

   - ALS model trains entirely in-memory on complete dataset
   - Model matrices (user/item factors) exist only during training
   - **No model persistence** — matrices are ephemeral

3. **Recommendation Generation**

   - Generate top-10 recommendations for all 6,040 users
   - Pre-compute all predictions (no runtime matrix multiplication)
   - Bulk-insert ~60,400 recommendations into `recommendations` table
   - Replace old recommendations with fresh ones

4. **Model Disposal**
   - ALS factor matrices are discarded after recommendation generation
   - The `recommendations` table IS the "stored model"

### **Serving (Real-time)**

Fast SQLite queries for instant recommendation retrieval:

```sql
SELECT movie_id, score FROM recommendations
WHERE user_id = ? ORDER BY score DESC LIMIT 10
```

Returns JSON or HTML snippet for HTMX (based on user-agent).

---

## 🔗 References

- [Apache Spark MLlib ALS](https://spark.apache.org/docs/latest/ml-collaborative-filtering.html)
- [MovieLens Dataset](https://grouplens.org/datasets/movielens/)
- [HTMX](https://htmx.org)
- [Pico.css](https://picocss.com)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc)

---

Built with practical curiosity, modern tools, and just enough ceremony.
