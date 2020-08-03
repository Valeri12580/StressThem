package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Cryptocurrency;
import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.UserDeletionException;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.UserServiceImpl;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.TransactionService;
import com.stressthem.app.services.interfaces.UserActivePlanService;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserActivePlanService userActivePlanService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PlanService planService;

    @Mock
    private CryptocurrencyService cryptocurrencyService;

    private Plan plan;

    private Cryptocurrency cryptocurrency;

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

        plan = new Plan("Starter",
                new BigDecimal("15"), 30, 200, 45, 1,
                LocalDateTime.now(ZoneId.systemDefault()));

        cryptocurrency = new Cryptocurrency("Bitcoin",
                "Bitcoin was the first cryptocurrency to successfully record transactions on a secure, decentralized blockchain-based network. Launched in early 2009 by its pseudonymous creator Satoshi Nakamoto, Bitcoin is the largest cryptocurrency measured by market capitalization and amount of data stored on its blockchain.",
                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");

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
    public void purchasePlanShouldThrowExceptionIfUserAlreadyHavePlan() {

        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));

        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());

        Mockito.when(cryptocurrencyService.getCryptocurrencyByName("bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(modelMapper.map(planService.getPlanById("1"), Plan.class)).thenReturn(new Plan());
        Mockito.when(modelMapper.map(cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(new Cryptocurrency());

        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertThrows(UserPlanActivationException.class, () -> {
            userService.purchasePlan("1", "valeri", "bitcoin");
        });
    }

    @Test
    public void purchasePlanShouldWork() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(cryptocurrencyService.getCryptocurrencyByName("bitcoin")).thenReturn(new CryptocurrencyServiceModel());

        Mockito.when(modelMapper.map(planService.getPlanById("1"), Plan.class)).thenReturn(plan);
        Mockito.when(modelMapper.map(cryptocurrencyService.getCryptocurrencyByName("bitcoin"), Cryptocurrency.class)).thenReturn(cryptocurrency);

        userService.purchasePlan("1", "valeri", "bitcoin");

        Mockito.verify(userActivePlanService).saveActivatedPlan(ArgumentMatchers.any());
        Mockito.verify(transactionService).saveTransaction(ArgumentMatchers.any());


    }

    @Test
    public void getUserAvailableAttacksShouldWork(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertEquals(1,userService.getUserAvailableAttacks("valeri"));
    }

    @Test
    public void getUSerAvailableAttacksShouldReturnZero_UserNotHavingActivePlan(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        assertEquals(0,userService.getUserAvailableAttacks("valeri"));
    }

    @Test
    public void getAllUsersShouldReturnAllUsers(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(userRepository.findAll(),UserServiceModel[].class))
                .thenReturn(new UserServiceModel[]{userServiceModel});

        List<UserServiceModel> actual = userService.getAllUsers();

        assertEquals(1,actual.size());
        assertEquals(userServiceModel,actual.get(0));
    }

    @Test
    public void deleteByIdShouldDeleteUser(){
        userService.deleteUserById("1");
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void deleteUserByUsernameShouldThrowException_DeleteSameUser(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        assertThrows(UserDeletionException.class,()->{
           userService.deleteUserByUsername("valeri", "valeri");
        });


    }

    @Test
    public void deleteUserByUsernameShouldWork(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        userService.deleteUserByUsername("valeri","ivan");
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void getUserByUsernameShouldReturnNull_InvalidUser(){
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.empty());
        assertNull(userService.getUserByUsername("valeri"));

    }





}
