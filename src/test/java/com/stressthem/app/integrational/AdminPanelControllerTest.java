package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.repositories.RoleRepository;
import com.stressthem.app.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminPanelControllerTest extends ControllerTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

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
                null, null, null, null, null,null);


        User user = new User("test",
                "test1234",
                "test@dir.bg", "", LocalDateTime.now(ZoneId.systemDefault()), null, Set.of(userRole), null, null, null, null, null,null);


        userRepository.save(admin);
        userRepository.save(user);

    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testDeleteUser_LoadingPage() throws Exception {
        super.mockMvc.perform(get("/admin/delete-user"))
                .andExpect(model().attributeExists("users"))
                .andExpect(status().isOk()).andExpect(view().name("admin-panel-delete-user"));
    }

//     //todo exception constraint violation
    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testDeleteUser_Action() throws Exception {
        super.mockMvc.perform(post("/admin/delete-user").param("username","test").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/delete-user?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,userRepository.count());
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddAnnouncement_Loading() throws Exception {

        super.mockMvc.perform(get("/admin/add-announcement"))
                .andExpect(model().attributeExists("announcement"))
                .andExpect(status().isOk()).andExpect(view().name("admin-panel-announcement-add"));
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddAnnouncement_ShouldAdd_ValidData() throws Exception {
        announcementRepository.deleteAll();
        super.mockMvc.perform(post("/admin/add-announcement")
        .param("title","Our services are down for a short period of time!")
        .param("description","We are sorry about this!Soon it will be fixed!")
        .with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/add-announcement?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,announcementRepository.count());

    }


}
