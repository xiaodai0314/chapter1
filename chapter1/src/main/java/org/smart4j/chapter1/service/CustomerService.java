package org.smart4j.chapter1.service;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.impl.CustomerServiceImpl;
import org.smart4j.framework.annotation.Impl;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Impl(CustomerServiceImpl.class)
public interface CustomerService {

    public List<Customer> getCustomerList();

    public Customer getCustomer(long id);

    public boolean createCustomer(Map<String, Object> fieldMap, FileParam fileParam);
    public boolean updateCustomer(long id, Map<String, Object> fieldMap);

    public boolean deleteCustomer(long id);

}
