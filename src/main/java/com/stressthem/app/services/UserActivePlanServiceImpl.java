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
    public void decreaseLeftAttacksForTheDay(UserActivePlan userActivePlan) {
        //todo validation for daily limit attacks,impelement constraint validator
        int leftAttacks=userActivePlan.getLeftAttacksForTheDay();

        if(leftAttacks<=0){
            userActivePlan.setLeftAttacksForTheDay(0);
        }else{
            userActivePlan.setLeftAttacksForTheDay(leftAttacks-1);
        }

        userActivePlanRepository.save(userActivePlan);
    }

    @Override
    public void decreaseLeftDays() {
        this.userActivePlanRepository.decreaseLeftDays();

    }


    @Override
    public void clearExpiredPlans(){
        this.userActivePlanRepository.deleteAllExpiredPlans(0);
    }

    @Override
    public void saveActivatedPlan(UserActivePlan userActivePlan) {
        this.userActivePlanRepository.save(userActivePlan);
    }

    @Override
    public void refreshDailyAttacks() {
        this.userActivePlanRepository.refreshDailyAttacks();
    }
}
