package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Announcement;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.AnnouncementServiceModel;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.services.interfaces.AnnouncementService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private ModelMapper modelMapper;
    private AnnouncementRepository announcementRepository;
    private UserService userService;

    @Autowired
    public AnnouncementServiceImpl(ModelMapper modelMapper, AnnouncementRepository announcementRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.announcementRepository = announcementRepository;
        this.userService = userService;
    }

    @Override
    public List<AnnouncementServiceModel> getAllAnnouncements() {
        return List
                .of(this.modelMapper
                        .map(announcementRepository.findAllByOrderByAddedOnDesc(),AnnouncementServiceModel[].class));
    }

    @Override
    public void deleteAnnouncementById(String id) {
        this.announcementRepository.deleteById(id);
    }

    @Override
    public AnnouncementServiceModel registerAnnouncement(AnnouncementServiceModel announcementServiceModel, String username) {

        User user=this.modelMapper.map(this.userService.getUserByUsername(username),User.class);
        Announcement announcement=this.modelMapper.map(announcementServiceModel,Announcement.class);

        announcement.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));
        announcement.setAuthor(user);
        this.announcementRepository.save(announcement);

        return this.modelMapper.map(announcement,AnnouncementServiceModel.class);
    }
}
