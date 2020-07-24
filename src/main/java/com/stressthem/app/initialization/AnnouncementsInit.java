package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Announcement;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.AnnouncementRepository;
import com.stressthem.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class AnnouncementsInit implements CommandLineRunner {
    private AnnouncementRepository announcementRepository;
    private UserRepository userRepository;

    @Autowired
    public AnnouncementsInit(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user=this.userRepository.findUserByUsername("valeri12580").get();
        Announcement announcement=new Announcement("Our services are down","Our servives are down due maintance",user,
                LocalDateTime.now(ZoneId.systemDefault()));

        announcementRepository.save(announcement);

    }
}
