package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Action;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.LoggerRepository;
import com.stressthem.app.services.LoggerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LoggerServiceTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LoggerRepository loggerRepository;

    @InjectMocks
    private LoggerServiceImpl loggerService;

    private User user;


    @BeforeEach
    public void init(){
        this.user=new User();
        user.setUsername("valeri");
        user.setId("id");
    }

    @Test
    public void loggerShouldWorkCorrect(){
        Mockito.when(modelMapper.map(new UserServiceModel(),User.class)).thenReturn(user);
        this.loggerService.log(Action.CREATE,"Test",new UserServiceModel(), LocalDateTime.now());

        Mockito.verify(loggerRepository).save(ArgumentMatchers.any());
    }
}
