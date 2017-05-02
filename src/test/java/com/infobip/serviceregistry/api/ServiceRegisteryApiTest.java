package com.infobip.serviceregistry.api;

import com.infobip.serviceregistry.AbstractAPITest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashant on 2/5/17.
 */
@Transactional
public class ServiceRegisteryApiTest extends AbstractAPITest {

    @Before
    public void setUp(){super.setUp();}

    @Test
    public void testAccountCreation() throws Exception {

        Map request = new HashMap<String,String>();

        request.put("AccountId","abc123");
        request.put("UserName","test123");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(request))).andReturn();

        Assert.assertEquals("Account Creation Service Failed",201,result.getResponse().getStatus());

    }
    @Test
    public void testRegistration() throws Exception {

        testAccountCreation();

        Map request = new HashMap<String,String>();

        request.put("AccountId","abc123");
        request.put("url","http://yahoo.co.in");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(request))).andReturn();

        Assert.assertEquals("Account Registration Service Failed",202,result.getResponse().getStatus());

    }
    @Test
    public void testStatistic() throws Exception {

         testRegistration();

         MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/statistic/abc123")
         .accept(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals("Statistic service Failed",200,result.getResponse().getStatus());

    }

}
