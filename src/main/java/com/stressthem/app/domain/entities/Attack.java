package com.stressthem.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodType;
import java.time.LocalDateTime;

@Table(name = "attack_history")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attack extends BaseEntity {

    @Column
    @NotNull
    private String target;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private MethodType method;


    @Column
    @NotNull
    private int servers;

    @Column
    @NotNull
    private LocalDateTime expiresOn;

    @Column
    @NotNull
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "attacker_id",referencedColumnName = "id")
    private User attacker;
}