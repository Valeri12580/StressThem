package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.services.interfaces.AttackService;
import com.stressthem.app.services.interfaces.ServerConnection;
import com.stressthem.app.services.interfaces.UserActivePlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private ServerConnection serverConnection;

    @Autowired
    public AttackServiceImpl(ModelMapper modelMapper, AttackRepository attackRepository, UserService userService, UserActivePlanService userActivePlanService, ServerConnection serverConnection) {
        this.modelMapper = modelMapper;
        this.attackRepository = attackRepository;
        this.userService = userService;
        this.userActivePlanService = userActivePlanService;
        this.serverConnection = serverConnection;
    }

    @Override
    public AttackServiceModel launchAttack(AttackServiceModel attackServiceModel, String username,String token) {

        try {
            serverConnection.sendRequest(attackServiceModel.getHost(),attackServiceModel.getPort(),String.valueOf(15),String.valueOf(2),2,token);
        } catch (URISyntaxException e) {

            System.out.println("ERR");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Attack attack = this.modelMapper.map(attackServiceModel, Attack.class);

        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);

        attack.setAttacker(user);

        this.userActivePlanService.decreaseLeftAttacksForTheDay(user.getUserActivePlan());


        return this.modelMapper.map(this.attackRepository.save(attack), AttackServiceModel.class);
    }

    @Override
    public List<AttackServiceModel> getAllAttacksForCurrentUser(String username) {
        List<Attack> attacks = this.attackRepository.findAllByAttacker_Username(username);

        return Arrays.asList(this.modelMapper.map(attacks, AttackServiceModel[].class));
    }

    @Override
    public AttackServiceModel setAttackExpiredOn(int seconds, AttackServiceModel attackServiceModel) {
        attackServiceModel.setExpiresOn(LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(seconds));
        return attackServiceModel;
    }


    @Override
    public void clearAttacks(String username) {
        String userId = this.userService.getUserByUsername(username).getId();
        this.attackRepository.deleteAllAttacksForUser(userId);
    }

    @Override
    public void validateAttack(int time, int servers, String username) {
        StringBuilder errorMessages = new StringBuilder();

        UserActivePlan userActivePlan = this.userService.getUserByUsername(username).getUserActivePlan();

        double includedMaxBootTimeInPlan = userActivePlan.getPlan().getMaxBootTimeInSeconds();
        int includedMaxServersInPlan = userActivePlan.getPlan().getServers();

        if (time > includedMaxBootTimeInPlan) {
            errorMessages.append(String.format("The maximum time included in your plan is: %.0f seconds\n", includedMaxBootTimeInPlan));

        }

        if (servers > includedMaxServersInPlan) {
            errorMessages.append(
                    String.format("The maximum included servers in your plan is: %d\n", includedMaxServersInPlan));
        }

        if (userActivePlan.getLeftAttacksForTheDay() <= 0) {
            errorMessages.append("You dont have available attacks for today\n");
        }

        if(errorMessages.length()!=0){
            throw new IllegalArgumentException(errorMessages.toString());
        }

    }
}
