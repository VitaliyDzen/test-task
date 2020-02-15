package task.service;

import java.util.List;
import task.dto.user.UserDto;
import task.entity.User;
import task.entity.enums.Color;

public interface UserService {

    List<User> findAll();

    List<UserDto> findByAgeGreaterThan(Integer age);

    List<UserDto> findByArticlesColor(Color color);

    List<String> findUniqueUserNamesByArticlesCountGreaterThan(Integer articlesCount);

    User save(User user);
}
