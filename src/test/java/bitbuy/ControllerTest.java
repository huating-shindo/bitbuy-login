package bitbuy;


import bitbuy.user.model.User;
import bitbuy.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockService;

    @BeforeEach
    public void init() throws Exception {
        User user = new User("user", "user", "", "");
        when(mockService.registerNewUserAccount(new User())).thenReturn(user);
        when(mockService.loginUser("user", "user")).thenReturn(Optional.of(user));
    }

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void register_ok() throws Exception {

        mockMvc.perform(post("/api/register")
                .content(asJsonString(new User("user", "user", null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("USER")
    @Test
    public void login_ok() throws Exception {

        mockMvc.perform(post("/api/login")
                .content(asJsonString(new User("user", "user", null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user")))
                .andExpect(jsonPath("$.password", is("user")));
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}