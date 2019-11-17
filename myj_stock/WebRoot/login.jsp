<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head lang="en">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统登录 -</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <script type="text/javascript">
       //刷新验证码
    	function refCode(obj){
    	     //重新设置图片对象的Src属性
    		obj.setAttribute("src","home/code?v="+new Date().getTime());
    	}
    </script>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>美宜佳库存管理系统</h1>
        </header>
        <section class="loginCont">
	        <form class="loginForm" action="public" method="get">
				<div class="info"></div>
				<div class="inputbox">
                    <label for="user">用户名：</label>
					<input type="text" class="input-text"  name="userCode" placeholder="请输入用户名" >
				</div>	
				<div class="inputbox">
                    <label for="mima">密码：</label>
                    <input type="password" name="userPassword" placeholder="请输入密码" >
                </div>	
                <div class="inputbox">
                    <label for="mima">验证码：</label>
                    <input type="text" style="width:30%;" name="code" placeholder="请输入验证码" >
                    <img src="home/code" onclick="refCode(this)" style="width: 90px;height: 37px;vertical-align: bottom;" />	
                </div>	
				<div class="subBtn">
					
                    <input type="submit" value="登录">
                    <input type="reset" value="重置">
                </div>	
			</form>
        </section>
    </section>


</body>
</html>