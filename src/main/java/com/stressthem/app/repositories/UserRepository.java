package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);


}
