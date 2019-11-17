<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head lang="en">
		<meta charset="UTF-8">
		<title></title>
		<link type="text/css" rel="stylesheet" href="../css/style.css" />
		<link type="text/css" rel="stylesheet" href="../css/public.css" />
	</head>

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
			<span id="time">2015年1月1日 11:11  星期一</span>
			<a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
		</section>
		<!--主体内容-->
		<section class="publicMian ">
			<div class="left">
				<h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
				<%@include file="menuleft.jsp" %>
			</div>

			<div class="right">
				<div class="location">
					<strong>你现在所在的位置是:</strong>
					<span>用户管理页面 >>  用户添加页面</span>
				</div>
				<div class="providerAdd">
					<form id="userForm" action="user" name="userForm" method="post" >
						<input type="hidden" name="action" value="add">
						<!--div的class 为error是验证错误，ok是验证成功-->
						<div>
							<label for="userCode">用户编码：</label>
							<input type="text" name="userCode" id="userCode" value="" validatestatus="false">
						</div>
						<div>
							<label for="userName">用户名称：</label>
							<input type="text" name="userName" id="userName" value="">
							<font color="red">*</font>
						</div>
						<div>
							<label for="userPassword">用户密码：</label>
							<input type="password" name="userPassword" id="userPassword" value="">
							<font color="red">*</font>
						</div>
						<div>
							<label for="ruserPassword">确认密码：</label>
							<input type="password" name="ruserPassword" id="ruserPassword" value="">
							<font color="red">*</font>
						</div>
						<div>
							<label>用户性别：</label>
							<select name="gender" id="gender">
								<option value="1" selected="selected">男</option>
								<option value="2">女</option>
							</select>
						</div>
						<div>
							<label for="birthday">出生日期：</label>
							<input type="text" class="Wdate" id="birthday" name="birthday" readonly="readonly" onclick="WdatePicker();">
							<font color="red">*</font>
						</div>
						<div>
							<label for="phone">用户电话：</label>
							<input type="text" name="phone" id="phone" value="">
							<font color="red">*</font>
						</div>
						<div>
							<label for="address">用户地址：</label>
							<input name="address" id="address" value="">
						</div>
						<div>
							<label>用户角色：</label>
							<!-- 列出所有的角色分类 -->
							<select name="userRole" id="userRole">
								<option value="0">请选择</option>
								<option value="1">系统管理员</option>
								<option value="2">经理</option>
								<option value="3">普通员工</option>
							</select>
							<font color="red">*</font>
						</div>
						<div class="providerAddBtn">
							<input type="submit" name="add" id="add" value="保存">
							<input type="button" id="back" name="back" value="返回"  onclick="javascript:history.go(-1)">
						</div>
					</form>
				</div>

			</div>
		</section>

		<footer class="footer">
			版权归源动力
		</footer>
		<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
		<!--时间js-->	
		<script type="text/javascript" src="../js/time.js"></script>
	</body>

</html>