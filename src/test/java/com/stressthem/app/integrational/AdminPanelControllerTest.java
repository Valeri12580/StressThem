package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.*;
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

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    private  Role adminRole ;

    @BeforeEach
    public void init() {

        loggerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();


        Role rootRole = new Role("ROOT");
        adminRole = new Role("ADMIN");
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
        loggerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        cryptocurrencyRepository.deleteAll();
        articleRepository.deleteAll();
        planRepository.deleteAll();
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
    public void testDeleteUser_Action_InvalidData() throws Exception {
        super.mockMvc.perform(post("/admin/delete-user").param("username","valeri12580").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/delete-user?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(2,userRepository.count());
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

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddAnnouncement_ShouldNotAdd_InvalidData() throws Exception {
        announcementRepository.deleteAll();
        super.mockMvc.perform(post("/admin/add-announcement")
                .param("title","Our!")
                .param("description","We are sorry about this!Soon it will be fixed!")
                .with(csrf())).andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/add-announcement?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(0,announcementRepository.count());

    }

    //todo exceptions
    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddArticle_LoadPage() throws Exception {
        super.mockMvc.perform(get("/admin/add-article"))
                .andExpect(view().name("admin-panel-article-add"))
                .andExpect(model().attributeExists("article"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddArticleShouldAddArticles_ValidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-article").with(csrf())
        .param("title","Welcome to our website.Here you can find the best services in the internet")
        .param("imageUrl","")
        .param("description","dgfsfghjkgfdsadfjghkhgfddasfdhjghfgadfsdhjgfdbmjvhghjhgcfhcjjhgfghjsfhjgkhgfdfsghjgfsfdghjgfdgdgfdggfg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-article?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,articleRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddArticleShouldNotAddArticles_InvalidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-article").with(csrf())
                .param("title","Wervices in the internet")
                .param("imageUrl","")
                .param("description","dsfhjgkhgfdfsgdgfdggfg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-article?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(0,articleRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddCryptocurrency_LoadPage() throws Exception {
        super.mockMvc.perform(get("/admin/add-cryptocurrency"))
                .andExpect(view().name("admin-panel-add-cryptocurrency"))
                .andExpect(model().attributeExists("cryptocurrency"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddCryptocurrencyShouldAddCryptocurrency_ValidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-cryptocurrency").with(csrf())
                .param("title","Ethereum")
                .param("imageUrl","")
                .param("description","Gold mineeeeeeeeee"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-cryptocurrency?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,cryptocurrencyRepository.count());

    }


    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddCryptocurrencyShouldNotAddCryptocurrency_InvalidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-cryptocurrency").with(csrf())
                .param("title","")
                .param("imageUrl","")
                .param("description",""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-cryptocurrency?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(0,cryptocurrencyRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddPlan_LoadPage() throws Exception {
        super.mockMvc.perform(get("/admin/add-plan"))
                .andExpect(view().name("admin-panel-add-plan"))
                .andExpect(model().attributeExists("plan"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddPlanShouldAddPlan_ValidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-plan").with(csrf())
                .param("type","VIP")
                .param("durationInDays","30")
                .param("maxBootTimeInSeconds","300")
                .param("maxBootsPerDay","15")
                .param("servers","2")
                .param("price","15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-plan?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,planRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testAddPlanShouldNotAddPlan_InvalidData() throws Exception {
        super.mockMvc.perform(post("/admin/add-plan").with(csrf())
                .param("type","")
                .param("durationInDays","")
                .param("maxBootTimeInSeconds","")
                .param("maxBootsPerDay","")
                .param("servers","2")
                .param("price","15"))
                .andExpect(redirectedUrl("/admin/add-plan?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(0,planRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testChangeRoles_Loadpage() throws Exception {
        super.mockMvc.perform(get("/admin/user-roles"))
                .andExpect(view().name("admin-panel-user-roles"))
                .andExpect(model().attributeExists("users","roles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testChangeRoleShouldChangeRole_ValidData() throws Exception {
        super.mockMvc.perform(post("/admin/user-roles").with(csrf())
                .param("username","test")
                .param("role","ADMIN")
        .param("type","Add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user-roles?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals( 2,userRepository.findUserByUsername("test").get().getRoles().size());

    }

    @Test
    @WithMockUser(username = "valeri12580",authorities = {"ADMIN","ROOT","USER"})
    public void testChangeRoleShouldNotChangeRole_InvalidData() throws Exception {
        super.mockMvc.perform(post("/admin/user-roles").with(csrf())
                .param("username","valeri12580")
                .param("role","ADMIN")
                .param("type","Add"))
                .andExpect(flash().attributeExists("error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user-roles?favicon=%5Cassets%5Cimg%5Cfavicon.png"));



    }




}
