package at.guyc.cinestra.repository;

import at.guyc.cinestra.model.Recommendation;
import at.guyc.cinestra.model.RecommendationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, RecommendationId> {

    // TODO: Add custom query methods if needed

}
