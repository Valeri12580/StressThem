package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.Announcement;
import com.stressthem.app.domain.models.service.AnnouncementServiceModel;
import com.stressthem.app.domain.models.view.AnnouncementViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.services.interfaces.AnnouncementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@WebMvcTest(controllers = HomeControllerTest.class)
public class HomeControllerTest extends ControllerTestBase {

    @MockBean
    private AnnouncementService announcementService;

    @MockBean
    private ModelMapper modelMapper;




    @Test
    public void home_announcementShouldReturnCorrect() throws Exception {
//        AnnouncementServiceModel announcementServiceModel=new AnnouncementServiceModel();
//        announcementServiceModel.setTitle("test");
//        announcementServiceModel.setDescription("First test");

        Mockito.when(announcementService.getAllAnnouncements()).thenReturn(List.of(new AnnouncementServiceModel(),new AnnouncementServiceModel()));
        Mockito.when(modelMapper.map(announcementService.getAllAnnouncements(), AnnouncementViewModel[].class))
                .thenReturn(new AnnouncementViewModel[]{new AnnouncementViewModel(),new AnnouncementViewModel()});

        super.mockMvc.perform(get("/announcements"))
                .andExpect(view().name("home-announcements"))
                .andExpect(model().attribute("announcements",announcementService.getAllAnnouncements()));
    }
}
