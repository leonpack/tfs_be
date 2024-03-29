package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.LoginDTO;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AccountRestController {

    private final AccountService accountService;

    @Autowired
    public AccountRestController(AccountService theAccountService){
        accountService = theAccountService;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountByAccountId(@PathVariable String accountId){
        Account theAccount = accountService.getAccountById(accountId);
        if(theAccount==null){
            throw new RuntimeException("Account with id - " +accountId+ " not found!");
        }
        return theAccount;
    }

//    @GetMapping ("/accountsByUsername/{accountId}&{password}")
//    public Customer checkLogin(@PathVariable String accountId, @PathVariable String password){
//        Account theAccount = accountService.getAccountById(accountId);
//        if(theAccount==null){
//            return null;
//        }
//        if(!password.equals(theAccount.getPassword())){
//            throw new RuntimeException("Wrong password");
//        }
//        return theAccount.getTheCustomer();
//    }

    @PostMapping("/user/login")
    public Customer loginByUserInformation(@RequestBody LoginDTO userLogin){
        Account theAccount = accountService.getAccountById(userLogin.getUsername());
        if(theAccount == null){
            throw new RuntimeException("Account not found!");
        }
        if(!theAccount.getPassword().equals(userLogin.getPassword())){
            throw new RuntimeException("Password is incorrect!");
        }
        if(!theAccount.getRoleId().toString().equals("5")){
            throw new RuntimeException("This type of account can't login to the system");
        }
        if(theAccount.getTheCustomer()==null){
            throw new RuntimeException("Login success but this customer need to update their information!");
        }
        return theAccount.getTheCustomer();
    }

    @GetMapping("/accountsByPhone/{phoneNumber}&{password}")
    public Customer checkLoginByPhone(@PathVariable String phoneNumber,@PathVariable String password){
        Account theAccount = accountService.checkLoginByPhone(phoneNumber);
        if(theAccount == null){
            throw new RuntimeException("Account not found!");
        }
        if(!theAccount.getPassword().equals(password)){
            throw new RuntimeException("Wrong password");
        }
        if(!theAccount.getRoleId().toString().equals("5")){
            throw new RuntimeException("This account can't be used here!");
        }
        return theAccount.getTheCustomer();
    }

//    @PostMapping("/accounts")
//    public String addNewAccount(@RequestBody Account theAccount){
//        if(!accountService.CheckDuplicateAccountId(theAccount.getAccountId())){
//            return "Duplicate with accountId: "+theAccount.getAccountId() +" has been found,please try again!";
//        }
//        if(!accountService.checkDuplicatePhoneNumber(theAccount.getPhoneNumber())){
//            return "This phone number is already linked with another account, please try again!";
//        }
//        accountService.saveAccount(theAccount);
//        return "Saved account: "+theAccount;
//    }
}
