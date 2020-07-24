package com.stressthem.app.services;

import com.stressthem.app.domain.models.service.AnnouncementServiceModel;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.services.interfaces.AnnouncementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private ModelMapper modelMapper;
    private AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementServiceImpl(ModelMapper modelMapper, AnnouncementRepository announcementRepository) {
        this.modelMapper = modelMapper;
        this.announcementRepository = announcementRepository;
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
}
