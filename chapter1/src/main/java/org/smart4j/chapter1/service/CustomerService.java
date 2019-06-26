package org.smart4j.chapter1.service;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer WHERE id=?";
        DatabaseHelper.getEntity(Customer.class, sql, id);
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {

        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }

}
