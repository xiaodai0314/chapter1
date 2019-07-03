package org.smart4j.chapter1.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.controller.CustomerController;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;
import org.smart4j.chapter1.service.impl.CustomerServiceImpl;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Impl;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.DatabaseHelper;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CustomerServiceTest {

    @Inject
    private CustomerService customerService;

/*    public CustomerServiceTest() {
        customerService = new CustomerServiceImpl();
    }*/

    @Before
    public void init() throws Exception{
        //初始化数据库
        /*String file = "sql/customer_init.sql";
        DatabaseHelper.executeSqlFile(file);*/
    }

    @Test
    public void getCustomerListTest() {
        CustomerService customerService1 = customerService;
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

    @Test
    public void iocTest() throws Exception{
        CustomerController customerController;
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)) {
//            遍历Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean类中所有字段,(不包括父类方法)
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历Bean Field
                    for(Field beanField : beanFields) {
                        //判断当前Bean Field是否带有Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)) {
                            //在bean Map 中获取 Bean Field对应的实例 获取bean字段对应的接口
                            Class<?> interfaceClass = beanField.getType();
                            //获取 bean字段对应的实现类
                            Class<?> implementClass = findImplementClass(interfaceClass);
                            if(implementClass != null) {
                                //从BEANMAP 获取相应的实例
                                Object implementInstance = beanMap.get(implementClass);
                                if(implementInstance != null) {
                                    /*beanField.setAccessible(true);
                                    beanField.set(beanInstance, implementInstance);*/
                                    //通过反射初始化BeanField的值
//                                    LOGGER.debug("beanInstance: " + beanInstance + "  beanField: " + beanField + "implementInstance" +
//                                            implementInstance);
//                                    ReflectionUtil.setField(beanInstance, beanField, implementInstance);
                                    ReflectionUtil.setField(beanInstance, beanField, implementInstance);

                                } else {
                                    throw new RuntimeException("依赖注入失败！类名：" + beanClass.getSimpleName() + "，字段名：" + interfaceClass.getSimpleName());
                                }
                            }
                        }
                    }
                }
            }
        }
        customerController = (CustomerController) beanMap.get(CustomerController.class);
        ReflectionUtil.invokeMethod(beanMap.get(CustomerController.class), CustomerController.class.getMethod("getCustomerList"), null);
        customerService.getCustomerList();
    }

    /**
     * 查找实现类
     * @param interfaceClass
     * @return
     */
    private static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        //判断接口是否标注了 impl注解
        if (implementClass.isAnnotationPresent (Impl.class)) {
            //获取强制指定的实现类
            implementClass = interfaceClass.getAnnotation(Impl.class).value();
        } else {
            Set<Class<?>> implementClassList = ClassHelper.getClassSetBySuper(interfaceClass);
            if(CollectionUtil.isNotEmpty(implementClassList)) {
                implementClass = implementClassList.iterator().next();
            }
        }
        return implementClass;
    }

    @Test
    public void beanTest() {
        Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
        try {
            Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
            for(Class<?> beanClass : beanClassSet) {
                if(beanClass.isAnnotationPresent(Controller.class) || beanClass.isAnnotationPresent(Service.class)) {
                    Object obj = ReflectionUtil.newInstance(beanClass);
                    BEAN_MAP.put(beanClass, obj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化 BeanHelper 出错！",e);
        }
    }

}
