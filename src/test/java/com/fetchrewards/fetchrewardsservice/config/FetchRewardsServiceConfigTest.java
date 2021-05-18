package com.fetchrewards.fetchrewardsservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The ApplicationConfigsTest class tests the Application level
 * service parameters are passed correctly
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FetchRewardsServiceConfig.class)
@EnableConfigurationProperties
public class FetchRewardsServiceConfigTest {
    @Autowired
    private FetchRewardsServiceConfig FetchRewardsServiceConfig;

    @Test
    void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {
        //Check if the environment variable is passed or not
        Assertions.assertNotNull(FetchRewardsServiceConfig.getHostname());
        Assertions.assertNotNull(FetchRewardsServiceConfig.getPort());
        Assertions.assertNotNull(FetchRewardsServiceConfig.getPathPrefix());

        //Check if the right values are being passed
        Assertions.assertEquals("localhost",FetchRewardsServiceConfig.getHostname());
        Assertions.assertEquals(8080,FetchRewardsServiceConfig.getPort());
        Assertions.assertEquals("/fetch-rewards-service/v1/",FetchRewardsServiceConfig.getPathPrefix());
    }
}

