package com.stressthem.app.domain.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_active_plan")
@Getter
@Setter
@NoArgsConstructor
public class UserActivePlan  extends BaseEntity{
    public UserActivePlan(Plan plan, @Positive @NotNull int leftDays, @PositiveOrZero @NotNull int leftAttacksForTheDay, LocalDateTime startedOn) {
        this.plan = plan;
        this.leftDays = leftDays;
        this.leftAttacksForTheDay = leftAttacksForTheDay;
        this.startedOn = startedOn;
    }

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "plan_id",referencedColumnName = "id")
    private Plan plan;


    @Column
    @Positive
    @NotNull
    private int leftDays;

    @Column
    @PositiveOrZero(message = "Your daily limit is reached!")
    @NotNull
    private int leftAttacksForTheDay;

    @Column
    private LocalDateTime startedOn;



}
