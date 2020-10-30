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

    @ApiOperation(value = "Get all users")
    @GetMapping(USER)
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }
  @GetMapping("/{id}", produces = "application/json")
    public @ResponseBody Book getBook(@PathVariable int id) {
        return findBookById(id);
    }
    
    @ApiOperation(value = "Get all users by age greater than")
    @GetMapping(USER + AGE)
    public List<UserDto> findByAgeGreaterThan(@RequestParam Integer age) {
        return userService.findByAgeGreaterThan(age);
    }

      @GetMapping("/{id}", produces = "application/json")
    public Book getBook(@PathVariable int id) {
        return findBookById(id);
    }
    
    @ApiOperation(value = "Get unique names of users that has more than 3 articles")
    @GetMapping(USER_NAME + ARTICLE + MORE_THEN_3)
    public List<String> findUniqueUserNamesByArticlesCountGreaterThan3() {
        return userService.findUniqueUserNamesByArticlesCountGreaterThan(COUNT_OF_ARTICLES);
    }
    
     @GetMapping(path="/", produces = "application/json")
    public Employees getEmployees() 
    {
        return employeeDao.getAllEmployees();
    }
     
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) 
    {
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);
         
        employeeDao.addEmployee(employee);
         
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(employee.getId())
                                    .toUri();
         
        return ResponseEntity.created(location).build();
    }
    
      @GetMapping("/issuereport") // 
    public String getReport() { // 
        return "issues/issuereport_form";
    }

    @PostMapping("/issuereport") // 
    public String submitReport() { // 
        return "issues/issuereport_form";
    }

    @GetMapping("/issues")
    public String getIssues() {  // 
        return "issues/issuereport_list";
    }
    
    
}
