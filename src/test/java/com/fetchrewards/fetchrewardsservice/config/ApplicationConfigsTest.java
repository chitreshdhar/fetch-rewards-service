package com.fetchrewards.fetchrewardsservice.config;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * The ApplicationConfigsTest class tests the Application level
 * service parameters are paased correctly
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@SuppressWarnings("ALL")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ApplicationConfigs.class)
@TestPropertySource("classpath:application.yml")
public class ApplicationConfigsTest {
    @Autowired
    private ApplicationConfigs applicationConfigs;

    @Test
    void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {
        //Assertions.assertEquals("stage", applicationConfigs.getEnvironment());
        //Check if the environment variable is passed or not
        Assertions.assertNotNull(applicationConfigs.getEnvironment());
    }
}
