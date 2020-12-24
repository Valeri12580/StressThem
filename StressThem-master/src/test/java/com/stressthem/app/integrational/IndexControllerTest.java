package com.stressthem.app.integrational;


import com.stressthem.app.integrational.base.ControllerTestBase;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IndexControllerTest extends ControllerTestBase {

    @Test
    public void indexControllerShouldReturnCorrectView() throws Exception {
        super.mockMvc.perform(get("/index"))
                .andExpect(view().name("index"));

    }

}
