package com.fetchrewards.fetchrewardsservice.controller;

import com.fetchrewards.fetchrewardsservice.request.UserPointsInput;

import com.fetchrewards.fetchrewardsservice.response.Payer;
import com.fetchrewards.fetchrewardsservice.response.Points;
import com.fetchrewards.fetchrewardsservice.service.ComputeUserPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The FetchRewardsController class controls the Fetch Rewards Service
 * to call methods for getting the user points, adding points to payer and calculating spends
 * which is an API wrapper around the call to the specific API service that is configurable in application.yml
 *
 * @author Chitresh Dhar
 * @project Fetch Rewards Service
 */


@RestController
public class FetchRewardsController {
    private static final Logger logger =
            LoggerFactory.getLogger(FetchRewardsController.class);

    @Value("${service.port}")
    int servicePort;

    @Value("${service.hostname}")
    String serviceHostName;

    @Value("${service.path-prefix}")
    String servicePathPrefix;

    @Autowired
    private ComputeUserPoints computeUserPoints;

    @GetMapping("/")
    public String getHomePageResponse() {
        logger.info("Service Started and Hitting root");

        return "Greetings Fetch Rewards User! You have landed on the root page";
    }

    @GetMapping(value = "{servicePathPrefix}/**", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRootServiceUsage() {
        logger.info("Getting the service endpoint info...");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Check Service Health --> http://localhost:8080/actuator/health \n" +
                        "Get all Payer Balances --> http://localhost:8080/fetch-rewards-service/v1/payer/points \n" +
                        "Add Points for specific Payer --> http://localhost:8080/fetch-rewards-service/v1/payer/addpoints \n" +
                        "Spend Points for a customer --> http://localhost:8080/fetch-rewards-service/v1/customer/spendpoints \n");
    }

    @GetMapping("{servicePathPrefix}/**/payer/points")
    List<Payer> allPayerBalances() {
        return computeUserPoints.getAllPayerBalances();
    }

    @PostMapping(path = "{servicePathPrefix}/**/payer/addpoints", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserPointsInput addPoints(@RequestBody UserPointsInput userPointsInput) {
        return computeUserPoints.addPoints(userPointsInput);
    }

    @PostMapping(path = "{servicePathPrefix}/**/customer/spendpoints", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<Payer> spendPoints(@RequestBody Points points) {
        return computeUserPoints.spendPoints(points);
    }

}
