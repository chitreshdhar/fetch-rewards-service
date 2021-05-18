package com.fetchrewards.fetchrewardsservice.service;

import com.fetchrewards.fetchrewardsservice.response.Payer;
import com.fetchrewards.fetchrewardsservice.response.Points;
import com.fetchrewards.fetchrewardsservice.request.UserPointsInput;

import java.util.List;

public interface FetchRewardsService {

    List<Payer> spendPoints(Points points);

    UserPointsInput addPoints(UserPointsInput newTransaction);

    List<UserPointsInput> getAllTransactions();

    List<Payer> getAllPayerBalances();
}

