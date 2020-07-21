package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.entities.UserActivePlan;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.ChangeRoleException;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.RoleService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private RoleService roleService;
    private PlanService planService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, @Lazy PlanService planService, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.planService = planService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));



        if (this.userRepository.count() == 0) {
            user.setRoles(new HashSet<>(this.roleService.getAllRoles()));
        } else {
            user.setRoles(Set.of(this.roleService.getRoleByName("USER")));
        }

        user.setRegisteredOn(LocalDateTime.now(ZoneId.systemDefault()));
        this.userRepository.save(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(String id) {
        //todo vsichki trqbva da stanat kato tova
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("The user is not available"));
        return this.modelMapper.map(user,UserServiceModel.class);
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

        return this.userRepository.findUserByUsername(username).orElse(null).getUserActivePlan() != null;
    }

    @Override
    public UserServiceModel purchasePlan(String id, String username) {
        User user = this.modelMapper.map(this.getUserByUsername(username), User.class);
        Plan plan = this.planService.getPlanEntity(id);

        if (user.getUserActivePlan() != null) {
            throw new UserPlanActivationException("You have already activate plan!");
        }

        UserActivePlan userActivePlan = new UserActivePlan(plan, plan.getDurationInDays(), plan.getMaxBootsPerDay(),
                LocalDateTime.now(ZoneId.systemDefault()));
        user.setUserActivePlan(userActivePlan);
        this.userRepository.save(user);
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public int getUserAvailableAttacks(String username) {
        User user = this.userRepository.findUserByUsername(username).get();
        if(user.getUserActivePlan()==null){
            return 0;
        }
        return user.getUserActivePlan().getLeftAttacksForTheDay();
    }

    @Override
    public UserServiceModel updateUser(UserServiceModel userServiceModel) {
        User user=this.userRepository.findById(userServiceModel.getId()).orElseThrow(()->new UsernameNotFoundException("The user is not found"));

        modifyUser(userServiceModel,user);

        this.userRepository.save(user);
        return userServiceModel;
    }

    @Override
    public void changeUserRole(String username, String roleName,String type, Principal principal) {
        User user=this.userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        Role role=this.roleService.getRoleByName(roleName);

        if(principal.getName().equals(username)){
            throw new ChangeRoleException("You can only change other user roles");
        }

        if(type.equals("Add")){
            if(user.getRoles().contains(role)){
                throw new ChangeRoleException("This user already have that role");
            }

            user.getRoles().add(role);

        }else if(type.equals("Remove")){
            if(!user.getRoles().contains(role)){
                throw new ChangeRoleException("This user doesn't have that role");
            }

            user.getRoles().remove(role);
        }

        this.userRepository.save(user);


    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return List.of(this.modelMapper.map(this.userRepository.findAll(),UserServiceModel[].class));
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
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
        return this.userRepository.findUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    private void  modifyUser(UserServiceModel modified,User main){
        main.setUsername(modified.getUsername());
        main.setEmail(modified.getEmail());
        main.setImageUrl(modified.getImageUrl());
        main.setPassword(modified.getPassword());

    }
}
