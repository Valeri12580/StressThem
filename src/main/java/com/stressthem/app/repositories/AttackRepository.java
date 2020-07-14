package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Attack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttackRepository extends JpaRepository<Attack,String> {
}
