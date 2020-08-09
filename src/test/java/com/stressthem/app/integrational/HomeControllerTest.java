package com.stressthem.app.integrational;

import com.stressthem.app.domain.MethodType;
import com.stressthem.app.domain.models.service.AnnouncementServiceModel;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.view.AnnouncementViewModel;
import com.stressthem.app.domain.models.view.AttackViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.services.interfaces.AnnouncementService;
import com.stressthem.app.services.interfaces.AttackService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest extends ControllerTestBase {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AnnouncementService announcementService;

    @MockBean
    private AttackService attackService;




    @Test
    @WithMockUser(username = "valeri12580")
    public void testAnnouncements() throws Exception {

        Mockito.when(announcementService.getAllAnnouncements()).thenReturn(
                List.of(new AnnouncementServiceModel(), new AnnouncementServiceModel()));
        Mockito.when(modelMapper.map(announcementService.getAllAnnouncements(), AnnouncementViewModel[].class))
                .thenReturn(new AnnouncementViewModel[]{new AnnouncementViewModel(), new AnnouncementViewModel()});


        super.mockMvc.perform(get("/home/announcements"))
                .andExpect(view().name("home-announcements"))
                .andExpect(model().attributeExists("announcements"));
    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void deleteAnnouncementsTest() throws Exception {
        Mockito.doNothing().when(announcementService).deleteAnnouncementById("1");

        mockMvc.perform(get("/home/announcements/delete/{id}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home/announcements?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testGetAttackForCurrentUser() throws Exception {
        Mockito.when(attackService.getAllAttacksForCurrentUser("valeri12580"))
                .thenReturn(List.of(new AttackServiceModel()));

        AttackViewModel attackViewModel = new AttackViewModel();
        attackViewModel.setHost("111");
        attackViewModel.setMethod(MethodType.UDP);
        attackViewModel.setPort("15");

        Mockito.when(modelMapper.map(attackService.getAllAttacksForCurrentUser("valeri12580"), AttackViewModel[].class))
                .thenReturn(new AttackViewModel[]{attackViewModel});

        super.mockMvc.perform(get("/home/launch/refresh")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].host").value("111"))
                .andExpect(jsonPath("$.[0].method").value("UDP"))
                .andExpect(jsonPath("$.[0].port").value("15"))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testClearAllAttacksFOrCurrentUser() throws Exception {
        super.mockMvc.perform(get("/home/launch/clear"))
                .andExpect(status().isOk());
        Mockito.verify(attackService).clearAttacks("valeri12580");


    }
}

