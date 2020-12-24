package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.*;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.ChangeRoleException;
import com.stressthem.app.exceptions.UserDeletionException;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.UserServiceImpl;
import com.stressthem.app.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private RoleService roleService;

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

    private Role adminRole;
    private Role rootRole;
    private Role userRole;

//
//    @BeforeEach
//    public void init() {
//        adminRole = new Role("ADMIN");
//        rootRole = new Role("ROOT");
//        userRole=new Role("User");
//
//        user = new User();
//        user.setId("1");
//        user.setUsername("valeri");
//        user.setRoles(new HashSet<>(List.of(adminRole,userRole)));
//
//
//        userServiceModel = new UserServiceModel();
//        userServiceModel.setId("1");
//        userServiceModel.setUsername("valeri");
//        userServiceModel.setUserActivePlan(user.getUserActivePlan());
//
//        plan = new Plan("Starter",
//                new BigDecimal("15"), 30, 200, 45, 1,
//                LocalDateTime.now(ZoneId.systemDefault()));
//
//        cryptocurrency = new Cryptocurrency("Bitcoin",
//                "Bitcoin was the first cryptocurrency to successfully record transactions on a secure, decentralized blockchain-based network. Launched in early 2009 by its pseudonymous creator Satoshi Nakamoto, Bitcoin is the largest cryptocurrency measured by market capitalization and amount of data stored on its blockchain.",
//                user, LocalDateTime.now(ZoneId.systemDefault()), "https://static.coindesk.com/wp-content/uploads/2018/11/dark-bitcoin-scaled.jpg");
//
//    }

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
    public void getUserAvailableAttacksShouldWork() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        user.setUserActivePlan(new UserActivePlan(null, 15, 1, null));

        assertEquals(1, userService.getUserAvailableAttacks("valeri"));
    }

    @Test
    public void getUSerAvailableAttacksShouldReturnZero_UserNotHavingActivePlan() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        assertEquals(0, userService.getUserAvailableAttacks("valeri"));
    }

    @Test
    public void getAllUsersShouldReturnAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(userRepository.findAll(), UserServiceModel[].class))
                .thenReturn(new UserServiceModel[]{userServiceModel});

        List<UserServiceModel> actual = userService.getAllUsers();

        assertEquals(1, actual.size());
        assertEquals(userServiceModel, actual.get(0));
    }

    @Test
    public void deleteByIdShouldDeleteUser() {
        userService.deleteUserById("1");
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void deleteUserByUsernameShouldThrowException_DeleteSameUser() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        assertThrows(UserDeletionException.class, () -> {
            userService.deleteUserByUsername("valeri", "valeri");
        });


    }

    @Test
    public void deleteUserByUsernameShouldWork() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        userService.deleteUserByUsername("valeri", "ivan");
        Mockito.verify(userRepository).deleteById("1");
    }

    @Test
    public void getUserByUsernameShouldReturnNull_InvalidUser() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.empty());
        assertNull(userService.getUserByUsername("valeri"));

    }

    @Test
    public void getUserByUsernameShouldReturnUserServiceModel() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        UserServiceModel actual = userService.getUserByUsername("valeri");
        assertEquals(userServiceModel, actual);
    }

    @Test
    public void loadUserByUsernameShouldThrowUserNotFoundException_InvalidUser() {
        Mockito.when(this.userRepository.findUserByUsername("emtpy")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("test");
        });
    }

    //todo fix this

//    @Test
//    public void updateUserShouldUpdateCurrentUser() {
//        UserServiceModel userServiceModel = new UserServiceModel();
//        userServiceModel.setUsername("modified");
//        userServiceModel.setId("1");
//        Mockito.when(this.userRepository.findById("1")).thenReturn(Optional.of(user));
//        Mockito.when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
//
//        assertEquals(userServiceModel, userService.updateUser(username, userServiceModel));
//        Mockito.verify(userRepository).save(user);
//    }

    @Test
    public void changeUserRoleShouldThrowChangeRoleException_SameUser() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));

        assertThrows(ChangeRoleException.class, () -> {
            userService.changeUserRole("valeri", null, null, "valeri");
        });
    }

    @Test
    public void changeUserRoleShouldThrowException_UserAlreadyHasThatRole() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);

        assertThrows(ChangeRoleException.class, () -> {
            userService.changeUserRole("valeri", "ADMIN", "Add", "gosho");
        });


    }

    @Test
    public void changeUserRoleShouldAddRoleIfEverything_OK() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ROOT")).thenReturn(rootRole);

        userService.changeUserRole("valeri", "ROOT", "Add", "gosho");
        assertTrue(user.getRoles().contains(rootRole));
        assertEquals(3, user.getRoles().size());

    };

    @Test
    public void changeUserRoleShouldThrowException_UserDoesntHaveThatRole() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ROOT")).thenReturn(rootRole);

        assertThrows(ChangeRoleException.class, () -> {
            userService.changeUserRole("valeri", "ROOT", "Remove", "gosho");
        });

    }

    @Test
    public void changeUserRoleShouldRemoveRoleIfEverything_OK() {
        Mockito.when(userRepository.findUserByUsername("valeri")).thenReturn(Optional.of(user));
        Mockito.when(roleService.getRoleByName("ADMIN")).thenReturn(adminRole);

        userService.changeUserRole("valeri", "ADMIN", "Remove", "gosho");
        assertFalse(user.getRoles().contains(adminRole));
        assertEquals(1, user.getRoles().size());

    };


}





