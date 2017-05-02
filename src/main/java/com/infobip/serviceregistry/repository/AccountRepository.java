package com.infobip.serviceregistry.repository;

import com.infobip.serviceregistry.model.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by prashant on 28/4/17.
 */
public interface AccountRepository extends CrudRepository<Account,String> {

    public Account findByAccountId(String id);
}
