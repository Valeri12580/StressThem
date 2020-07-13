package com.stressthem.app.domain.models.service;

import com.stressthem.app.domain.entities.AttackHistory;
import com.stressthem.app.domain.entities.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {


    private String username;

    private String password;


    private String email;


    private String imageUrl;


    private LocalDateTime registeredOn;


    private Role role;

    private List<AttackHistory> attacks;
}
