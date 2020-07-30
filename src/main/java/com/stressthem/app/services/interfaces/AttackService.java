package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.AttackServiceModel;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

public interface AttackService {

    AttackServiceModel launchAttack(AttackServiceModel attackServiceModel,String username);

    List<AttackServiceModel>getAllAttacksForCurrentUser(String username);

    AttackServiceModel setAttackExpiredOn(int seconds,AttackServiceModel attackServiceModel);

    void clearAttacks(String username);

    void validateAttack(int time, int servers, String username, BindingResult bindingResult);
}
