package at.guyc.cinestra.repository;

import at.guyc.cinestra.model.Rating;
import at.guyc.cinestra.model.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {

    // TODO: Add custom query methods if needed

}
