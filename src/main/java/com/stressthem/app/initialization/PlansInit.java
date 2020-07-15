package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.PlanRepository;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PlansInit implements CommandLineRunner {
    private PlanRepository planRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PlansInit(PlanRepository planRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        Plan plan=new Plan("Test",new BigDecimal("1500.25"),
                60,30,75,LocalDateTime.now());
        User user=this.userRepository.findUserByUsername("valeri12580").get();
        plan.setAuthor(user);
        this.planRepository.save(plan);
    }
}
