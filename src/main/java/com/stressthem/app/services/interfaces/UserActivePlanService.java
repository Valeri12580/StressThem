package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.entities.UserActivePlan;

public interface UserActivePlanService {
    void decreaseLeftAttacksForTheDay(UserActivePlan userActivePlan);

    void decreaseLeftDays();

    void clearExpiredPlans();

    void saveActivatedPlan(UserActivePlan userActivePlan);

    void refreshDailyAttacks();
}
