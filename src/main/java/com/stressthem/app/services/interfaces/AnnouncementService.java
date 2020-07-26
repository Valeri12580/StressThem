package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.AnnouncementServiceModel;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementServiceModel>getAllAnnouncements();

    void deleteAnnouncementById(String id);

    AnnouncementServiceModel registerAnnouncement(AnnouncementServiceModel announcementServiceModel,String username);
}
