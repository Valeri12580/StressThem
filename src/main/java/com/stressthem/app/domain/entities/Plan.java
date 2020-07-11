package com.stressthem.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "plans")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Plan extends BaseEntity {

    @Column
    @NotNull
    private String type;

    @Column
    @Positive
    @NotNull
    private BigDecimal price;

    @Column
    @NotNull
    @Positive
    private int durationInDays;

    @Column
    @NotNull
    @Positive
    private double maxBootTimeInSeconds;

    @Column
    @NotNull
    @Positive
    private int maxBootsPerDay;

    @Column
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "creator_id",referencedColumnName = "id")
    private User author;



}
