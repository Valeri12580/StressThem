package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Column
    @NotNull
    @Min(value = 1,message = "The rating must be >= 1")
    @Max(value = 5,message = "The rating must be <=5")
    private int rate;

    @Column
    @NotNull
    @Size(min = 10,max = 100,message = "The size must be between 10 and 100 characters")
    private String description;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User author;
}
