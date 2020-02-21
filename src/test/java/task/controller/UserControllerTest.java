package task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import task.dto.user.UserSaveDto;
import task.security.dto.SignInDto;
import task.security.dto.SuccessSignInDto;
import task.security.service.SecurityService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findByAge() throws Exception {
        securityService.saveUser(new UserSaveDto("Test",
            52, "test@gmail.com", "test@gmail.com"));
        SuccessSignInDto successSignInDto = securityService.signIn(new SignInDto(
            "test@gmail.com", "test@gmail.com"));
        String myToken = successSignInDto.getAccessToken();

        mockMvc.perform(get("/user/age/?age=56").header("Authorization",
            "Bearer " + myToken))
            .andExpect(status().isOk());
    }

    @Test
    void findByAgeFailed() throws Exception {
        mockMvc.perform(get("/user/age/?age=56"))
            .andExpect(status().isUnauthorized());
    }
}