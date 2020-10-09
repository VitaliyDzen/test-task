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

     @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
        ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Article save(Long userId, ArticleSaveDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        return userRepository.findById(userId).map(user -> {
            article.setUser(user);
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
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
