package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dto.SMSRequestDTO;

public interface TwilioService {

    String sendSMS(SMSRequestDTO smsRequestDTO);

    boolean isPhoneNumberValid(String phoneNumber);
}
