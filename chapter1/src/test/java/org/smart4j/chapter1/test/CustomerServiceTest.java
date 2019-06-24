package org.smart4j.chapter1.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;

import java.util.List;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() {
        //初始化数据库
    }

    @Test
    public void getCustomerListTest() {
        List<Customer> customers = customerService.getCustomerList();
        Assert.assertEquals(2, customers.size());
    }
}
