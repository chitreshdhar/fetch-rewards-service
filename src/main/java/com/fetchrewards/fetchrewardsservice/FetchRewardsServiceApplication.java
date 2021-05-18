package com.fetchrewards.fetchrewardsservice;

import com.fetchrewards.fetchrewardsservice.config.ApplicationConfigs;
import com.fetchrewards.fetchrewardsservice.config.FetchRewardsServiceConfig;
import com.fetchrewards.fetchrewardsservice.service.ComputeUserPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * The FetchRewardsServiceApplication class is the main class and entry point of the application
 *
 * @author  Chitresh Dhar
 * @project Fetch Rewards Service
 */

@SpringBootApplication
public class FetchRewardsServiceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(FetchRewardsServiceApplication.class);
	private final FetchRewardsServiceConfig fetchRewardsServiceConfig;
	private final ApplicationConfigs applicationConfigs;

	@Autowired
	private ComputeUserPoints computeUserPoints;

	public FetchRewardsServiceApplication(FetchRewardsServiceConfig fetchRewardsServiceConfig, ApplicationConfigs applicationConfigs
			,ComputeUserPoints computeUserPoints) {
		this.fetchRewardsServiceConfig = fetchRewardsServiceConfig;
		this.applicationConfigs = applicationConfigs;
		this.computeUserPoints = computeUserPoints;
	}

	public static void main(String[] args) {
		SpringApplication fetchRewardsServiceApplication = new SpringApplication(FetchRewardsServiceApplication.class);
		fetchRewardsServiceApplication.addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
		fetchRewardsServiceApplication.run(args);
	}

	public void run(String... args) throws Exception {
		log.info("ACTIVE profile is {}", applicationConfigs.getEnvironment());
		//Logging Information about the running application with LOGGER info on StartUp
		log.info("Fetch Rewards Service Application running at {} with port number {} and Path Prefix as {}",
		fetchRewardsServiceConfig.getHostname() , fetchRewardsServiceConfig.getPort() , 
		fetchRewardsServiceConfig.getPathPrefix());

				//computeUserPoints.writeANewCSV();
	}

}
