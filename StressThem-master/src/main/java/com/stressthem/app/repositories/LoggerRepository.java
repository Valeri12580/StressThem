package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepository extends JpaRepository<Logger,String> {
}
