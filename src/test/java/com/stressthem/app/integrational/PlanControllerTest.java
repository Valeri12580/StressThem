package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.view.PlanViewModel;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;

public class PlanControllerTest extends ControllerTestBase {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PlanService planService;

    @MockBean
    private CryptocurrencyService cryptocurrencyService;

    @MockBean
    private UserService userService;


    @Test
    public void testALlPlans() throws Exception {
        Mockito.when(planService.getAllPlans()).thenReturn(List.of(new PlanServiceModel()));
        Mockito.when(modelMapper.map(planService.getAllPlans(), PlanViewModel[].class)).
                thenReturn(new PlanViewModel[]{new PlanViewModel(),new PlanViewModel()});

        super.mockMvc.perform(get("/plans"))
                .andExpect(view().name("pricing")).andExpect(status().isOk())
        .andExpect(model().attributeExists("plans"));
    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testConfirmOrder() throws Exception {
        CryptocurrencyServiceModel cryptoOne=new CryptocurrencyServiceModel();
        cryptoOne.setTitle("bitcoin");
        CryptocurrencyServiceModel cryptoTwo=new CryptocurrencyServiceModel();
        cryptoOne.setTitle("ethereum");

        Mockito.when(planService.getPlanById("1")).thenReturn(new PlanServiceModel());
        Mockito.when(this.cryptocurrencyService.getAllCryptocurrencies()).thenReturn(List.of(cryptoOne,cryptoTwo));

//        SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        super.mockMvc.perform(get("/plans/confirm/{id}",1))
                .andExpect(view().name("confirm-order"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plan","crypto","id"));

    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testPostConfirmOrderShouldThrowError_UserHasPlan() throws Exception {
        Mockito.when(userService.purchasePlan("1","valeri12580","bitcoin"))
                .thenThrow(new UserPlanActivationException("You have already activate plan!"));


        super.mockMvc.perform(post("/plans/confirm/{id}",1).param("cryptocurrency","bitcoin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("activationError"))
        .andExpect(redirectedUrl("/plans/confirm/1?favicon=%5Cassets%5Cimg%5Cfavicon.png"));


    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testPostConfirmOrderShouldWork() throws Exception {

        Mockito.when(userService.purchasePlan("1","valeri12580","bitcoin"))
        .thenReturn(null);


        super.mockMvc.perform(post("/plans/confirm/{id}",1).param("cryptocurrency","bitcoin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home/launch?favicon=%5Cassets%5Cimg%5Cfavicon.png"));
    }

//    @Test
//    @WithMockUser(username = "valeri12580")
//    public void testDeletePlan() throws Exception {
//        Mockito.doNothing().when(planService).deletePlanById("1");
//        mockMvc.perform(get("/plans/delete/{id}","1"))
//                .andExpect(redirectedUrl("/plans/?favicon=%5Cassets%5Cimg%5Cfavicon.png"));
//        Mockito.verify(planService).deletePlanById("1");
//
//    }


}
