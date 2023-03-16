package com.tfs.demo.tfs_crud_demo.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private final String TWILIO_SID = "AC9bcf2d40ecc62994d25ce04877062f79";
    private final String TWILIO_TOKEN = "33cc07b0cea43762a080540b6c394692";

    private TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioInitializer(TwilioConfiguration theTwilioConfiguration){
        twilioConfiguration = theTwilioConfiguration;
        Twilio.init(TWILIO_SID, TWILIO_TOKEN);
    }

}
