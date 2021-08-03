package com.onlineinteract.fileserver.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.onlineinteract.fileserver.config.ApplicationProperties;

@Controller
@EnableAutoConfiguration
public class LoanApplicationController {

	@Autowired
	ApplicationProperties applicationProperties;

	/**
	 * curl -i -H "Content-Type:application/json" -X POST --data
	 * '{"CustomerId":"1234","Location":"Toronto","AmountRequested":"1000.00","Denomination":"CAD","LoanPeriod":"2
	 * Years"}' http://localhost:9080/application
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "text/plain", value = "/application")
	@ResponseBody
	public ResponseEntity<String> processApplication(@RequestBody Map<String, String> application,
			@RequestHeader HttpHeaders incomingHeaders) {
		System.out.println("\nProcessing Application with customer ID: " + application.get("CustomerId"));
		System.out.println("Location: " + application.get("Location"));
		System.out.println("Amount Requested: " + application.get("AmountRequested"));
		System.out.println("Denomination: " + application.get("Denomination"));
		System.out.println("Loan Period: " + application.get("LoanPeriod"));

		String socialInsuranceWorkflowUrl = applicationProperties.getSocialInsuranceWorkflowUrl();
		System.out.println("socialInsuranceWorkflowUrl: " + socialInsuranceWorkflowUrl);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.addAll(incomingHeaders);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(
				"{\"CustomerId\":\"" + application.get("CustomerId") + "\"}", headers);
		ResponseEntity<String> response = restTemplate.postForEntity(socialInsuranceWorkflowUrl, request, String.class);
		System.out.println("Response from Social Insurance Workflow Service: " + response.getBody());

		if (response.getStatusCodeValue() == HttpStatus.OK.value() && logCustomer(response.getBody()) == 1) {
			return new ResponseEntity<>("Application processed OK", response.getStatusCode());
		} else {
			return new ResponseEntity<>(
					"There was a problem processing the payload from the Social Insurance Workflow body: "
							+ response.getBody(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	protected Integer logCustomer(String body) {
		try {
			Integer customerId = Integer.valueOf(body.substring(body.indexOf(": ") + 2));
			System.out.println("Logging customer with id: " + customerId);
			return customerId;
		} catch (Throwable e) {
			return -1;
		}
	}
}
