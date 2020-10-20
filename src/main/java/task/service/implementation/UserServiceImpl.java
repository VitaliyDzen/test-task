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

   public GroupDTO getByName(String name) {
        return modelMapper.map(groupRepository.findByName(name), GroupDTO.class);
    }

    public List<GroupDTO> getAllGroups() {
        return modelMapper.map(groupRepository.findAll(), new TypeToken<List<GroupDTO>>() {
        }.getType());
    }

    public List<GroupAndroidDTO> getAllGroupNames() {
        return groupRepository.findAll().stream().map(GroupAndroidDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByAgeGreaterThan(Integer age) {
        return modelMapper.map(userRepository.findUsersByAgeGreaterThan(age),
            new TypeToken<List<UserDto>>() {
            }.getType());
    }

     public List<GroupAndroidDTO> getAllByInstituteName(String name) {
        return groupRepository.findByInstituteName(name).stream().map(GroupAndroidDTO::new)
            .collect(Collectors.toList());
    }

    public GroupDTO getGroupById(Long id) {
        return modelMapper.map(groupRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Element with id - " + id + " not found"))
            , GroupDTO.class);
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

      public void remove(Long id) {
        groupRepository.deleteById(id);
    }

    public Group update(Long id, GroupPostDTO group) {
        return groupRepository.findById(id)
            .map(employee -> {
                employee.setName(group.getName());
                employee.setInstitute(group.getInstitute());
                employee.setSpecialty(group.getSpecialty());
                employee.setCurator(group.getCurator());
                employee.setDepartment(group.getDepartment());
                employee.setCourse(group.getCourse());
                return groupRepository.save(employee);
            })
            .orElseThrow(
                () -> new ResourceNotFoundException("Group with id - " + id + " not found"));
    }
    
      public Group save(GroupPostDTO group) {
        return groupRepository.save(modelMapper.map(group, Group.class));
    }

}
