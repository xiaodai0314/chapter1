package org.smart4j.chapter1.service.impl;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Impl;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.helper.DatabaseHelper;
import org.smart4j.framework.helper.UploadHelper;
import org.smart4j.framework.tx.annotation.Transaction;

import java.util.List;
import java.util.Map;



@Service
public class CustomerServiceImpl implements CustomerService{
    @Override
    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    @Override
    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer WHERE id=?";
        DatabaseHelper.getEntity(Customer.class, sql, id);
        return null;
    }

    @Override
    @Transaction
    public boolean createCustomer(Map<String, Object> fieldMap, FileParam fileParam) {
        boolean result = DatabaseHelper.insertEntity(Customer.class, fieldMap);
        if (result) {
            UploadHelper.uploadFile("/Users/yupeng/upload/upload", fileParam);
        }
        return result;
    }

    @Override
    @Transaction
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    @Override
    @Transaction
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
