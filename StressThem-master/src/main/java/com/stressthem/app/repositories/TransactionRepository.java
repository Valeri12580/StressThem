package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Transaction;
import com.stressthem.app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {

    @Query("SELECT t from Transaction  t where  t.user.username=:username")
    Optional<Transaction> getTransactionByUsername(@Param("username")String username);
}
