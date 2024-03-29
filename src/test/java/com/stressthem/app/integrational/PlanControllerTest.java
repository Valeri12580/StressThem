package com.stressthem.app.integrational;

import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.PlanRepository;
import com.stressthem.app.repositories.TransactionRepository;
import com.stressthem.app.repositories.UserActivePlanRepository;
import com.stressthem.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlanControllerTest extends ControllerTestBase {


    @Autowired
    private PlanRepository planRepository;


    @Autowired
    private UserActivePlanRepository userActivePlanRepository;


    @Test
    @WithMockUser(username = "valeri12580")
    public void testALlPlans() throws Exception {


        super.mockMvc.perform(get("/plans"))
                .andExpect(view().name("pricing"))
                .andExpect(model().attributeExists("plans"))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testConfirmOrder() throws Exception {


        String planId = planRepository.findAll().get(0).getId();

        super.mockMvc.perform(get("/plans/confirm/{id}", planId))
                .andExpect(view().name("confirm-order"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plan", "crypto", "id"));

    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testPostConfirmOrderShouldThrowError_UserHasPlan() throws Exception {

        String planId = planRepository.findAll().get(0).getId();

        super.mockMvc.perform(post("/plans/confirm/{id}", planId).param("cryptocurrency", "Bitcoin").with(csrf()));


        super.mockMvc.perform(post("/plans/confirm/{id}", planId).param("cryptocurrency", "Bitcoin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plans/confirm/" + planId + "?favicon=%5Cassets%5Cimg%5Cfavicon.png"))
                .andExpect(flash().attributeExists("activationError"));


    }

    @Test
    @WithMockUser(username = "valeri12580")
    public void testPostConfirmOrderShouldWork() throws Exception {

        String planId = planRepository.findAll().get(0).getId();

        super.mockMvc.perform(post("/plans/confirm/{id}", planId).param("cryptocurrency", "Bitcoin").with(csrf())).
                andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home/launch?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1, userActivePlanRepository.count());

    }


    //todo fix
//    @Test
//    @WithMockUser(username = "valeri12580",roles = {"ROOT"})
//    public void testPlanDeletion() throws Exception {
//
//        String planId = planRepository.findAll().get(0).getId();
//
//        super.mockMvc.perform(get("/plans/delete/{id}",planId))
//             .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/plans?favicon=%5Cassets%5Cimg%5Cfavicon.png"));
//
//        assertEquals(1,planRepository.count());
//    }


}
