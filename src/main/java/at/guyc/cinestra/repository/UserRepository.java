package at.guyc.cinestra.repository;

import at.guyc.cinestra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // TODO: Add custom query methods if needed

}
