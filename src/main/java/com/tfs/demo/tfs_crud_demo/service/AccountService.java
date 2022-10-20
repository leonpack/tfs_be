package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Account;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    public Account getAccountById(String accountId);

    public void saveAccount(Account theAccount);

    public void disableAccount(String accountId);

    public boolean CheckDuplicateAccountId(String accountId);

    public boolean checkDuplicatePhoneNumber(String phoneNumber);

}
