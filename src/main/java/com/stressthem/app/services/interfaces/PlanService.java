package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.PlanServiceModel;

public interface PlanService {

    PlanServiceModel register(PlanServiceModel planServiceModel,String username);
}
