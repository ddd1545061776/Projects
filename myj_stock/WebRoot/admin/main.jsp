<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title></title>
    <link type="text/css" rel="stylesheet" href="../css/style.css">
    <link type="text/css" rel="stylesheet" href="../css/public.css">
    
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>美宜佳库存管理系统</h1>
        <div class="publicHeaderR">
            <%@include file="header.jsp" %>
        </div>
    </header>
	<!--时间-->
	 <section class="publicTime">
	        <span id="time">2018年08月13日&nbsp;18:48:55&nbsp;星期一</span>
	        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
	 </section>
	 <!--主体内容-->
	 <section class="publicMian ">
	     <div class="left">
	         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
	         <!-- 将其他页面嵌入到当前页面中 -->
	         <%@include file="menuleft.jsp" %>
	     </div>
	    <div class="right">
	        <img class="wColck" src="../images/clock.jpg" alt="">
	        <div class="wFont">
	            <h2>${role.roleName}</h2>
	            <p>欢迎来到美宜佳库存管理系统!</p>
	        </div>
	    </div>
	</section>
	
	<footer class="footer">
	    版权归源动力
	</footer>
<!--时间js-->	
<script type="text/javascript" src="../js/time.js"></script>
</body>
</html>