package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Announcement;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.AnnouncementServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.services.AnnouncementServiceImpl;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnnouncementServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    private Announcement announcementEntity;

    private AnnouncementServiceModel announcementServiceModel;

    private User user;

    @BeforeEach
    public void init(){
        this.announcementEntity=new Announcement();
        this.announcementEntity.setId("1");
        this.announcementEntity.setTitle("test");

        this.announcementServiceModel=new AnnouncementServiceModel();
        this.announcementServiceModel.setId("1");
        this.announcementEntity.setTitle("test");

        this.user=new User();
        user.setId("1");
        user.setUsername("valeri");

    }

    @Test
    public void deleteByIdShouldDeleteAnnouncement(){
        announcementService.deleteAnnouncementById("1");
        Mockito.verify(announcementRepository).deleteById("1");

    }

    @Test
    public void getAllAnnouncementShouldReturnAll(){
        Mockito.when(announcementRepository.findAllByOrderByAddedOnDesc()).thenReturn(List.of(announcementEntity));
        Mockito.when(modelMapper.map(announcementRepository.findAllByOrderByAddedOnDesc(),AnnouncementServiceModel[].class))
                .thenReturn(new AnnouncementServiceModel[]{announcementServiceModel});

        List<AnnouncementServiceModel>actual=announcementService.getAllAnnouncements();

        assertEquals(1,actual.size());
        assertEquals(announcementServiceModel,actual.get(0));
    }

    @Test
    public void registerAnnouncementShouldRegisterNewAnnouncements(){
        Mockito.when(modelMapper.map(new UserServiceModel(),User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(announcementServiceModel,Announcement.class)).thenReturn(announcementEntity);

        announcementService.registerAnnouncement(announcementServiceModel,"valeri");

        Mockito.verify(announcementRepository).save(announcementEntity);

    }
}
