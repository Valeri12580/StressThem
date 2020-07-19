package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.services.interfaces.AttackService;
import com.stressthem.app.services.interfaces.UserActivePlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
public class AttackServiceImpl implements AttackService {

    private ModelMapper modelMapper;
    private AttackRepository attackRepository;
    private UserService userService;
    private UserActivePlanService userActivePlanService;

    @Autowired
    public AttackServiceImpl(ModelMapper modelMapper, AttackRepository attackRepository, UserService userService, UserActivePlanService userActivePlanService) {
        this.modelMapper = modelMapper;
        this.attackRepository = attackRepository;
        this.userService = userService;
        this.userActivePlanService = userActivePlanService;
    }

    @Override
    public AttackServiceModel launchAttack(AttackServiceModel attackServiceModel,String username) {
        Attack attack=this.modelMapper.map(attackServiceModel,Attack.class);

        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);

        attack.setAttacker(user);
        //todo decrease daily attack with -1
        this.userActivePlanService.updateLeftAttacksForTheDay(user.getUserActivePlan());
        return this.modelMapper.map(this.attackRepository.save(attack),AttackServiceModel.class);
    }

    @Override
    public List<AttackServiceModel> getAllAttacksForCurrentUser(String username) {
        List<Attack>attacks=this.attackRepository.findAllByAttacker_Username(username);

        return Arrays.asList(this.modelMapper.map(attacks, AttackServiceModel[].class));
    }

    @Override
    public AttackServiceModel setAttackExpiredOn(int seconds,AttackServiceModel attackServiceModel) {
        attackServiceModel.setExpiresOn(LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(seconds));
        return  attackServiceModel;
    }
}
