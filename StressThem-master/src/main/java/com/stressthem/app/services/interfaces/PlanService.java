package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.PlanServiceModel;

import java.util.List;

public interface PlanService {

    PlanServiceModel register(PlanServiceModel planServiceModel, String username);

    List<PlanServiceModel> getAllPlans();

    PlanServiceModel getPlanById(String id);

    void deletePlanById(String id);


}
