package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.AccountRepository;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImplementation implements AccountService{

    private AccountRepository accountRepository;
    private EntityManager entityManager;

    @Autowired
    public AccountServiceImplementation(AccountRepository theAccountRepository, EntityManager theEntityManager){
        accountRepository = theAccountRepository;
        entityManager = theEntityManager;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(String accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account theAccount = null;

        if(result.isPresent()){
            theAccount = result.get();
        } else {
            throw new RuntimeException("Account with id - " +accountId+ " not found!");
        }
        return theAccount;
    }

    @Override
    public void saveAccount(Account theAccount) {
        accountRepository.save(theAccount);
    }

    @Override
    public void disableAccount(String accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account theAccount = null;
        if(result.isPresent()){
            theAccount = result.get();
        } else {
            throw new RuntimeException("Account with id - " +accountId+ " not found");
        }
        theAccount.setStatus(false);
        accountRepository.save(theAccount);
    }

    @Override
    public boolean CheckDuplicateAccountId(String accountId) {

        Optional<Account> result = accountRepository.findById(accountId);
        Account theAccount = null;
        if(result.isPresent()){
            throw new RuntimeException("Duplicate with accountId - " +accountId+ " has been found!, please try again!");
        }
        return true;

    }

    @Override
    public boolean checkDuplicatePhoneNumber(String phoneCheckNum) {
        Query theQuery = entityManager.createQuery("select phoneNumber from Account");
        theQuery.getResultList();
        for(int i = 0; i<theQuery.getResultList().size();i++){
            if (phoneCheckNum.equals(theQuery.getResultList().get(i))){
                throw new RuntimeException("This account has already linked with another account, please try another number!");
            }
        }
        return true;
    }


}
