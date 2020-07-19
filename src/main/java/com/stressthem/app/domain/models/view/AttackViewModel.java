package com.stressthem.app.domain.models.view;

import com.stressthem.app.domain.MethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AttackViewModel {
    private String host;
    private String port;
    private MethodType method;
    private int servers;
    private LocalDateTime expiresOn;
}
