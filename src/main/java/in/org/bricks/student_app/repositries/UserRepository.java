package in.org.bricks.student_app.repositries;

import in.org.bricks.student_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String email);

}
