package task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByAgeGreaterThan(Integer age);

    @Query(value = "SELECT DISTINCT u.name FROM User u WHERE SIZE(u.articles) > ?1")
    List<String> findUniqueUserNamesByArticlesCountGreaterThan(Integer count);

}
