package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptocurrencies")
@Getter
@Setter
@NoArgsConstructor

public class Cryptocurrency extends BasePublishEntity {

    public Cryptocurrency(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn, @NotNull String imageUrl) {
        super(title, description, author, addedOn);
        this.imageUrl = imageUrl;
    }

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String imageUrl;


}
