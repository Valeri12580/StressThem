package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.PlanRepository;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@Order(value = 3)
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
        if(this.planRepository.count()==0){
            Plan plan=new Plan("BASIC",new BigDecimal("8"),30,300,999,1,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-aS4xHudmadqpnR9jUeZBweXd");

            Plan planTwo=new Plan("BASIC PLUS",new BigDecimal("12"),30,300,999,2,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-aS4xHudmadqpnR9jUeZBweXd");
            Plan planThree=new Plan("PREMIUM",new BigDecimal("25"),30,900,999,2,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-EESTYR8QEg9EkGnYgFh5TGEe");
            Plan planFour=new Plan("PREMIUM PLUS",new BigDecimal("30"),30,900,999,3,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-EESTYR8QEg9EkGnYgFh5TGEe");
            Plan planFive=new Plan("BOOSTED",new BigDecimal("70"),30,3500,999,7,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-Jdtk3GDZcTaQ96Kgasdasdasd");
            Plan planSix=new Plan("LIFETIME",new BigDecimal("170"),999,3500,999,4,LocalDateTime.now(ZoneId.systemDefault()),"DoxyAss-EESTYR8QEg9EkGnYgFh5TGEe");

            User user=this.userRepository.findUserByUsername("valeri12580").get();
            plan.setAuthor(user);
            planTwo.setAuthor(user);
            planThree.setAuthor(user);
            planFour.setAuthor(user);
            planFive.setAuthor(user);
            planSix.setAuthor(user);
            this.planRepository.saveAll(List.of(plan,planTwo,planThree,planFour,planFive,planSix));
        }


    }
}
