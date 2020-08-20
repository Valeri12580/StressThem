package com.stressthem.app.integrational;

import com.stressthem.app.domain.MethodType;
import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.AttackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest extends ControllerTestBase {


    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AttackService attackService;

    @Autowired
    private AttackRepository attackRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @WithMockUser(username = "valeri12580")
    public void testAnnouncements() throws Exception {

        super.mockMvc.perform(get("/home/announcements"))
                .andExpect(model().attributeExists("announcements"))
                .andExpect(status().isOk()).andExpect(view().name("home-announcements"));
    }

    @Test
    @WithMockUser(username = "valeri12580", roles = {"ROOT", "ADMIN", "USER"})
    public void testDeleteAnnouncements() throws Exception {
        String announcementId = announcementRepository.findAll().get(0).getId();
        super.mockMvc.perform(get("/home/announcements/delete/{id}", announcementId))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/home/announcements?favicon=%5Cassets%5Cimg%5Cfavicon.png"))
                .andDo(print());
        Assertions.assertEquals(0, announcementRepository.count());

    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testGetAllAttackForCurrentUser() throws Exception {
        User user = userRepository.findUserByUsername("valeri12580").get();

        attackRepository.save(new Attack("111.256.132.123", "3306", MethodType.UDP, 2, LocalDateTime.now(), user));
        System.out.println();

        super.mockMvc.perform(get("/home/launch/refresh").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].host").value("111.256.132.123"))
                .andExpect(jsonPath("$[0].port").value("3306"))
                .andExpect(jsonPath("$[0].method").value("UDP"))
                .andExpect(jsonPath("$[0].servers").value("2")).andExpect(status().isOk())
        ;


    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testClearAllAttacksFOrCurrentUser() throws Exception {
        super.mockMvc.perform(get("/home/launch/clear"))
                .andExpect(status().isOk());
        Mockito.verify(attackService).clearAttacks("valeri12580");


    }
}

