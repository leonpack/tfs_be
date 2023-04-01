package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.ChangePasswordDTO;
import com.tfs.demo.tfs_crud_demo.dto.SMSRequestDTO;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms")
public class TwilioRestController {

    private AccountService accountService;
    private TwilioService twilioService;

    @Autowired
    public TwilioRestController(TwilioService theTwilioService, AccountService theAccountService){
        twilioService = theTwilioService;
        accountService = theAccountService;
    }

    @PostMapping("/checkAvailableAccount")
    public Map<String, Object> checkAvailableAccount(@RequestBody SMSRequestDTO smsRequestDTO){
        //check if phone number is valid
        if(!twilioService.isPhoneNumberValid(smsRequestDTO.getPhoneNumber())){
            throw new IllegalArgumentException("Phone number " + smsRequestDTO.getPhoneNumber()+" is not valid");
        }

        Account account = accountService.checkLoginByPhone(smsRequestDTO.getPhoneNumber());
        Map<String, Object> result = new HashMap<>();
        if(account==null){
            throw new RuntimeException("This phone number is not connect with any account");
        }
        else if(account!=null){
            String otp = twilioService.sendSMS(smsRequestDTO);
            result.put("otp", otp);
        }
        return result;
    }

    @PostMapping("/registerOTP")
    public Map<String, Object> getOtpForRegistration(@RequestBody SMSRequestDTO smsRequestDTO){
        //check if phone number is valid
        if(!twilioService.isPhoneNumberValid(smsRequestDTO.getPhoneNumber())){
            throw new IllegalArgumentException("Phone number " + smsRequestDTO.getPhoneNumber()+" is not valid");
        }

        Account account = accountService.checkLoginByPhone(smsRequestDTO.getPhoneNumber());
        Map<String, Object> result = new HashMap<>();
        if(account!=null){
            throw new RuntimeException("This phone number is already linked with another account");
        }
        else if(account==null){
            String otp = twilioService.sendSMS(smsRequestDTO);
            result.put("otp", otp);
        }
        return result;
    }

    @PostMapping("/changepass")
    public Account changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        Account account = accountService.checkLoginByPhone(changePasswordDTO.getPhoneNumber());
        if(account==null){
            throw new RuntimeException("This phone number is not connect with any account");
        }
        account.setPassword(changePasswordDTO.getPassword());
        accountService.saveAccount(account);
        return account;
    }

//    @PostMapping("/requestpass")
//    public void sendOTP(@RequestBody SMSRequestDTO smsRequestDTO){
//        twilioService.sendSMS(smsRequestDTO);
//    }

}
