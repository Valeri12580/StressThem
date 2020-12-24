package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role  extends BaseEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
