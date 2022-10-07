package az.blog.resource;

import az.blog.domain.dto.UserDTO;
import az.blog.resource.vm.LoginVM;
import az.blog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
class UserResourceTest {

    @MockBean
    static UserService userService;

    @Autowired
    MockMvc mockMvc;


    @BeforeAll
    static void createNewUser() {
    }

    @Test
    void should_return_successful_operation_after_creating_user() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Jack");
        userDTO.setLastname("Johnson");
        userDTO.setEmail("jack@gmail.com");
        userDTO.setPassword("jack123456");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.message", Matchers.is("User created")));

    }

    @Test
    void should_return_forbidden_status_when_user_not_authenticated() throws Exception {
        mockMvc.perform(get("/api/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isForbidden());
    }

    //TODO: Correct this test problem
    @Test
    void test() throws Exception {
        LoginVM loginVM = new LoginVM("test", "test");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginVM);

        mockMvc.perform(post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(jsonPath("$.message", Matchers.is("Username not found")));
    }

}