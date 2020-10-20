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
    public AudienceService(ModelMapper modelMapper, AudienceRepository audienceRepository) {
        this.modelMapper = modelMapper;
        this.audienceRepository = audienceRepository;
    }

    public List<AudienceDTO> getAllAudiences() {
        return modelMapper.map(audienceRepository.findAll(), new TypeToken<List<AudienceDTO>>() {
        }.getType());
    }

    public AudienceDTO findById(Long id) {
        return modelMapper.map(audienceRepository.findById(id).get(), AudienceDTO.class);
    }
    
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
        ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    
      public AudienceDTO getByName(String name) {
        return modelMapper.map(audienceRepository.findByName(name), AudienceDTO.class);
    }

    public void remove(Long id) {
        audienceRepository.deleteById(id);
    }

    
    @Override
    public Article save(Long userId, ArticleSaveDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        return userRepository.findById(userId).map(user -> {
            article.setUser(user);
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
    }
    
      public Audience update(Long id, AudiencePostDTO audience) {
        return audienceRepository.findById(id)
            .map(employee -> {
                employee.setName(audience.getName());
                employee.setDescription(audience.getDescription());
                return audienceRepository.save(employee);
            })
            .orElseThrow(
                () -> new ResourceNotFoundException("Audience with id - " + id + " not found"));
    }

    public Audience save(AudiencePostDTO audience) {
        return audienceRepository.save(
            modelMapper.map(audience, Audience.class)
        );
    }
    
}
