package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.UserActivePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserActivePlanRepository extends JpaRepository<UserActivePlan,String> {

    @Transactional
    @Modifying
    @Query("update UserActivePlan uap set uap.leftDays=uap.leftDays-1")
    void decreaseLeftDays();

    @Transactional
    @Modifying
    @Query("delete FROM  UserActivePlan uap WHERE uap.leftDays=:leftDays")
    void deleteAllExpiredPlans(@Param("leftDays") int leftDays);

    @Transactional
    @Modifying
    @Query("update UserActivePlan uap set uap.leftAttacksForTheDay=(select p.maxBootsPerDay from Plan p where uap.plan.id=p.id)")
    void refreshDailyAttacks();
}
