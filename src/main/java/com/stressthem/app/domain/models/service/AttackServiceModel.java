package com.stressthem.app.domain.models.service;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.binding.AttackBindingModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.invoke.MethodType;
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

    private boolean isActive;

    //todo userservicemodel here
    private User attacker;

//    public static String formatTarget(AttackBindingModel attack ){
//        return String.format("%s:%s",attack.getHost(),attack.getPort());
//    }
}
