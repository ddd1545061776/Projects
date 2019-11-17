package com.ty.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * js操作的封装类
 * @author Administrator
 *
 */
public class JsUtil {

	//打印提示信息
	public static void printAlert(HttpServletResponse resp,String msg){
		resp.setHeader("Content-Type","text/html;charset=UTF-8"); //给浏览器响应的类型
		try {
			resp.getWriter().println("<script>alert('"+msg+"');window.history.go(-1);</script>");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} //javascript 信息给浏览器
	}
}
