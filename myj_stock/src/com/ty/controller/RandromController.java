package com.ty.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet是简化web.xml配置,urlPatterns属性映射虚拟路径
//alt+/
@WebServlet(urlPatterns="/home/code")
public class RandromController extends HttpServlet {
	
	//请求方法 get
	//生产验证码
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.存储的文字放在那里？
		String [] codeStr={"1","2","3","4","5","6","7","8","9","0","A","B","C"};
		//2.绘制图片的方式使用什么?
		//BufferedImage 图片对象  TYPE_INT_ARGB_PRE支持透明色
		BufferedImage bfi=new BufferedImage(90, 37, BufferedImage.TYPE_INT_ARGB_PRE);
		//获取画板
		Graphics g=bfi.getGraphics();
		//画东西
		g.setColor(Color.BLACK); //设置颜色
		//设置文字大小
		g.setFont(new Font("宋体",Font.BOLD, 32));
		
		Random rd=new Random(); //Randrom是随机类
		//存储循环后的验证码
		String allCode="";
		//生成四个字符,循环四次
		for(int i=0;i<4;i++){
			//随机获取字符 ,nextInt()随机方法
			String temp=codeStr[rd.nextInt(codeStr.length)];
			allCode=allCode+temp; //字符串拼接
		}
		//获取session对象 ,setAttribute存储的值：存储的名称，存储的值
		req.getSession().setAttribute("allCode", allCode);
		//写文字
		g.drawString(allCode,0, 30); //绘制的文字,x坐标,y坐标
		ImageIO.write(bfi, "PNG", resp.getOutputStream()); //输出到浏览器
		resp.getOutputStream().close(); //关闭响应对象
	}

	
	
	
}
