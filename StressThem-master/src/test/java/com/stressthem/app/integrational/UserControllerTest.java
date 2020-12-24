package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.RoleRepository;
import com.stressthem.app.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends ControllerTestBase {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;


    @BeforeEach
    public void init() {

        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role rootRole = new Role("ROOT");
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        roleRepository.saveAll(List.of(rootRole, adminRole, userRole));

        User admin = new User("valeri12580", "12345678", "valeri125@dir.bg",
                "https://i.ytimg.com/vi/WhIrvsbEJ6Q/maxresdefault.jpg",
                LocalDateTime.now(ZoneId.systemDefault()), null, new HashSet<>(List.of(rootRole, adminRole, userRole)),
                null, null, null, null, null, null);


        User user = new User("test",
                "test1234",
                "test@dir.bg", "", LocalDateTime.now(ZoneId.systemDefault()), null, Set.of(userRole), null, null, null, null, null, null);


        userRepository.save(admin);
        userRepository.save(user);

    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testRegister_Successful() throws Exception {
        super.mockMvc.perform(post("/users/register")
                .param("username", "test35").param("passwordWrapper.password", "12345678").param("passwordWrapper.confirmPassword", "12345678")
                .param("email", "test@abv.bg").param("imageUrl", "")
                .with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/users/login?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(3, userRepository.count());

    }

    @Test
    public void shouldNotCreateUser_WrongParams() throws Exception {
        //act
        mockMvc.perform(post("/users/register")
                .with(csrf())
                .param("username", "g")
                .param("email", "IgnatIgnat.com")
                .param("passwordWrapper.password", "123")
                .param("passwordWrapper.confirmPassword", "12345")
        ).andExpect(status().is3xxRedirection());

        //assert
        Assertions.assertEquals(2, this.userRepository.count());
    }

    @Test
    public void testRegister_LoadingPage() throws Exception {
        super.mockMvc.perform(get("/users/register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_LoadingPage() throws Exception {
        super.mockMvc.perform(get("/users/login"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testProfileEdit_Loading() throws Exception {
        super.mockMvc.perform(get("/users/profile/{username}", "valeri12580"))
                .andExpect(model().attributeExists("userEdit")).andExpect(status().isOk())
                .andExpect(view().name("profile-edit"));
    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testDeleteProfile() throws Exception {
        String userID = userRepository.
                findUserByUsername("valeri12580").get().getId();
        super.mockMvc.perform(get("/users/profile/delete/{id}", userID))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, userRepository.count());

    }


}
