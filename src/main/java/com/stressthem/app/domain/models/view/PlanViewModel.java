package com.stressthem.app.domain.models.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NotNull
public class PlanViewModel {

    private String id;

    private String type;

    private BigDecimal price;

    private int durationInDays;

    private double maxBootTimeInSeconds;

    private int maxBootsPerDay;

    private int servers;
}
