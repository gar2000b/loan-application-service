package com.onlineinteract.fileserver.rest;

import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@EnableAutoConfiguration
public class LoanApplicationController {

	/**
	 * curl -i -H "Content-Type:application/json" -X POST --data '{"CustomerId":"1234","Location":"Toronto","AmountRequested":"1000.00","Denomination":"CAD","LoanPeriod":"2 Years"}' http://localhost:9080/application
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "text/plain", value = "/application")
	@ResponseBody
	public ResponseEntity<String> processApplication(@RequestBody Map<String, String> application) {
		System.out.println("\nProcessing Application with customer ID: " + application.get("CustomerId"));
		System.out.println("Location: " + application.get("Location"));
		System.out.println("Amount Requested: " + application.get("AmountRequested"));
		System.out.println("Denomination: " + application.get("Denomination"));
		System.out.println("Loan Period: " + application.get("LoanPeriod"));

		String socialInsuranceWorkflowUrl = "http://localhost:9081/verifySin";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(
				"{\"CustomerId\":\"" + application.get("CustomerId") + "\"}", headers);
		ResponseEntity<String> response = restTemplate.postForEntity(socialInsuranceWorkflowUrl, request, String.class);
		System.out.println("Response from Social Insurance Workflow Service: " + response.getBody());

		return new ResponseEntity<>("Application processed OK", response.getStatusCode());
	}
}
