package at.guyc.cinestra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ratings")
@IdClass(RatingId.class)
@Data
public class Rating {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "movie_id")
    private Integer movieId;

    private Integer rating;
    private Integer timestamp;

}
