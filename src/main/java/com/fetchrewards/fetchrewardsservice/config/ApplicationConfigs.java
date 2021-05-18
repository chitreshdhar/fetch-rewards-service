package com.fetchrewards.fetchrewardsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The ApplicationConfigs class configures the Service config and
 * the service parameters can be accessed here and in the service controllers on project level
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@SuppressWarnings("ALL")
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfigs {
    private String environment;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
