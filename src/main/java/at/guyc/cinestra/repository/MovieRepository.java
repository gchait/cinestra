package at.guyc.cinestra.repository;

import at.guyc.cinestra.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // TODO: Add custom query methods if needed

}
