<%@page import="com.ty.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

	<head lang="en">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
				<span id="time">2018年08月13日&nbsp;18:45:39&nbsp;星期一</span>
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
						<span>用户管理页面</span>
					</div>
					<div class="search">
						<form method="get" action="">
							<a href="useradd.jsp">添加用户</a>
							<span>用户名：</span>
							<input name="queryname" class="input-text" type="text" value="">

							<span>用户角色：</span>
							<select name="queryUserRole">

								<option value="">--请选择--</option>

								<option value="1">系统管理员</option>

								<option value="2">经理</option>

								<option value="3">普通员工</option>

							</select>

							<input type="hidden" name="pageIndex" value="1">
							<input value="查 询" type="submit" id="searchbutton">

						</form>
					</div>
					<!--账单表格 样式和供应商公用-->
					<table class="providerTable" cellpadding="0" cellspacing="0">
						<tbody>
							<tr class="firstTr">
								<th width="10%">用户编码</th>
								<th width="20%">用户名称</th>
								<th width="10%">性别</th>
								<th width="10%">年龄</th>
								<th width="10%">电话</th>
								<th width="10%">用户角色</th>
								<th width="30%">操作</th>
							</tr>
						
							<c:forEach var="vo" items="${userDataList }">
							<tr>
								<td>
									<span>${vo.userCode }</span>
								</td>
								<td>
									<span>${vo.userName }</span>
								</td>
								<td>
									<span>
								
								    ${vo.gender==1?'女':'男' }
									</span>
								</td>
								<td>
									<span>${vo.age }</span>
								</td>
								<td>
									<span>${vo.phone}</span>
								</td>
								<td>
									<span>${vo.roleName }</span>
								</td>
								<td>
									<span><a class="viewUser" href="user_detail.html"><img src="../images/read.png" alt="查看" title="查看"></a></span>
									<span><a class="modifyUser" href="user_update.html" ><img src="../images/xiugai.png" alt="修改" title="修改"></a></span>
									<span><a class="deleteUser" href="delete_user()" ><img src="../images/schu.png" alt="删除" title="删除"></a></span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="page-bar">
						<ul class="page-num-ul clearfix">
							<li>共9条记录&nbsp;&nbsp; 1/2页</li>
							<a href="">首页</a>
							<a href="">上一页</a>
							<a href="">下一页</a>
							<a href="">最后一页</a>
							&nbsp;&nbsp;
						</ul>
						<span class="page-go-form"><label>跳转至</label>
					     <input type="text" name="inputPage" id="inputPage" class="page-key">页
					     <button type="button" class="page-btn" >GO</button>
						</span>
					</div>
				</div>
			</section>

			<footer class="footer">
				版权归源动力
			</footer>
		</body>

		<script type="text/javascript">
			function delete_user() {
				if(confirm('您是否删除此条数据！')) {
					alert("删除成功！");
				}
			}
		</script>
		<!--时间js-->	
		<script type="text/javascript" src="../js/time.js"></script>
</html>