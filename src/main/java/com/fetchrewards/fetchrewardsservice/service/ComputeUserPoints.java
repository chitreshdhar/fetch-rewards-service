package com.fetchrewards.fetchrewardsservice.service;

import com.fetchrewards.fetchrewardsservice.repository.PayerRepository;
import com.fetchrewards.fetchrewardsservice.repository.UserPointsRepository;
import com.fetchrewards.fetchrewardsservice.request.UserPointsInput;
import com.fetchrewards.fetchrewardsservice.response.Payer;
import com.fetchrewards.fetchrewardsservice.response.Points;

import com.fetchrewards.fetchrewardsservice.controller.FetchRewardsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The ComputeUserPoints class creates the output CSV File
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@Service
public class ComputeUserPoints implements FetchRewardsService{

    @Autowired
    private FetchRewardsController fetchRewardsController;

    @Autowired
    private UserPointsRepository userPointsRepository;
    @Autowired
    private PayerRepository payerRepository;

    /**
     * @param points
     * @return the amount of points spent for each Fetch Reward participating company
     */
    @Override
    public List<Payer> spendPoints(Points points) {

        List<UserPointsInput> sortedAwardedPointsByDate = userPointsRepository.getMostRecentPoints();
        int pointsToSubtract = points.getPoints();
        SpentPointsUtil spentPointsUtil = new SpentPointsUtil();

        for (UserPointsInput userPointsInput : sortedAwardedPointsByDate) {

            if (pointsToSubtract == 0) {
                break;
            } else if (userPointsInput.getPoints() < pointsToSubtract) {
                pointsToSubtract -= userPointsInput.getPoints();
                spentPointsUtil.subtractPoints(userPointsInput.getPayer(), userPointsInput.getPoints());
                userPointsRepository.removeTransaction(userPointsInput.getId());
            } else {
                int updatePoints = userPointsInput.getPoints() - pointsToSubtract;
                spentPointsUtil.subtractPoints(userPointsInput.getPayer(), pointsToSubtract);
                userPointsRepository.updateTransactionsPoints(updatePoints, userPointsInput.getId());
                pointsToSubtract = 0;
            }
        }

        subtractPointsFromPayerBalances(spentPointsUtil);

        return spentPointsUtil.getSubtractedPointsForPayers();
    }

    /**
     * @param spentPointsUtil contains the spent points for each Fetch Reward participating company when a consumer
     *           spends points.
     */
    private void subtractPointsFromPayerBalances(SpentPointsUtil spentPointsUtil) {
        for (Map.Entry<String, Integer> entry : spentPointsUtil.getSubtractedPoints().entrySet()) {
            payerRepository.updatePayersBalance(entry.getKey(), entry.getValue());
        }
    }

    /**
     * This method will add awarded points to a given Fetch Rewards participating companies balance as
     * well as saving it in the overall transaction repo.
     *
     * @param awardedPoints
     * @return the awarded points that were sent.
     */
    @Override
    public UserPointsInput addPoints(UserPointsInput awardedPoints) {

        if (!payerRepository.findPayer(awardedPoints.getPayer()).isEmpty()) {
            payerRepository.updatePayersBalance(awardedPoints.getPayer(), awardedPoints.getPoints());
        } else {
            payerRepository.save(new Payer(awardedPoints.getPayer(), awardedPoints.getPoints()));
        }

        return userPointsRepository.save(awardedPoints);
    }

    @Override
    public List<UserPointsInput> getAllTransactions() {
        return userPointsRepository.findAll();
    }

    @Override
    public List<Payer> getAllPayerBalances() {
        return payerRepository.findAll();
    }


}
