package com.ddd.controller;


import java.util.List;   

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddd.entity.BaseDict;
import com.ddd.entity.Customer;
import com.ddd.entity.PageBean;
import com.ddd.entity.QueryVo;
import com.ddd.service.BaseDictService;
import com.ddd.service.CustomerService;

@Controller
public class CustomerController {
	@Autowired
  private BaseDictService baseDictService;
	@Autowired
	  private CustomerService customerService;
	@Value("${fromType.code}")
	private String fromTypeCode;
	@Value("${industryType.code}")
	private String industryTypeCode;
	@Value("${levelType.code}")
	private String levelTypeCode;
	   @RequestMapping("/list")
       public String   List(QueryVo vo,Model model,HttpServletRequest req) {
		  
		   List<BaseDict> fromType=   baseDictService.selectBaseDictListBycode(fromTypeCode);
		   List<BaseDict> industryType=   baseDictService.selectBaseDictListBycode(industryTypeCode);
		   List<BaseDict> levelType=   baseDictService.selectBaseDictListBycode(levelTypeCode);
		   model.addAttribute("fromType",fromType);
		   model.addAttribute("industryType",industryType);
		   model.addAttribute("levelType",levelType);
		 String currentPageStr=  req.getParameter("currentPage");
		 String pageSizeStr=  req.getParameter("pageSize");
		 int currentPage = 0;//当前页码，如果不传递，则默认为第一页
	        if(currentPageStr != null && currentPageStr.length() > 0){
	            currentPage = Integer.parseInt(currentPageStr);
	        }else{
	            currentPage = 1;
	        }

	        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
	        if(pageSizeStr != null && pageSizeStr.length() > 0){
	            pageSize = Integer.parseInt(pageSizeStr);
	        }else{
	            pageSize = 5;
	        }
	         vo.setCurrentPage(currentPage);
	       vo.setPageSize(pageSize);
	        PageBean<Customer> page=  customerService.selectPageByQueryVo(vo);
	        System.out.println(page.getTotalCount());
	        System.out.println(page.getList());
		   model.addAttribute("page",page);
		   model.addAttribute("custName",vo.getCustName());
		   model.addAttribute("custSource",vo.getCustSource());
		   model.addAttribute("custIndustry",vo.getCustIndustry());
		   model.addAttribute("custLevel",vo.getCustLevel());
    	   return "customer";
       }
	   @RequestMapping("/customer/edit.action")
	   public @ResponseBody
	   Customer toedit(Integer id) {
		   return  customerService.selectCustomerByid(id);
	   }
	  
	   @RequestMapping("/ customer/update.action")
	   public @ResponseBody
	   String toupdate(Customer customer) {
		   customerService.updateCustomerByid(customer);
		   return  "ok";
	   }
	   @RequestMapping("/customer/delete.action")
	   public @ResponseBody
	   String todelete(Integer id) {
		   customerService.deleteCustomerByid(id);
		   return  "ok";
	   }
}
