package com.fetchrewards.fetchrewardsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetchrewards.fetchrewardsservice.controller.FetchRewardsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FetchRewardsServiceApplicationTests {

	@Autowired
	private FetchRewardsController fetchRewardsController;
	private String baseUri;

	@LocalServerPort
	private int port;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void entireFlowTest() throws Exception {

		baseUri = "http://localhost:" + port + "/fetch-rewards-service/v1";

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");

		// Given fetch rewards participant companies award points

		List<String> payerPointsList = Arrays.asList(
				"{ \"payer\": \"DANNON\", \"points\": 1000, \"timestamp\": \"2020-11-02T14:00:00Z\" }",
				"{ \"payer\": \"UNILEVER\", \"points\": 200, \"timestamp\": \"2020-10-31T11:00:00Z\" }",
				"{ \"payer\": \"DANNON\", \"points\": -200, \"timestamp\": \"2020-10-31T15:00:00Z\" }",
				"{ \"payer\": \"MILLER COORS\", \"points\": 10000, \"timestamp\": \"2020-11-01T14:00:00Z\" }",
				"{ \"payer\": \"DANNON\", \"points\": 300, \"timestamp\": \"2020-10-31T10:00:00Z\" }");

		for (String transaction : payerPointsList) {
			HttpEntity<String> request = new HttpEntity(
					transaction, headers);

			this.restTemplate.postForEntity(baseUri + "/payer/addpoints", request,
					String.class);
		}

		// When a user of Fetch Rewards spends points

		HttpEntity<String> request = new HttpEntity(
				"{ \"points\": 5000 }", headers);

		ResponseEntity<String> spendPointsResponse = this.restTemplate.postForEntity(baseUri + "/customer/spendpoints", request,
						String.class);

		Assertions.assertEquals(spendPointsResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(
				spendPointsResponse.getBody().contains("{\"payer\":\"UNILEVER\",\"points\":-200}"));
		Assertions.assertTrue(
				spendPointsResponse.getBody().contains("{\"payer\":\"MILLER COORS\",\"points\":-4700}"));
		Assertions.assertTrue(
				spendPointsResponse.getBody().contains("{\"payer\":\"DANNON\",\"points\":-100}"));

		// Then Fetch Reward participating companies awarded points will be updated

		ResponseEntity<String> balanceResponse = this.restTemplate
				.getForEntity(baseUri + "/payer/points", String.class);

		Assertions.assertEquals(balanceResponse.getStatusCode(), HttpStatus.OK);
		Assertions
				.assertTrue(balanceResponse.getBody().contains("{\"payer\":\"DANNON\",\"points\":1000}"));
		Assertions
				.assertTrue(balanceResponse.getBody().contains("{\"payer\":\"UNILEVER\",\"points\":0}"));
		Assertions.assertTrue(
				balanceResponse.getBody().contains("{\"payer\":\"MILLER COORS\",\"points\":5300}"));
	}

}
