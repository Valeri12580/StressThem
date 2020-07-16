package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Plan;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.PlanServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.repositories.PlanRepository;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {
    private ModelMapper modelMapper;
    private PlanRepository planRepository;
    private UserService userService;

    @Autowired
    public PlanServiceImpl(ModelMapper modelMapper, PlanRepository planRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.planRepository = planRepository;
        this.userService = userService;
    }

    @Override
    public PlanServiceModel register(PlanServiceModel planServiceModel,String username) {

        UserServiceModel author=this.userService.getUserByUsername(username);
        planServiceModel.setAuthor(author);

        Plan plan=this.modelMapper.map(planServiceModel,Plan.class);

        planRepository.save(plan);

        return this.modelMapper.map(plan,PlanServiceModel.class);
    }

    @Override
    public List<PlanServiceModel> getAllPlans() {
        return Arrays.stream(this.modelMapper.map(this.planRepository.findAll(), PlanServiceModel[].class))
                .sorted(Comparator.comparing(PlanServiceModel::getPrice)).collect(Collectors.toList());

    }
}
