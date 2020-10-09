package task.controller;

import static task.constants.HttpStatuses.BAD_REQUEST;
import static task.constants.HttpStatuses.FORBIDDEN;
import static task.constants.HttpStatuses.UNAUTHORIZED;
import static task.constants.ResourceMapping.ARTICLE;
import static task.constants.ResourceMapping.USER;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task.constants.HttpStatuses;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(USER)
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Get all users by article's color")
    @GetMapping(USER + ARTICLE_COLOR)
    public List<UserDto> findByArticlesColor(@RequestParam Color color) {
        return userService.findByArticlesColor(color);
    }

  
    
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = HttpStatuses.CREATED),
        @ApiResponse(code = 400, message = BAD_REQUEST),
        @ApiResponse(code = 401, message = UNAUTHORIZED),
        @ApiResponse(code = 403, message = FORBIDDEN)
    })
    @ApiOperation(value = "Save new article")
    @PostMapping(USER + "/{userId}" + ARTICLE)
    public Article save(@PathVariable(value = "userId") Long userId,
        @Valid @RequestBody ArticleSaveDto articleDto) {
        return articleService.save(userId, articleDto);
    }
    
      @ApiOperation(value = "Get all users by age greater than")
    @GetMapping(USER + AGE)
    public List<UserDto> findByAgeGreaterThan(@RequestParam Integer age) {
        return userService.findByAgeGreaterThan(age);
    }

    @ApiOperation(value = "Get unique names of users that has more than 3 articles")
    @GetMapping(USER_NAME + ARTICLE + MORE_THEN_3)
    public List<String> findUniqueUserNamesByArticlesCountGreaterThan3() {
        return userService.findUniqueUserNamesByArticlesCountGreaterThan(COUNT_OF_ARTICLES);
    }
    
}
