package com.ddd.service;

import java.util.ArrayList;  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddd.entity.Customer;
import com.ddd.entity.PageBean;
import com.ddd.entity.QueryVo;
import com.ddd.mapper.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDao customerDao;
       public PageBean<Customer> selectPageByQueryVo(QueryVo vo) {
//    	   Page<Customer> page=new Page<Customer>();
//    	   vo.setSize(10);
//    	   page.setSize(vo.getSize());
    	 //封装PageBean对象
    	   PageBean<Customer> page =new  PageBean<Customer>();
           //设置当前页码
    	   page.setCurrentPage(vo.getCurrentPage());
           //设置每页显示的行数
    	   page.setPageSize(vo.getPageSize());
    	   if(null!=vo) {
    		   
    		   if(null!=vo.getCustName()&&"".equals(vo.getCustName().trim())) {
    			  vo.setCustName(vo.getCustName().trim());
    		   }
    		   if(null!=vo.getCustIndustry()&&"".equals(vo.getCustIndustry().trim())) {
     			  vo.setCustIndustry(vo.getCustIndustry().trim());
     		   }
    		   if(null!=vo.getCustLevel()&&"".equals(vo.getCustLevel().trim())) {
     			  vo.setCustLevel(vo.getCustLevel().trim());
     		   }
    		   if(null!=vo.getCustSource()&&"".equals(vo.getCustSource().trim())) {
     			  vo.setCustSource(vo.getCustSource().trim());
     		   }
    	   }
    	   //计算开始行
    	   int start=(vo.getCurrentPage()-1)*vo.getPageSize();
    	   vo.setStartRow(start);
    	   //设置总条数
    	   int totalCount=customerDao.customerCount(vo);
    	  page.setTotalCount(totalCount);
    	//设置总页数
          int totalPage=(totalCount%vo.getPageSize())==0?totalCount/vo.getPageSize():(totalCount/vo.getPageSize())+1;
    	 page.setTotalPage(totalPage);
    	 List<Customer>  customer=customerDao.selectCustomerLsitByQueryVo(vo);
          page.setList(customer);
		return page;
    	   
       }
	public Customer selectCustomerByid(Integer id) {
		
		return customerDao.selectCustomerByid(id);
	}
	public void updateCustomerByid(Customer customer) {
		 customerDao.updateCustomerByid(customer);
		
	}
	public void deleteCustomerByid(Integer id) {
		customerDao.deleteCustomerByid(id);
		
	}
}
