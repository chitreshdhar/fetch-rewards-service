package com.fetchrewards.fetchrewardsservice.service;


import java.time.LocalDateTime;
import java.util.List;

import com.fetchrewards.fetchrewardsservice.repository.PayerRepository;
import com.fetchrewards.fetchrewardsservice.repository.UserPointsRepository;
import com.fetchrewards.fetchrewardsservice.request.UserPointsInput;
import com.fetchrewards.fetchrewardsservice.response.Payer;
import com.fetchrewards.fetchrewardsservice.response.Points;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ComputeUserPointsTest {

    @Autowired
    private ComputeUserPoints computeUserPoints;
    @Autowired
    private UserPointsRepository userPointsRepository;
    @Autowired
    private PayerRepository payerRepository;

    @Test
    public void addPoints() {
        UserPointsInput userPointsInput = new UserPointsInput(null, "DANNON", 200, LocalDateTime.now());
        UserPointsInput outTransaction = computeUserPoints
                .addPoints(userPointsInput);

        Assertions.assertEquals(userPointsInput, outTransaction);
    }

    @Test
    public void getAllPayerBalances() {

        computeUserPoints.addPoints(new UserPointsInput(null, "DANNON", 200, LocalDateTime.now()));
        List<Payer> allPayerBalances = computeUserPoints.getAllPayerBalances();
        Assertions.assertEquals(allPayerBalances.get(0), new Payer(1L, "DANNON", 200));
    }

    @Test
    public void getAllTransactions() {

        UserPointsInput inTransaction = new UserPointsInput(null, "DANNON", 200, LocalDateTime.now());
        computeUserPoints.addPoints(inTransaction);
        List<UserPointsInput> allTransactions = computeUserPoints.getAllTransactions();
        Assertions.assertEquals(allTransactions.get(0).getPayer(), "DANNON");
        Assertions.assertEquals(allTransactions.get(0).getPoints(), 200);
    }

    @Test
    public void spendPoints() {
        UserPointsInput inTransaction = new UserPointsInput(null, "DANNON", 200, LocalDateTime.now());
        computeUserPoints.addPoints(inTransaction);
        Payer pointsSpentForPayer = computeUserPoints.spendPoints(new Points(100)).get(0);
        Assertions.assertEquals(pointsSpentForPayer, new Payer(null, "DANNON", -100));
        Payer balancesByPayer = computeUserPoints.getAllPayerBalances().get(0);
        Assertions.assertEquals(balancesByPayer, new Payer(1L, "DANNON", 100));
    }

}
