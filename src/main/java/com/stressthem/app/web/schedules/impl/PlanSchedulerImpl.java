package com.stressthem.app.web.schedules.impl;

import com.stressthem.app.services.interfaces.UserActivePlanService;
import com.stressthem.app.web.schedules.PlanScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class PlanSchedulerImpl implements PlanScheduler {
    private UserActivePlanService userActivePlanService;

    @Autowired
    public PlanSchedulerImpl(UserActivePlanService userActivePlanService) {
        this.userActivePlanService = userActivePlanService;
    }


    @Override
    @Scheduled(cron ="0 0 0 ? * *" )
    public void decreaseLeftDays() {
        this.userActivePlanService.decreaseLeftDays();
        this.userActivePlanService.clearExpiredPlans();

    }


    @Override
    @Scheduled(cron = "0 5 0 ? * *")
    public void refreshLeftAttacks() {
        this.userActivePlanService.refreshDailyAttacks();
    }
}
