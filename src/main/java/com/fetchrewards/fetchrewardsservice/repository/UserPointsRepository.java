package com.fetchrewards.fetchrewardsservice.repository;


import java.util.List;
import javax.transaction.Transactional;

import com.fetchrewards.fetchrewardsservice.request.UserPointsInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPointsRepository extends JpaRepository<UserPointsInput, Long> {

    @Query("SELECT u FROM UserPointsInput u WHERE u.payer = :payer")
    List<UserPointsInput> findByPayer(@Param("payer") String payer);

    @Query("SELECT u FROM UserPointsInput u ORDER BY u.timestamp ASC")
    List<UserPointsInput> getMostRecentPoints();

    @Transactional
    @Modifying
    @Query("UPDATE UserPointsInput u SET u.points = :updatePoints WHERE u.id = :id")
    void updateTransactionsPoints(@Param("updatePoints") int updatedPoints,
                                  @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE UserPointsInput u WHERE u.id = :id")
    void removeTransaction(@Param("id") Long id);
}
