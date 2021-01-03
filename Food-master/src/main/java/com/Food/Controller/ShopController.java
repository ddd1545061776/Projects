package com.Food.Controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Food.Entity.Shop;
import com.Food.Service.ShopService;

@Controller
public class ShopController {

	@Resource
	private ShopService shopService;

	@RequestMapping(value = "/AddShop")
	public String AddShop(HttpServletRequest request,Shop model,MultipartFile pictureFile,String verifyCode)throws Exception
	{
		System.out.print(verifyCode );
		Map<String,String> verifyCode1 =(Map<String,String>) request.getSession().getAttribute("verifyCode");
		String verifyCode2 = verifyCode1.get("verifyCode");
		if (!verifyCode2.equals(verifyCode)){
			return "/fail";
		}
		//使用UUID给图片重命名，并去掉四个“-”
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		//获取文件的扩展名
		String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
		//设置图片上传路径
		String url = request.getSession().getServletContext().getRealPath("/upload");		
		//以绝对路径保存重名命后的图片
		pictureFile.transferTo(new File(url+"/"+name + "." + ext));
		System.out.println("url::"+url);
		//把图片存储路径保存到数据库
		if(pictureFile.getSize()!=0)
		{
			model.setImage("upload/"+name + "." + ext);
		}
		model.setId(UUID.randomUUID().toString());
		shopService.AddShop(model);
		return "success";
	}
	/**
	 * 查询所有店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/GetAllShop")
	@ResponseBody
	public List<Shop> GetAllShop(HttpServletRequest request){
		return shopService.GetAllShop();
	}

	@RequestMapping(value="/GetShopById")
	@ResponseBody
	public Shop GetShopById(HttpServletRequest request){
		String shopId = request.getParameter("shopId");
		return shopService.GetShopById(shopId);
	}

	@RequestMapping(value="/ShopLogin")
	@ResponseBody
	public Shop ShopLogin(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		Shop shop = shopService.ShopLogin(phone, password);
		if (shop!=null){
			request.getSession().setAttribute("SHOP_SESSION",shop);
		}
		return shop;
	}

	@RequestMapping(value = "/ShopEdit")
	public String ShopEdit(HttpServletRequest request,Shop model,MultipartFile pictureFile)throws Exception
	{
		System.out.print("进入::"+ model);
		//使用UUID给图片重命名，并去掉四个“-”
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		//获取文件的扩展名
		String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
		//设置图片上传路径
		String url = request.getSession().getServletContext().getRealPath("/upload");
		//以绝对路径保存重名命后的图片
		pictureFile.transferTo(new File(url+"/"+name + "." + ext));
		System.out.println("url::"+url);
		//把图片存储路径保存到数据库
		if(pictureFile.getSize()!=0)
		{
			model.setImage("upload/"+name + "." + ext);
		}
		else
		{
			Shop tmp = shopService.GetShopById(model.getId());
			model.setImage(tmp.getImage());
		}
		shopService.ShopEdit(model);
		return "success";
	}

	@RequestMapping(value="/DeleteShop")
	@ResponseBody
	public String DeleteShop(HttpServletRequest request){
		String shopId = request.getParameter("shopId");
		 shopService.DeleteShop(shopId);
		 return "success";
	}
	@RequestMapping(value="/checkCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkCode(HttpServletRequest request, HttpServletResponse response,String verifyCode){
         response.setContentType("text/html;charset=utf-8");
         response.setCharacterEncoding("UTF-8");
		Map<String,String> verifyCode1 =(Map<String,String>) request.getSession().getAttribute("verifyCode");
		if (verifyCode1==null||verifyCode1.size()==0){
			return "请先获取验证码";
		}
		String verifyCode2 = verifyCode1.get("verifyCode");
		if (!verifyCode2.equals(verifyCode)){
			return "验证码不正确";
		}
		return "";
	}
	@RequestMapping(value="/checkPhone",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkPhone(HttpServletRequest request,String phone, HttpServletResponse response,String verifyCode){
		response.setCharacterEncoding("UTF-8");
		Integer integer = shopService.checkPhoneIsExist(phone);
		if (integer>0){
			return "手机号码已经存在";
		}
		return "";
	}
	@RequestMapping(value="/removeSession",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String removeSession(HttpServletRequest request,String phone, HttpServletResponse response,String verifyCode){
		request.getSession().removeAttribute("SHOP_SESSION");
		request.getSession().invalidate();
		return "true";
	}
}
