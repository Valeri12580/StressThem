package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.entities.UserActivePlan;

public interface UserActivePlanService {
    void updateLeftAttacksForTheDay(UserActivePlan userActivePlan);
}
