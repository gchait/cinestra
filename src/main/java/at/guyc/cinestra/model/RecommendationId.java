package at.guyc.cinestra.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendationId implements Serializable {

    private Integer userId;
    private Integer movieId;

}
