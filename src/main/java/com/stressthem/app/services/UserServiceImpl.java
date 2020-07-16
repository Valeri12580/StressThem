package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.RoleService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private RoleService roleService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getImageUrl()==null || user.getImageUrl().equals("")){
            user.setImageUrl("\\assets\\img\\default_user_icon.png");
        }

        if(this.userRepository.count()==0){
            user.setRoles(new HashSet<>(this.roleService.getAllRoles()));
        }else{
            user.getRoles().add(this.roleService.getRoleByName("USER"));
        }

        user.setRegisteredOn(LocalDateTime.now());
        this.userRepository.save(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public long getUsersCount() {

        return this.userRepository.count();
    }

    @Override
    public boolean hasUserActivePlan(String username) {

        return this.userRepository.findUserByUsername(username).orElse(null).getUserActivePlan()!=null;
    }

    @Override
    public UserServiceModel purchasePlan(PlanServiceModel planServiceModel, String username) {
        User user=this.modelMapper.map(this.getUserByUsername(username),User.class);
        Plan plan=this.modelMapper.map(planServiceModel,Plan.class);
        if(user.getUserActivePlan()!=null){
            //todo throw new error
        }
        //todo dobavi cascade type za da moje kogato se iztrie usera,da mu se trie i aktivniq plan
        UserActivePlan userActivePlan=new UserActivePlan();
        user.setUserActivePlan();
        return null;

    }

    @Override
    public UserServiceModel getUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(s).orElseThrow(()->new UsernameNotFoundException("Invalid credentials"));
    }
}
