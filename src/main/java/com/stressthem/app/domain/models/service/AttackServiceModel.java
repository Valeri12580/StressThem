package com.stressthem.app.domain.models.service;

import com.stressthem.app.domain.MethodType;
import com.stressthem.app.domain.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class AttackServiceModel extends BaseServiceModel {

    private String host;
    private String port;


    private MethodType method;

    private int servers;


    private LocalDateTime expiresOn;



    //todo userservicemodel here
    private User attacker;

//    public static String formatTarget(AttackBindingModel attack ){
//        return String.format("%s:%s",attack.getHost(),attack.getPort());
//    }
}
