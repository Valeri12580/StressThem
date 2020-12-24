package com.stressthem.app.unit.services;

import com.stressthem.app.domain.MethodType;
import com.stressthem.app.domain.entities.Attack;
import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.AttackRepository;
import com.stressthem.app.services.AttackServiceImpl;
import com.stressthem.app.services.UserActivePlanServiceImpl;
import com.stressthem.app.services.interfaces.UserActivePlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AttackServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AttackRepository attackRepository;



    @Mock
    private UserService userService;

    @Mock
    private UserActivePlanServiceImpl userActivePlanService;

    @InjectMocks
    private AttackServiceImpl attackService;

    private UserServiceModel userServiceModel;

    private Attack attack;
    private Attack attackTwo;
    private User userEntity;

    private Plan plan;

    @BeforeEach
    public void init() {
//        plan=new Plan("VIP", BigDecimal.valueOf(15),15,50,15,2,LocalDateTime.now(ZoneId.systemDefault()));
        userEntity = new User();
        userEntity.setId("1");
        userEntity.setUsername("valeri");
        userEntity.setUserActivePlan(new UserActivePlan(plan,15,15,null));


        userServiceModel = new UserServiceModel();
        userServiceModel.setId("1");
        userServiceModel.setUsername("valeri");
        userServiceModel.setUserActivePlan(new UserActivePlan(plan,15,15,null));

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

//    @Test
//    public void launchAttackShouldLaunchAttack(){
//        AttackServiceModel attackServiceModel=new AttackServiceModel();
//        attackServiceModel.setId("1");
//
//        Mockito.when(modelMapper.map(attackServiceModel,Attack.class)).thenReturn(attack);
//
//        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
//
//        Mockito.when(modelMapper.map(userServiceModel,User.class)).thenReturn(userEntity);
//
//        attackService.launchAttack(attackServiceModel,"valeri");
//
//        Mockito.verify(attackRepository).save(attack);
//    }

    @Test
    public void validateAttackShouldValidate(){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
    }

    @Test
    public void validateAttackShouldNotThrowError_DataValid(){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
        attackService.validateAttack(15,1,"valeri");

    }

    private static Stream<Arguments> provideArgumentsForValidateAttack_Throw(){
        return Stream.of(Arguments.of(400,1),Arguments.of(15,30));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForValidateAttack_Throw")
    public void validateAttackShouldThrowError_DataInvalid(int time,int servers){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
        assertThrows(IllegalArgumentException.class,()->{
           attackService.validateAttack(time,servers,"valeri");
        });
    }


    @Test
    public void validateAttackShouldThrowError_UserEmptyDailyAttacks(){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(userServiceModel);
        userServiceModel.getUserActivePlan().setLeftAttacksForTheDay(0);
        assertThrows(IllegalArgumentException.class,()->{
            attackService.validateAttack(15,1,"valeri");
        });
    }


}
