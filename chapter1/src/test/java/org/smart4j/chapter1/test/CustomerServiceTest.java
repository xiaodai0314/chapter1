package org.smart4j.chapter1.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;
import org.smart4j.framework.helper.DatabaseHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() throws Exception{
        //初始化数据库
        String file = "sql/customer_init.sql";
        DatabaseHelper.executeSqlFile(file);
    }

    @Test
    public void getCustomerListTest() {
        List<Customer> customers = customerService.getCustomerList();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void insertCustomer() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("contact", "zhangsan");
        map.put("email", "zhangsan@qq.com");
        map.put("telephone","13398764567");
        DatabaseHelper.insertEntity(Customer.class, map);
    }

}
