package com.stressthem.app.domain.models.service;

import com.stressthem.app.domain.entities.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PlanServiceModel extends BaseServiceModel {


    @NonNull
    private String type;

    @NonNull
    private BigDecimal price;
    @NonNull
    private int durationInDays;

    @NonNull
    private double maxBootTimeInSeconds;

    @NonNull
    private int maxBootsPerDay;

    @NonNull
    private LocalDateTime createdOn;


    private UserServiceModel author;

}
