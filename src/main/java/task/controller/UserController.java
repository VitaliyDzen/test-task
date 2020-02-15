package task.controller;

import static task.constants.AppConstant.COUNT_OF_ARTICLES;
import static task.constants.ResourceMapping.AGE;
import static task.constants.ResourceMapping.ARTICLE;
import static task.constants.ResourceMapping.ARTICLE_COLOR;
import static task.constants.ResourceMapping.MORE_THEN_3;
import static task.constants.ResourceMapping.USER;
import static task.constants.ResourceMapping.USER_NAME;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.dto.user.UserDto;
import task.dto.user.UserSaveDto;
import task.entity.User;
import task.entity.enums.Color;
import task.service.UserService;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(USER)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Get all users by article's color")
    @GetMapping(USER + ARTICLE_COLOR)
    public List<UserDto> findByArticlesColor(@RequestParam Color color) {
        return userService.findByArticlesColor(color);
    }

    @ApiOperation(value = "Get all users by age greater than")
    @GetMapping(USER + AGE)
    public List<UserDto> findByAgeGreaterThan(@RequestParam Integer age){
        return userService.findByAgeGreaterThan(age);
    }

    @ApiOperation(value = "Get unique names of users that has more than 3 articles")
    @GetMapping(USER_NAME + ARTICLE + MORE_THEN_3)
    public List<String> findUniqueUserNamesByArticlesCountGreaterThan3(){
        return userService.findUniqueUserNamesByArticlesCountGreaterThan(COUNT_OF_ARTICLES);
    }

    @ApiOperation(value = "Create new user")
    @PostMapping(USER)
    public User save(@Valid @RequestBody UserSaveDto userDto) {
        return userService.save(modelMapper.map(userDto, User.class));
    }
}
