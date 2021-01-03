<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script src="<%=basePath%>/js/jquery-3.3.1.min.js"></script>
    <script src="<%=basePath%>/js/register.js"></script>
    <script>
        function getBasePath(){
            return '<%=basePath%>';
        }
    </script>
</head>
<body>
<form>
    <div>
        账号: <input name="userId">
    </div>
    <div>
        密码: <input name="password">
    </div>
    <div>
        手机号: <input name="number">
    </div>
    <div>
        验证码: <input name="verifyCode"><button type="button" class="sendVerifyCode">获取短信验证码</button>
    </div>
    <div><button type="button" class="sub-btn">提交</button></div>
</form>
</body>
</html>
