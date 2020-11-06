package com.stressthem.app.integrational.base;

import com.stressthem.app.AppApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = AppApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTestBase {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected MockMvc mockMvc;

//   @BeforeAll
//    public void setUp(){
//        mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
}
