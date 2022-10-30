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
    
    
    @GetMapping(STATUS + ACTIVE_DATE_YESTERDAY)
    public List<FlightDto> findAllByFlightByActiveStatusAndLessThanYesterday() {
        return flightService.findAllByFlightByActiveStatusAndLessThanYesterday();
    }

    @PostMapping
    public ResponseEntity<Flight> save(@Valid @RequestBody FlightPostDto flightPostDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.save(flightPostDto));
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
    
    
       @PutMapping(ID_PATH_VARIABLE + STATUS)
    public ResponseEntity<Flight> updateStatus(@RequestParam FlightStatus flightStatus, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.updateStatus(flightStatus, id));
    }

    @GetMapping(ID)
    public FlightDto findFlightById(@RequestParam Long id) {
        return flightService.findFlightById(id);
    }

    @GetMapping(STATUS + COMPLETED_DELAYED)
    public List<FlightDto> findAllByFlightStatusCOMPLETEDAndDelayed() {
        return flightService.findAllByFlightStatusCOMPLETEDAndDelayed();
    }
    
}
