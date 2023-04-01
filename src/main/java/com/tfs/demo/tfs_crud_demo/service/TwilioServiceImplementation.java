package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dto.SMSRequestDTO;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Pattern;

@Service
public class TwilioServiceImplementation implements TwilioService{

    private final String trialNumber = "+15074364252";
    Random rnd = new Random();


    @Override
    public String sendSMS(SMSRequestDTO smsRequestDTO) {
        int otp = rnd.nextInt((999999-100000)+1) + 100000;
        if(isPhoneNumberValid(smsRequestDTO.getPhoneNumber())){
            PhoneNumber from = new PhoneNumber(trialNumber);
            PhoneNumber to = new PhoneNumber("+84"+smsRequestDTO.getPhoneNumber());
            String message = "Mã OTP của bạn là " + otp;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
        } else {
             throw new IllegalArgumentException("Phone number " +smsRequestDTO.getPhoneNumber()+ " is not valid");
        }
        return String.valueOf(otp);
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber){
        Pattern ptrn = Pattern.compile("(0/84)?[0-9]{9}");
        Pattern ptrn2 = Pattern.compile("(0/84)?[0-9]{10}");

        if(ptrn.matcher(phoneNumber).matches() || ptrn2.matcher(phoneNumber).matches()){
            return true;
        }
        else
        return false;
    }


}
