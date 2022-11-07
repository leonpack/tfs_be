package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    private AccountService accountService;

    @Autowired
    public AccountRestController(AccountService theAccountService){
        accountService = theAccountService;
    }

    @GetMapping("/accounts")
    @ApiOperation("Return list of all accounts")
    public List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    @ApiOperation("Return account based on accountId")
    public Account getAccountByAccountId(@PathVariable String accountId){
        Account theAccount = accountService.getAccountById(accountId);
        if(theAccount==null){
            throw new RuntimeException("Account with id - " +accountId+ " not found!");
        }
        return theAccount;
    }

    @PostMapping("/accounts")
    @ApiOperation("Add new account (need full Account's JSON)")
    public String addNewAccount(@RequestBody Account theAccount){
        if(!accountService.CheckDuplicateAccountId(theAccount.getAccountId())){
            return "Duplicate with accountId: "+theAccount.getAccountId() +" has been found,please try again!";
        }
        if(!accountService.checkDuplicatePhoneNumber(theAccount.getPhoneNumber())){
            return "This phone number is already linked with another account, please try again!";
        }
        accountService.saveAccount(theAccount);
        return "Saved account: "+theAccount;
    }

    @PutMapping("/accounts")
    @ApiOperation("Update existing account (need full Account's JSON)")
    public Account updateAccount(@RequestBody Account theAccount){
        accountService.saveAccount(theAccount);
        return theAccount;
    }

    @DeleteMapping("/accounts/{accountId}")
    @ApiOperation("Disable existing account based on id")
    public String disableAccount(@PathVariable String accountId){
        Account theAccount = accountService.getAccountById(accountId);
        if(theAccount==null){
            throw new RuntimeException("Account with id - "+accountId+ " not found!");
        }
        accountService.disableAccount(accountId);
        return "Disable account with id - " +accountId+ " completed!";
    }
}
