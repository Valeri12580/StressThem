package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Cryptocurrency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptocurrencyRepository  extends JpaRepository<Cryptocurrency,String> {
    Cryptocurrency findByTitle(String title);
}
