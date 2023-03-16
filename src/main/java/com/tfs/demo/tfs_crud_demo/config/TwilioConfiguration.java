package com.tfs.demo.tfs_crud_demo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    private String accountSid;
    private String authToken;
    private String trialNumber;

    public TwilioConfiguration(){

    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }
}
