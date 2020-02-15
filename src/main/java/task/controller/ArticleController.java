package task.controller;

import static task.constants.ResourceMapping.ARTICLE;
import static task.constants.ResourceMapping.USER;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task.dto.article.ArticleSaveDto;
import task.entity.Article;
import task.service.ArticleService;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "Save new article")
    @PostMapping(USER + "/{userId}" + ARTICLE)
    public Article save(@PathVariable(value = "userId") Long userId,
        @Valid @RequestBody ArticleSaveDto articleDto) {
        return articleService.save(userId, articleDto);
    }
}