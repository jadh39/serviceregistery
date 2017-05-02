package com.infobip.serviceregistry.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by prashant on 30/4/17.
 */
@Entity
public class LongURL {

    public LongURL(){}

    public LongURL(Account account,String url){
        this.account=account;
        this.url=url;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String longId;

    private String url;

    @ManyToOne()
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "longId")
    private Set<ShortURL> shortURLs = new HashSet<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<ShortURL> getShortURLs() {
        return shortURLs;
    }

    public void setShortURLs(Set<ShortURL> shortURLs) {
        this.shortURLs = shortURLs;
    }
}
