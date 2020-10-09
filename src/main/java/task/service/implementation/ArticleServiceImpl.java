package task.service.implementation;

import static task.constants.ErrorMessage.USER_NOT_FOUND_WITH_ID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.dto.article.ArticleSaveDto;
import task.entity.Article;
import task.exception.ResourceNotFoundException;
import task.repository.ArticleRepository;
import task.repository.UserRepository;
import task.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

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

    @Override
    public Article save(Long userId, ArticleSaveDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        return userRepository.findById(userId).map(user -> {
            article.setUser(user);
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
    }
    
        @Override
    public List<UserDto> findByAgeGreaterThan(Integer age) {
        return modelMapper.map(userRepository.findUsersByAgeGreaterThan(age),
            new TypeToken<List<UserDto>>() {
            }.getType());
    }
    
}
