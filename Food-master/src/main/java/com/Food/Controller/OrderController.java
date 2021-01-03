package com.Food.Controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Food.Entity.Shop;
import com.Food.Entity.Shoporder;
import com.Food.Entity.User;
import com.Food.Service.OrderService;
import com.Food.Service.ShopService;
import com.Food.Service.UserService;

@Controller
public class OrderController {

	@Resource
	private OrderService orderService;
	@Resource
	private ShopService shopService;
	@Resource
	private UserService userService;
	@RequestMapping(value="/AddOrder")
	@ResponseBody
	public String AddOrder(HttpServletRequest request){
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String shopId = request.getParameter("shopId");
		String num = request.getParameter("num");
		String ordertime = request.getParameter("ordertime");
		String roomtype= request.getParameter("roomtype");
		String money= request.getParameter("money");
		Shop shopmodel = shopService.GetShopById(shopId);
		User user = userService.GetUserById(userId);
		if (user.getMoney()<Double.parseDouble(money)){
			return "money";
		}
		if(shopmodel!=null)
		{
			if(Integer.parseInt(roomtype)==1)
			{
				if(shopmodel.getRoom1()<=0){
					return "error";
				}else{
					shopmodel.setRoom1(shopmodel.getRoom1()-1);
					shopService.ShopEdit(shopmodel);
					user.setMoney(user.getMoney()-Double.parseDouble(money));
					userService.UserEdit(user);
				}
			}
			if(Integer.parseInt(roomtype)==2)
			{
				if(shopmodel.getRoom2()<=0){
					return "error";
				}else{
					shopmodel.setRoom2(shopmodel.getRoom2()-1);
					shopService.ShopEdit(shopmodel);
					user.setMoney(user.getMoney()-Double.parseDouble(money));
					userService.UserEdit(user);
				}
			}
			if(Integer.parseInt(roomtype)==3)
			{
				if(shopmodel.getRoom3()<=0){
					return "error";
				}else{
					shopmodel.setRoom3(shopmodel.getRoom3()-1);
					shopService.ShopEdit(shopmodel);
					user.setMoney(user.getMoney()-Double.parseDouble(money));
					userService.UserEdit(user);
				}
			}
		}
		Shoporder model = new Shoporder();
		model.setId(UUID.randomUUID().toString());
		model.setPnum(Integer.parseInt(num));
		model.setPhone(phone);
		model.setUserId(userId);
		model.setShopId(shopId);
		model.setOrdertime(ordertime);
		model.setOrderMoney(Double.valueOf(money));
		model.setRoomtype(Integer.parseInt(roomtype));
		System.out.print("modelmodel:"+model);
		orderService.AddOrder(model);
		return "true";
	}

	@RequestMapping(value="/GetOrderByUser")
	@ResponseBody
	public List<Shoporder> GetOrderByUser(HttpServletRequest request){
		String userId = request.getParameter("userId");
		 List<Shoporder>  ShoporderList = orderService.GetOrderByUser(userId);
		 return ShoporderList ;
	}

	@RequestMapping(value="/DelOrder")
	@ResponseBody
	public String DelOrder(HttpServletRequest request){
		String orderId = request.getParameter("orderId");
		String shopId = request.getParameter("shopId");
		String userId = request.getParameter("userId");
		String orderMoney = request.getParameter("orderMoney");
		orderService.DelOrder(orderId);
		Shop shopmodel = shopService.GetShopById(shopId);
		String roomtype = request.getParameter("roomtype");
		if(Integer.parseInt(roomtype)==1){
			shopmodel.setRoom1(shopmodel.getRoom1()+1);
			shopService.ShopEdit(shopmodel);
		}else if(Integer.parseInt(roomtype)==2){
			shopmodel.setRoom2(shopmodel.getRoom2()+1);
			shopService.ShopEdit(shopmodel);
		}else if(Integer.parseInt(roomtype)==3){
			shopmodel.setRoom3(shopmodel.getRoom3()+1);
			shopService.ShopEdit(shopmodel);
		}
		shopService.ShopEdit(shopmodel);
		User user = userService.GetUserById(userId);
		user.setMoney(user.getMoney()+Double.parseDouble(orderMoney));
		userService.UserEdit(user);
		return "success";
	}
	
	@RequestMapping(value="/GetOrderByShop")
	@ResponseBody
	public List<Shoporder> GetOrderByShop(HttpServletRequest request){
		String shopId = request.getParameter("shopId");
		return orderService.GetOrderByShop(shopId);
	}


}
