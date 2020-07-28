package com.stressthem.app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true)
    @NotNull
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @Column(columnDefinition = "TEXT")
    //todo Data truncation: Data too long for column 'image_url' at row 1
    private String imageUrl;

    //todo try to automate the registration time
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")//todo fix this,doesnt show the correct registration time
    private LocalDateTime registeredOn;

    @OneToOne(mappedBy = "user")
    private UserActivePlan userActivePlan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;


    @OneToMany(mappedBy = "attacker",cascade = CascadeType.ALL)
    private List<Attack> attacks=new ArrayList<>();


    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Plan>plans;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
