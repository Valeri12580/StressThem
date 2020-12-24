package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Transaction extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "plan_id",referencedColumnName = "id")
    private Plan plan;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "payment_method_id",referencedColumnName = "id")
    private Cryptocurrency paymentMethod;

    @NotNull
    @Column
    private LocalDateTime createdOn;


}
