package com.fetchrewards.fetchrewardsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The FetchRewardsServiceConfig class configures the CSV Service config and
 * the specific service parameters can be accessed here and in the
 * service controllers
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "service")
public class FetchRewardsServiceConfig {

    private String hostname;
    private int port;
    private String pathPrefix;
    private String apiUrl;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
