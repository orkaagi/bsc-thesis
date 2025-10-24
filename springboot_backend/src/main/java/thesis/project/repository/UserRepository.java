package thesis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thesis.project.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u")
    public List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findUserByUsernameWithJPQL(String username);

    Optional<User> findByUsername(String username);
}
