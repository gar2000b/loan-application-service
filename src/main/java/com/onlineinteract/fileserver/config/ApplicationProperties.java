package com.onlineinteract.fileserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

	@Value("${standup.new.service}")
	private String socialInsuranceWorkflowUrl;

	public String getSocialInsuranceWorkflowUrl() {
		return socialInsuranceWorkflowUrl;
	}

	public void setStandupNewService(String socialInsuranceWorkflowUrl) {
		this.socialInsuranceWorkflowUrl = socialInsuranceWorkflowUrl;
	}
}
