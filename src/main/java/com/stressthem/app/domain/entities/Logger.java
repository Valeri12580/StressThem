package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Logger extends BaseEntity {
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private User author;

    @Column
    @NotNull
    private LocalDateTime time;



}
