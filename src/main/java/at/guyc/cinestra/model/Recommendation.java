package at.guyc.cinestra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@IdClass(RecommendationId.class)
@Data
public class Recommendation {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "movie_id")
    private Integer movieId;

    private Double score;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

}
