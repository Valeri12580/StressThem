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

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor

public class Article  extends BasePublishEntity{

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    public Article(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn, String imageUrl) {
        super(title, description, author, addedOn);
        this.imageUrl = imageUrl;
    }
}
