package com.stressthem.app.services;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }
}
