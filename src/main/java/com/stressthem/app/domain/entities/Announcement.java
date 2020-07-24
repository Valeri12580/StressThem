package com.stressthem.app.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity
public class Announcement extends BasePublishEntity {
    public Announcement(@NotNull String title, @NotNull String description, @NotNull User author, @NotNull LocalDateTime addedOn) {
        super(title, description, author, addedOn);
    }
}
