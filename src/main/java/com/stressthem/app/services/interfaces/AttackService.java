package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.AttackServiceModel;

public interface AttackService {

    AttackServiceModel launchAttack(AttackServiceModel attackServiceModel,String username);
}
