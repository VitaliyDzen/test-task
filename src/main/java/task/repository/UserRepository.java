package task.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
     <S extends T> S save(S entity);
                                                                                                                    
    T findOne(ID primaryKey);
                                                                                                                    
    Iterable<T> findAll();

    Long count();
                                                                                                                       
    void delete(T entity);
                                                                                                                     
    boolean exists(ID primaryKey);
    
    Optional<User> findByEmail(String email);

     List<Article> findAllByColor(Color color);
    
    List<User> findUsersByAgeGreaterThan(Integer age);

    @Query(value = "SELECT DISTINCT u.name FROM User u WHERE SIZE(u.articles) > ?1")
    List<String> findUniqueUserNamesByArticlesCountGreaterThan(Integer count);

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
    
    @Modifying
    @Query(value = "UPDATE User SET refreshTokenKey=:refreshTokenKey WHERE id=:id")
    void updateUserRefreshToken(String refreshTokenKey, Long id);
}
