package org.smart4j.chapter1.controller;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;


import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("GET:/getCustomerList")
    public View getCustomerList() {
        System.out.println("进入Action");
        customerService = new CustomerService();
        List<Customer> list = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", list);
    }

    @Action("GET:/getCustomerList1")
    public View getCustomerList1(Param param) {
        System.out.println("进入Action" + param.getLong("add"));
        customerService = new CustomerService();
        List<Customer> list = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", list);
    }

}
