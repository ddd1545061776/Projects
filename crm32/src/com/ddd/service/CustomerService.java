package com.ddd.service;

 
import com.ddd.entity.Customer;
import com.ddd.entity.PageBean;
import com.ddd.entity.QueryVo;

public interface CustomerService {
	public PageBean<Customer> selectPageByQueryVo(QueryVo vo);
	 public Customer selectCustomerByid(Integer id);
	 public void updateCustomerByid(Customer customer);
	 public void deleteCustomerByid(Integer id);
}
