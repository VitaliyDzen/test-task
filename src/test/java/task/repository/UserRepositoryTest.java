package task.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import task.entity.Article;
import task.entity.User;

@DataJpaTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUsersByAgeGreaterThan() {
       List<User> users = userRepository.findUsersByAgeGreaterThan(56);
        assertEquals(1, users.size());
        assertEquals("Alice@gmail.com", users.get(0).getEmail());
        assertEquals("Alice", users.get(0).getName());
    }
}