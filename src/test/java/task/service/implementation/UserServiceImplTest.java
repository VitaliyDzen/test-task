package task.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import task.dto.user.UserDto;
import task.entity.User;
import task.repository.UserRepository;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

        private User user1 = User.builder()
        .id(1L)
        .age(30)
        .email("ann@gmail.com")
        .name("Ann")
        .password("password")
        .articles(null)
        .build();

    private User user2 = User.builder()
        .id(2L)
        .age(23)
        .email("bill@gmail.com")
        .name("Bill")
        .password("password")
        .articles(null)
        .build();

    private UserDto userDto = new UserDto(1L, "test", 45, null);

    private List<User> users = Arrays.asList(user1, user2);
    private List<UserDto> expected = Arrays.asList(userDto);

    @Test
    public void findByAgeGreaterThan() {
        when(userRepository.findUsersByAgeGreaterThan(anyInt())).thenReturn(users);
        when(modelMapper.map(userRepository.findUsersByAgeGreaterThan(anyInt()),
            new TypeToken<List<UserDto>>() {
            }.getType())).thenReturn(expected);
        List<UserDto> actual = userService.findByAgeGreaterThan(anyInt());
        assertEquals(expected, actual);
    }

    @Test
    public void findUniqueUserNamesByArticlesCountGreaterThan(){
        List<String> expected = Arrays.asList("test1", "test2", "test3");
        when(userRepository.findUniqueUserNamesByArticlesCountGreaterThan(anyInt())).thenReturn(expected);
        List<String> actual = userService.findUniqueUserNamesByArticlesCountGreaterThan(anyInt());
        assertEquals(expected, actual);
    }
}