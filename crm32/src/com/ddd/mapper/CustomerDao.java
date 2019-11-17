package com.ddd.mapper;

import java.util.List; 

import com.ddd.entity.Customer;
import com.ddd.entity.QueryVo;

public interface CustomerDao  {
	//总条数
      public  Integer  customerCount(QueryVo vo);
      //结果集 
      public List<Customer> selectCustomerLsitByQueryVo(QueryVo vo);
      //通过id查询用户
      public Customer selectCustomerByid(Integer id);
      //更新客户
      public void updateCustomerByid(Customer customer);
      //删除客户
      public void deleteCustomerByid(Integer id);
}
