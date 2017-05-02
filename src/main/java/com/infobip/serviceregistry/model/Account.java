package com.infobip.serviceregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by prashant on 28/4/17.
 */
@Entity
public class Account {

    @JsonProperty(value = "AccountId")
    @Id
    private String accountId;

    @JsonProperty(value = "UserName")
    private String userName;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Set<LongURL> longURLs = new HashSet<>();

    public Set<LongURL> getLongURLs() {
        return longURLs;
    }

    public void setLongURLs(Set<LongURL> longURLs) {
        this.longURLs = longURLs;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
