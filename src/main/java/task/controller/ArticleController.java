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
    
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
    
    @RequestMapping(value = "/getDateAndTime")
    public ModelAndView getDateAndTime() {

        var now = LocalDateTime.now();
        var dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        var date_time = dtf.format(now);

        var params = new HashMap<String, Object>();
        params.put("date_time", date_time);

        return new ModelAndView("showMessage", params);
    }
    
}
