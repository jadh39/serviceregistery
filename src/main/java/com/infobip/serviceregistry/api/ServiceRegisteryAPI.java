package com.infobip.serviceregistry.api;

import com.infobip.serviceregistry.model.Account;
import com.infobip.serviceregistry.model.ShortURL;
import com.infobip.serviceregistry.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by prashant on 28/4/17.
 */
@RestController
public class ServiceRegisteryAPI {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/account",method = RequestMethod.POST)
    public Object createAccount(@RequestBody Account account){
        return accountService.createAccount(account);
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public Object registerAccount(@RequestBody Map requestJson){
         return accountService.registerAccount(requestJson);
    }

   @RequestMapping(path = "/statistic/{accountID}",method = RequestMethod.GET)
    public Object getAccountStatistic(@PathVariable("accountID")final String accountID){ return accountService.getAccountStatistic(accountID);}

    @RequestMapping(value ="/infobib/*")
    public Object sendRedirect(HttpServletRequest request,HttpServletResponse httpServletResponse) throws IOException {

        ShortURL shortURL = accountService.getLongURL(request.getRequestURL().toString());

        if(shortURL==null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        httpServletResponse.setStatus(shortURL.getRedirectType());

        httpServletResponse.sendRedirect(shortURL.getLongURL().getUrl());

        return null;
    }
}
