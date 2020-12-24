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
            Plan plan=new Plan("Starter",new BigDecimal("15"),30,200,45,1,LocalDateTime.now(ZoneId.systemDefault()));
            Plan planTwo=new Plan("Standart",new BigDecimal("30"),60,400,90,1,LocalDateTime.now(ZoneId.systemDefault()));
            User user=this.userRepository.findUserByUsername("valeri12580").get();
            plan.setAuthor(user);
            planTwo.setAuthor(user);
            this.planRepository.saveAll(List.of(plan,planTwo));
        }


    }
}
