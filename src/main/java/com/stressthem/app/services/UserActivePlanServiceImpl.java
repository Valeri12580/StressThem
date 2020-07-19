package com.stressthem.app.services;

import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.repositories.UserActivePlanRepository;
import com.stressthem.app.services.interfaces.UserActivePlanService;
import org.springframework.stereotype.Service;

@Service
public class UserActivePlanServiceImpl implements UserActivePlanService {
    private UserActivePlanRepository userActivePlanRepository;

    public UserActivePlanServiceImpl(UserActivePlanRepository userActivePlanRepository) {
        this.userActivePlanRepository = userActivePlanRepository;
    }

    @Override
    public void updateLeftAttacksForTheDay(UserActivePlan userActivePlan) {
        //todo validation for daily limit attacks,impelement constraint validator
        int leftAttacks=userActivePlan.getLeftAttacksForTheDay();

        if(leftAttacks<=0){
            userActivePlan.setLeftAttacksForTheDay(0);
        }else{
            userActivePlan.setLeftAttacksForTheDay(leftAttacks-1);
        }

        userActivePlanRepository.save(userActivePlan);
    }
}
