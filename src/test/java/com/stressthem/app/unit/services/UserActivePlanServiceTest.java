package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.repositories.UserActivePlanRepository;
import com.stressthem.app.services.UserActivePlanServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserActivePlanServiceTest {

    @Mock
    private UserActivePlanRepository userActivePlanRepository;

    @InjectMocks
    private UserActivePlanServiceImpl userActivePlanService;

    private UserActivePlan userActivePlan;

    @BeforeEach
    public void init() {
        userActivePlan = new UserActivePlan(null, 15, 1, null);
    }


    @Test
    public void decreaseLeftAttackForTheDayShouldWork(){
        userActivePlanService.decreaseLeftAttacksForTheDay(userActivePlan);
        assertEquals(0,userActivePlan.getLeftAttacksForTheDay());
        userActivePlanService.decreaseLeftAttacksForTheDay(userActivePlan);
        assertEquals(0,userActivePlan.getLeftAttacksForTheDay());

    }

    @Test
    public void decreaseLeftDaysShouldClearLeftDays(){
        userActivePlanService.decreaseLeftDays();
        Mockito.verify(userActivePlanRepository).decreaseLeftDays();
    }

    @Test
    public void clearExpiredPlansShouldClearAllExpiredPlans(){
        userActivePlanService.clearExpiredPlans();
        Mockito.verify(userActivePlanRepository).deleteAllExpiredPlans(0);
    }

    @Test
    public void saveActivatedPlanShouldSaveActivatedPlan(){
        userActivePlanService.saveActivatedPlan(userActivePlan);
        Mockito.verify(userActivePlanRepository).save(userActivePlan);
    }

    @Test
    public void refreshDailyAttacksShouldRefreshAttacks(){
        userActivePlanService.refreshDailyAttacks();
        Mockito.verify(userActivePlanRepository).refreshDailyAttacks();
    }
}
