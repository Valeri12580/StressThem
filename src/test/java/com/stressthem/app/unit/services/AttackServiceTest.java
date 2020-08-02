package com.stressthem.app.unit.services;

import com.stressthem.app.domain.MethodType;
import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.services.AttackServiceImpl;
import com.stressthem.app.services.interfaces.UserActivePlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AttackServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AttackRepository attackRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserActivePlanService userActivePlanService;

    @InjectMocks
    private AttackServiceImpl attackService;

    private UserServiceModel userServiceModel;

    private Attack attack;
    private Attack attackTwo;
    private User userEntity;

    @BeforeEach
    public void init() {
        userEntity = new User();
        userEntity.setId("1");
        userEntity.setUsername("valeri");
        userEntity.setUserActivePlan(new UserActivePlan(null,15,15,null));


        userServiceModel = new UserServiceModel();
        userServiceModel.setId("1");
        userServiceModel.setUsername("valeri");

        attack = new Attack("193.156.83.136", "3500", MethodType.SSDP, 2, LocalDateTime.now(), userEntity);
        attackTwo = new Attack("191.156.83.136", "8080", MethodType.TCP, 1, LocalDateTime.now(), userEntity);
        userServiceModel.setAttacks(List.of(attack, attackTwo));
        userEntity.setAttacks(List.of(attack, attackTwo));


    }

    @Test
    public void clearAttackShouldClearAllAttacksForSpecificUser() {
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);


        attackService.clearAttacks("valeri");
        Mockito.verify(attackRepository).deleteAllAttacksForUser("1");
    }

    @Test
    public void setAttackExpiresOnShouldWork() {

        AttackServiceModel actual = attackService.setAttackExpiredOn(30, new AttackServiceModel());


        assertEquals(LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(30).getSecond(), actual.getExpiresOn().getSecond());
    }

    @Test
    public void getAllAttacksForCurrentUserShouldReturnAllAttacks() {

        Mockito.when(attackRepository.findAllByAttacker_Username("valeri")).thenReturn(List.of(attack,attackTwo));

        Mockito.when(modelMapper.map(List.of(attack,attackTwo),AttackServiceModel[].class))
                .thenReturn(new AttackServiceModel[]{new AttackServiceModel(),new AttackServiceModel()});

        List<AttackServiceModel> actual=attackService.getAllAttacksForCurrentUser("valeri");

        Mockito.verify(attackRepository).findAllByAttacker_Username("valeri");
        assertEquals(2,actual.size());
    }

    @Test
    public void launchAttackShouldLaunchAttack(){
        Mockito.when(modelMapper.map(new AttackServiceModel(),Attack.class)).thenReturn(attack);
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
        Mockito.when(modelMapper.map(userServiceModel,User.class)).thenReturn(userEntity);
        Mockito.when(userActivePlanService.decreaseLeftAttacksForTheDay(userEntity.getUserActivePlan())).thenCallRealMethod();
    }

}
