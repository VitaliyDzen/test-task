package task.controller;

import static task.constants.AppConstant.COUNT_OF_ARTICLES;
import static task.constants.HttpStatuses.BAD_REQUEST;
import static task.constants.HttpStatuses.FORBIDDEN;
import static task.constants.HttpStatuses.OK;
import static task.constants.HttpStatuses.UNAUTHORIZED;
import static task.constants.ResourceMapping.AGE;
import static task.constants.ResourceMapping.ARTICLE;
import static task.constants.ResourceMapping.ARTICLE_COLOR;
import static task.constants.ResourceMapping.MORE_THEN_3;
import static task.constants.ResourceMapping.USER;
import static task.constants.ResourceMapping.USER_NAME;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.dto.user.UserDto;
import task.entity.enums.Color;
import task.service.UserService;

@ApiResponses(value = {
    @ApiResponse(code = 200, message = OK),
    @ApiResponse(code = 400, message = BAD_REQUEST),
    @ApiResponse(code = 401, message = UNAUTHORIZED),
    @ApiResponse(code = 403, message = FORBIDDEN)
})
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AirCompanyDto> findAllAirCompanies() {
        return airCompanyService.findAllAirCompanies();
    }
    
    @GetMapping(NAME_COMPANY_NAME_FLIGHTS_STATUS)
    public List<FlightDto> findAllFlightsByFlightStatusAndAirCompany_Name(@RequestParam FlightStatus status, @PathVariable String companyName) {
        return flightService.findAllByFlightStatusAndAirCompany_Name(status, companyName);
    }

    @GetMapping(ID)
    public AirCompanyDto findAirCompanyById(@RequestParam Long id) {
        return airCompanyService.findAirCompanyById(id);
    }

    @GetMapping(NAME)
    public AirCompanyDto findAirCompanyByName(@RequestParam String name) {
        return airCompanyService.findAirCompanyByName(name);
    }

    @ApiOperation(value = "Get all users by article's color")
    @GetMapping(USER + ARTICLE_COLOR)
    public List<UserDto> findByArticlesColor(@RequestParam Color color) {
        return userService.findByArticlesColor(color);
    }

    @ApiOperation(value = "Get all users by age greater than")
    @GetMapping(USER + AGE)
    public List<UserDto> findByAgeGreaterThan(@RequestParam Integer age) {
        return userService.findByAgeGreaterThan(age);
    }

    @PostMapping
    public ResponseEntity<AirCompany> save(@Valid @RequestBody AirCompanyPostDto airCompanyPostDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(airCompanyService.save(airCompanyPostDto));
    }

    @PutMapping(ID_PATH_VARIABLE)
    public ResponseEntity<AirCompany> update(@Valid @RequestBody AirCompanyDto airCompanyDto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(airCompanyService.update(airCompanyDto, id));
    }

    @DeleteMapping(ID_PATH_VARIABLE)
    public void delete(@PathVariable Long id) {
        airCompanyService.delete(id);
    }
}
