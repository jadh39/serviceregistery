package com.infobip.serviceregistry.service;

import com.infobip.serviceregistry.model.Account;
import com.infobip.serviceregistry.model.LongURL;
import com.infobip.serviceregistry.model.ShortURL;
import com.infobip.serviceregistry.repository.AccountRepository;
import com.infobip.serviceregistry.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by prashant on 28/4/17.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ShortURLRepository shortURLRepository;

    public Object createAccount(Account account){

        Map response = new HashMap<String,String>();

        if(!validateAccount(account)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account check = accountRepository.findByAccountId(account.getAccountId());

        if(check != null){
            response.put("success","false");
            response.put("discription","AccountId is already exist");
            return new ResponseEntity<Map>(response, HttpStatus.FORBIDDEN);
        }

        account.setPassword(randomeAlphaNumericString());

        Account savedAccount = accountRepository.save(account);

        response.put("success","true");
        response.put("discription","Your Account is Opened");
        response.put("password",savedAccount.getPassword());

        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }
    public Object registerAccount(Map requestJson){

        Account account = accountRepository.findByAccountId(requestJson.get("AccountId").toString());

        Integer redirectType =(Integer) requestJson.get("redirectType");

        if(null == redirectType)
            redirectType = 302;

        LongURL longURL = null;

        if(account == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!account.getLongURLs().isEmpty()) {

            Set<LongURL> longURLs = account.getLongURLs();

            String longRequestUrl = requestJson.get("url").toString();

            for(LongURL url:longURLs){
                if (url.getUrl().equalsIgnoreCase(longRequestUrl))
                    longURL = url;
            }

            if(null != longURL) {
                ShortURL newShortURL = new ShortURL(longURL, "http://localhost:8080/infobib/" + randomeAlphaNumericString(), redirectType);
                longURL.getShortURLs().add(newShortURL);
                accountRepository.save(account);
                Map response = new HashMap<>();
                response.put("shortURL",newShortURL.getUrl());
                return new ResponseEntity<Map>(response,HttpStatus.ACCEPTED);
            }
        }

        longURL = new LongURL(account,requestJson.get("url").toString());

        ShortURL newShortURL = new ShortURL(longURL, "http://localhost:8080/infobib/" + randomeAlphaNumericString(), redirectType);

        longURL.getShortURLs().add(newShortURL);

        account.getLongURLs().add(longURL);

        accountRepository.save(account);

        Map response = new HashMap<>();

        response.put("shortURL",newShortURL.getUrl());

        return new ResponseEntity<Map>(response,HttpStatus.ACCEPTED);
    }

    public Object getAccountStatistic(String accountId){

        Account account = accountRepository.findByAccountId(accountId);

        if(account == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map response = new HashMap<>();

        Set<LongURL> longURLs = account.getLongURLs();

        for(LongURL url:longURLs)
            response.put(url.getUrl(),url.getShortURLs().size());

        return new ResponseEntity<Map>(response,HttpStatus.OK);

    }

    public ShortURL getLongURL(String url){
        return shortURLRepository.findByUrl(url);
    }

    public String randomeAlphaNumericString(){ return UUID.randomUUID().toString().substring(0, 8); }

    public boolean validateAccount(Account account){
        return account.getAccountId() != null?true:false;
    }



}
