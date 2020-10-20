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

    @ApiOperation(value = "Get all groups")
    @GetMapping("/all")
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @ApiOperation(value = "Get all groups for android")
    @GetMapping("/allGroupNames")
    public List<GroupAndroidDTO> getAllGroupNames() {
        return groupService.getAllGroupNames();
    }

    @ApiOperation(value = "Get all groups by name")
    @GetMapping("/name/{name}")
    public GroupDTO getByName(@PathVariable String name) {
        return groupService.getByName(name);
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

    @ApiOperation(value = "Get all users by age greater than")
    @GetMapping(USER + AGE)
    public List<UserDto> findByAgeGreaterThan(@RequestParam Integer age) {
        return userService.findByAgeGreaterThan(age);
    }
    
      @ApiOperation(value = "Get all groups for android by Institute name")
    @GetMapping("/allDepartmentNames/{name}")
    public List<GroupAndroidDTO> getAllByInstituteName(@PathVariable String name) {
        return groupService.getAllByInstituteName(name);
    }

    @ApiOperation(value = "Get group by id")
    @GetMapping("/{id}")
    public GroupDTO getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @ApiOperation(value = "Update")
    @PutMapping("/{id}")
    public void update(@RequestBody GroupPostDTO group, @PathVariable Long id) {
        groupService.update(id, group);
    }
   @ApiOperation(value = "Delete")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        groupService.remove(id);
    }
}
