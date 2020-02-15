package task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.entity.Article;
import task.entity.enums.Color;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByColor(Color color);
}
