package task.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.dto.user.UserDto;
import task.entity.Article;
import task.entity.enums.Color;
import task.repository.ArticleRepository;
import task.repository.UserRepository;
import task.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository,
        ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return modelMapper.map(userRepository.findAll(),
            new TypeToken<List<UserDto>>() {
            }.getType());
    }

    @Override
    public List<UserDto> findByAgeGreaterThan(Integer age) {
        return modelMapper.map(userRepository.findUsersByAgeGreaterThan(age),
            new TypeToken<List<UserDto>>() {
            }.getType());
    }

    @Override
    public List<UserDto> findByArticlesColor(Color color) {
        return modelMapper.map(articleRepository.findAllByColor(color)
                .stream()
                .map(Article::getUser)
                .distinct()
                .collect(Collectors.toList()),
            new TypeToken<List<UserDto>>() {
            }.getType());
    }

    @Override
    public List<String> findUniqueUserNamesByArticlesCountGreaterThan(Integer articlesCount) {
        return userRepository.findUniqueUserNamesByArticlesCountGreaterThan(articlesCount);
    }
}
