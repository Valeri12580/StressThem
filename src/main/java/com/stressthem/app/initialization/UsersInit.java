package com.stressthem.app.initialization;

import com.stressthem.app.domain.models.binding.PasswordWrapper;
import com.stressthem.app.domain.models.binding.UserRegisterBindingModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersInit implements CommandLineRunner {
    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public UsersInit(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        if(this.userService.getUsersCount()==0){
            UserRegisterBindingModel user=new UserRegisterBindingModel("Valeri12580",
                    new PasswordWrapper("12345678","12345678"),
                    "valeri125@dir.bg","https://i.ytimg.com/vi/WhIrvsbEJ6Q/maxresdefault.jpg");

            this.userService.register(this.modelMapper.map(user,UserServiceModel.class));

        }


    }
}
