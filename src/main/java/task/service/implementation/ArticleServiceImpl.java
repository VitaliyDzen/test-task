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
}
