package task.service;

import task.dto.article.ArticleSaveDto;
import task.entity.Article;

public interface ArticleService {

    Article save(Long userId, ArticleSaveDto articleDto);
}
