package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.services.interfaces.AttackService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Service
public class AttackServiceImpl implements AttackService {

    private ModelMapper modelMapper;
    private AttackRepository attackRepository;
    private UserService userService;

    @Autowired
    public AttackServiceImpl(ModelMapper modelMapper, AttackRepository attackRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.attackRepository = attackRepository;
        this.userService = userService;
    }

    @Override
    public AttackServiceModel launchAttack(AttackServiceModel attackServiceModel,String username) {
        Attack attack=this.modelMapper.map(attackServiceModel,Attack.class);

        attack.setAttacker(this.modelMapper.map(this.userService.getUserByUsername(username),User.class));
        //todo decrease daily attack with -1
        return this.modelMapper.map(this.attackRepository.save(attack),AttackServiceModel.class);
    }

    @Override
    public List<AttackServiceModel> getAllAttacksForCurrentUser(String username) {
        List<Attack>attacks=this.attackRepository.findAllByAttacker_Username(username);

        return Arrays.asList(this.modelMapper.map(attacks, AttackServiceModel[].class));
    }
}
