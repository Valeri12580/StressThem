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

    //"0 0 0 ? * *"
    @Override
    @Scheduled(cron ="* * * * * ? " )
    public void decreaseLeftDays() {
        this.userActivePlanService.decreaseLeftDays();
        this.userActivePlanService.clearExpiredPlans();

    }

    @Override
    public void refreshLeftAttacks() {

    }
}
