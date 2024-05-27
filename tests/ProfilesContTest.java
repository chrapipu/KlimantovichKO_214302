import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.controller.ProfilesCont;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfilesCont.class)
public class ProfilesContTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository usersRepo;

    @Test
    public void testProfiles() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "User1", Role.ADMIN),
                new User(2L, "User2", Role.USER)
        );
        when(usersRepo.findAllByOrderByRole()).thenReturn(users);

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usersList", "roles"))
                .andExpect(model().attribute("usersList", hasSize(2)))
                .andExpect(model().attribute("usersList", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("usersList", hasItem(hasProperty("id", is(2L)))));
    }

    @Test
    public void testProfileEditRole() throws Exception {
        User user = new User(1L, "User1", Role.USER);
        when(usersRepo.getReferenceById(1L)).thenReturn(user);

        mockMvc.perform(post("/profiles/1/edit").param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles"));

        verify(usersRepo).save(user);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testProfileDelete() throws Exception {
        mockMvc.perform(get("/profiles/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles"));

        verify(usersRepo).deleteById(1L);
    }
}
