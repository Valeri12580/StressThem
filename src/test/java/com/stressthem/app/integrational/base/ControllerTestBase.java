package com.stressthem.app.integrational.base;

import com.stressthem.app.AppApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = AppApplication.class)
public class ControllerTestBase {
    @Autowired
    protected MockMvc mockMvc;
}
