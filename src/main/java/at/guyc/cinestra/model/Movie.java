package at.guyc.cinestra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Integer movieId;

    private String title;
    private String genres;

}
