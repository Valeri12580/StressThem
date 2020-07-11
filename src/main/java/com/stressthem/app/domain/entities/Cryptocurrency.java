package com.stressthem.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cryptocurrencies")
@Getter
@Setter
@NoArgsConstructor

public class Cryptocurrency extends BasePublishEntity {

    @Column
    @NotNull
    private String imageUrl;
}
