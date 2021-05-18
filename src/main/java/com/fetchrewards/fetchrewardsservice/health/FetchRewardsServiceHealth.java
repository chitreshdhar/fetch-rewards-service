package com.fetchrewards.fetchrewardsservice.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

/**
 * The FetchRewardsServiceHealth class implements HealthIndicator that
 * helps to override the health() method to check for the health of
 * various and future service end points.
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */
@Component
@Slf4j
public class FetchRewardsServiceHealth
        implements HealthIndicator {

    private static final String URL
            = "http://localhost:8080/fetch-rewards-service/v1/";

    /**
     * This method is used to check the health of the CSV Service Endpoint.
     *
     * @return The Health Status of CSV Service Endpoint.
     */
    @Override
    public Health health() {
        // Check if the CSV Service Health is Up or not
        try (Socket socket =
                     new Socket(new java.net.URL(URL).getHost(),8080)) {
        } catch (Exception e) {
            log.warn("Failed to connect to: {}",URL);
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.up().build();
    }

}
