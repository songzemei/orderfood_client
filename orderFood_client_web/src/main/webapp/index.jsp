<%@ page contentType="text/html;charset=UTF-8"  isELIgnored="false" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body{
            background: url("${pageContext.request.contextPath}/img/img5.jpg") 100%;
        }
        #checkCode{
            width: 150px;
            float: left;
            margin-left: 175px;
        }
        #img{
            width: 100px;
            float: left;
            height: 43px;
        }
        #login-button{
            margin-right: 25px;
            margin-left: 25px;
        }
    </style>
</head>
<body>
<div class="htmleaf-container">
    <div class="wrapper">
        <div class="container">
            <h1>Welcome</h1>
            <form class="form" method="post" action="${pageContext.request.contextPath}/login" id="form">
                <input type="text" placeholder="用户名" name="username" id="username">
                <input type="password" placeholder="密码" name="password" id="password">
                <button type="submit" id="login-button">登录</button>
                <h3>没有账户？请<a href="${pageContext.request.contextPath}/register.jsp" style="color:white;font-weight: bold">注册</a></h3>
            </form>
        </div>
        <ul class="bg-bubbles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script>
    // window.onload = change;
    // $(function () {
    //     $("#username").blur(checkUsername);
    //     $("#password").blur(checkPassword);
    //     $("#form").submit(function () {
    //         return checkUsername()&&checkPassword();
    //     })
    //
    // })
    <%--function change() {--%>
        <%--var date = new Date().getTime();--%>
        <%--$("#img").attr("src", "${pageContext.request.contextPath}/checkCode?"+date);--%>
    <%--}--%>

    // function checkUsername() {
    //     var username= $("#username").val();
    //     var reg =  /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/;
    //     var flag = reg.test(username);
    //     if(flag){
    //         $("#username").css("border"," 1px solid rgba(255, 255, 255, 0.4)");
    //     }else{
    //         $("#username").css("border","1px solid red");
    //     }
    //     return flag;
    // }

    // function checkPassword() {
    //     var password= $("#password").val();
    //     var reg = /^\w{6,12}$/;
    //     var flag = reg.test(password);
    //     if(flag){
    //         $("#password").css("border"," 1px solid rgba(255, 255, 255, 0.4)");
    //     }else{
    //         $("#password").css("border","1px solid red");
    //     }
    //     return flag;
    // }

</script>
</body>
</html>

