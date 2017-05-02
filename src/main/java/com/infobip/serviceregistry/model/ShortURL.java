package com.infobip.serviceregistry.model;

import javax.persistence.*;

/**
 * Created by prashant on 30/4/17.
 */
@Entity
public class ShortURL {

    public ShortURL(){}

    public ShortURL(LongURL longURL,String url,int redirectType)
    {
        this.longURL = longURL;
        this.url = url;
        this.redirectType=redirectType;
    }

    @Id
    private String url;

    @ManyToOne()
    @JoinColumn(name = "longId", insertable = false, updatable = false)
    private LongURL longURL;

    private int redirectType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LongURL getLongURL() {
        return longURL;
    }

    public void setLongURL(LongURL longURL) {
        this.longURL = longURL;
    }

    public int getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(int redirectType) {
        this.redirectType = redirectType;
    }
}
