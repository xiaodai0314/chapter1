package org.smart4j.chapter1.controller;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.IocHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController extends BaseController{

    @Inject
    public CustomerService customerService;

    @Action("GET:/getCustomerList")
    public View getCustomerList() {
        List<Customer> list = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", list);
    }

    @Action("GET:/getCustomerList1")
    public View getCustomerList1(Param param) {
        System.out.println("进入Action" + param.getLong("add"));
//        customerService = new CustomerService();
        List<Customer> list = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", list);
    }

    @Action("GET:/addCustomer")
    public View addCustomer() {
        return new View("customer_create.jsp");
    }

    @Action("POST:/customer_create")
    public Data createSubmit(Param param) {
        Map<String, Object> fieldMap = param.getFieldMap();
        FileParam fileParam = param.getFile("photo");
        boolean result = customerService.createCustomer(fieldMap, fileParam);
        return new Data(result);
    }

}
