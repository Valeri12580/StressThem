package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.PlanNotFoundException;
import com.stressthem.app.repositories.PlanRepository;
import com.stressthem.app.services.PlanServiceImpl;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PlanServiceTest {

    @InjectMocks
    private PlanServiceImpl planService;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    private Plan planOne;
    private Plan planTwo;

    private UserServiceModel user;


    private PlanServiceModel expectedOne;
    private PlanServiceModel expectedTwo;


    @BeforeEach
    public void init() {
        this.planOne = new Plan("Starter",
                new BigDecimal("15"), 30, 200, 45, 1, LocalDateTime.now(ZoneId.systemDefault()));
        this.planOne.setId("1");

        this.planTwo = new Plan("Standart", new BigDecimal("30"), 60, 400, 90, 1, LocalDateTime.now(ZoneId.systemDefault()));
        this.planTwo.setId("2");


        this.expectedOne = new PlanServiceModel("Starter",
                new BigDecimal("15"), 30, 200, 45, LocalDateTime.now(ZoneId.systemDefault()), 1);
        this.expectedOne.setId("1");

        this.expectedTwo = new PlanServiceModel("Standart", new BigDecimal("30"), 60, 400, 90,LocalDateTime.now(ZoneId.systemDefault()) ,1);
        this.expectedTwo.setId("2");


        this.user=new UserServiceModel();
        user.setId("1");
        user.setUsername("valeri");
    }

    @Test
    public void getPlanByIdShouldReturnCorrect_WhenDataIsValid() {

        Mockito.when(planRepository.findById("1")).thenReturn(Optional.of(planOne));

        Mockito.when(modelMapper.map(planOne, PlanServiceModel.class)).thenReturn(expectedOne);

        PlanServiceModel actual = this.planService.getPlanById("1");

        Assertions.assertEquals(expectedOne, actual);

    }

    @Test
    public void getPlanByIdShouldThrowException_WhenDataIsInvalid() {
        Mockito.when(planRepository.findById("2")).thenReturn(Optional.empty());

        Assertions.assertThrows(PlanNotFoundException.class, () -> {
            planService.getPlanById("2");
        });

    }

    @Test
    public void getAllPlansShouldReturnAllAvailablePlans() {
////        Mockito.when(planRepository.findAll()).thenReturn(List.of(planOne,planTwo));
////
////        Mockito.when(modelMapper.map(planOne,PlanServiceModel.class)).thenReturn(expectedOne);
////
////        Mockito.when(modelMapper.map(planTwo,PlanServiceModel.class)).thenReturn(expectedTwo);
////
////        List<PlanServiceModel>expected=List.of(expectedOne,expectedTwo);
////        List<PlanServiceModel>actual=this.planService.getAllPlans();
////
////        Assertions.assertEquals(actual.size(), 2);
////        Assertions.assertEquals(actual,expected);
//
//
        Mockito.when(planRepository.findAll()).thenReturn(List.of(planOne,planTwo));

        Mockito.when(List.of(modelMapper.map(planRepository.findAll(),PlanServiceModel[].class)))
                .thenReturn(List.of(expectedOne,expectedTwo));

        List<PlanServiceModel>expected=List.of(expectedOne,expectedTwo);
        List<PlanServiceModel>actual=this.planService.getAllPlans();

        Assertions.assertEquals(actual.size(), 2);
        Assertions.assertEquals(actual,expected);



    }

    @Test
    public void registerPlanShouldRegisterNewPlan(){
        PlanServiceModel input=this.expectedOne;

        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(user);
        Mockito.when(modelMapper.map(input,Plan.class)).thenReturn(planOne);

        planService.register(input,"valeri");


        Assertions.assertEquals(1,this.planRepository.count());

    }
}
