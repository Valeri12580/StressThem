package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Cryptocurrency;
import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.UserServiceImpl;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.services.interfaces.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PlanService planService;

    @Mock
    private CryptocurrencyService cryptocurrencyService;

    private Plan plan;

    private User user;
    private UserServiceModel userServiceModel;


    @BeforeEach
    public void init() {
        user = new User();
        user.setId("1");
        user.setUsername("valeri");



        userServiceModel = new UserServiceModel();
        userServiceModel.setId("1");
        userServiceModel.setUsername("valeri");
        userServiceModel.setUserActivePlan(user.getUserActivePlan());

        plan=new Plan("Starter",
                new BigDecimal("15"), 30, 200, 45, 1,
                LocalDateTime.now(ZoneId.systemDefault()));

    }

    @Test
    public void findUserByIdShouldReturnUser() {
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));


        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);

        UserServiceModel actual = userService.findUserById("1");

        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test
    public void findUserByIdShouldThrowException_InvalidData() {
        Mockito.when(userRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.findUserById("2");
        });
    }

    @Test
    public void getUserByEmailShouldReturnUser_ValidEmail() {
        Mockito.when(userRepository.findUserByEmail("valeri")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        UserServiceModel actual = userService.getUserByEmail("valeri");
        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());

    }

    @Test
    public void getUserByEmailShouldReturnNull_InvalidEmail() {
        Mockito.when(userRepository.findUserByEmail("dsa")).thenReturn(Optional.empty());
        assertNull(userService.getUserByEmail("dsa"));
    }

    @Test
    public void hasUserActivePlanShouldWork() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));
        assertTrue(userService.hasUserActivePlan("valeri"));
        user.setUserActivePlan(null);
        assertFalse(userService.hasUserActivePlan("valeri"));

    }

    @Test
    public void purchasePlanShouldThrowExceptionIfUserAlreadyHavePlan(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));


        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());

        Mockito.when(cryptocurrencyService.getCryptocurrencyByName("bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(modelMapper.map(planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(modelMapper.map(cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));
        assertThrows(UserPlanActivationException.class,()->{
           userService.purchasePlan("1","valeri","bitcoin") ;
        });
    }




}
