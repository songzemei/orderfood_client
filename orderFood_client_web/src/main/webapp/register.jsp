<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册新用户</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            background: url("${pageContext.request.contextPath}/img/img5.jpg") 100%;
        }

        #checkCode {
            width: 150px;
            float: left;
            margin-left: 175px;
        }

        #img {
            width: 100px;
            float: left;
            height: 43px;
        }

        #login-button {
            margin-right: 25px;
            margin-left: 25px;
            margin-top: 60px;
        }

        #message {
            font-size: 18px;
            position: absolute;
            top: 28px;
            left: 450px;
        }

        .wrapper {
            top: 42%;
            height: 550px;
        }

        #file {
            float: left;
            width: 175px;
            margin-left: 175px;
        }

        #fileBtn {
            float: left;
            width: 75px;
            height: 52px;
            color: #7DEABB;
            background: white;
        }

        #headerForm {
            top: -170px;
        }

        #headerImg {
            margin-left: -80px;
        }
    </style>
</head>
<body>
<div class="htmleaf-container">
    <div class="wrapper">
        <div class="container">
            <h2>注册</h2>
            <form class="form" method="post" action="${pageContext.request.contextPath}/member/register" id="form">
                <input type="hidden" name="headerImg" id="headerImage">
                <input type="text" placeholder="用户名" name="username">
                <input type="password" placeholder="密码" name="password">
                <input type="text" placeholder="邮箱" name="email" id="email" onblur="checkemail()">
                <input type="text" placeholder="手机号" name="phoneNum">
                <button type="submit" id="login-button">注册</button>
                <h3>已有账户，直接<a href="${pageContext.request.contextPath}/index.jsp" style="color:white;font-weight: bold">登录</a>
                </h3>
            </form>
            <form id="headerForm" enctype="multipart/form-data" method="post">
                <input type="file" name="file" id="file">
                <input type="button" value="上传" id="fileBtn">
                <img src="${pageContext.request.contextPath}/img/defaultHeader.gif" id="headerImg" width="52" height="52">
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
<%--<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js" type="text/javascript"></script>--%>
<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript"></script>
<script>
    // window.onload = change;

    // $("#username").blur(function () {
    //     return checkUsername() && checkUsernameRepeat();
    // });
    // $("#password").blur(checkPassword);
    // $("#password2").blur(checkPasswordConfirm);
    //
    // $("#form").submit(function () {
    //     return checkUsername() && checkPassword() && checkPasswordConfirm() && checkUsernameRepeat();
    // })
    $(function () {
        $("#fileBtn").click(function () {
            $("#headerForm").ajaxSubmit({
                url: "${pageContext.request.contextPath}/member/upload",
                type: "POST",
                success: function (data) {
                    if (data.result) {
                        $("#headerImg").attr("src", "http://localhost:81/upload/" + data.message);
                        $("#headerImage").val(data.message);
                    }
                }
            })
            // alert("11");
        })
    })


    function checkemail() {
        var email = $("#email").val();
        var reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/;
        var flag = reg.test(email);
        if (flag) {
            $("#email").css("border", " 1px solid rgba(255, 255, 255, 0.4)");
        } else {
            $("#email").css("border", "1px solid red");
        }
        return flag;
    }

    <%--function checkPasswordConfirm() {--%>
    <%--var password = $("#password").val();--%>
    <%--var password2 = $("#password2").val();--%>
    <%--var flag = password == password2;--%>
    <%--if(flag){--%>
    <%--$("#password2").css("border", " 1px solid rgba(255, 255, 255, 0.4)");--%>
    <%--}else{--%>
    <%--$("#password2").css("border", "1px solid red");--%>
    <%--}--%>
    <%--return flag--%>
    <%--}--%>

    <%--function checkUsernameRepeat() {--%>
    <%--var username = $("#username").val();--%>
    <%--$.get("/user/checkUsername",{"username":username},function (data) {--%>
    <%--if(data.result){--%>
    <%--$("#message").html("<img src=\"/img/duigou.png\">");--%>
    <%--}else{--%>
    <%--$("#message").html("<span style=\"color: red\">"+data.message+"</span>")--%>
    <%--}--%>
    <%--return result;--%>
    <%--},"json")--%>
    <%--}--%>

    <%--function checkPassword() {--%>
    <%--var password = $("#password").val();--%>
    <%--var reg = /^\w{6,12}$/;--%>
    <%--var flag = reg.test(password);--%>
    <%--if (flag) {--%>
    <%--$("#password").css("border", " 1px solid rgba(255, 255, 255, 0.4)");--%>
    <%--} else {--%>
    <%--$("#password").css("border", "1px solid red");--%>
    <%--}--%>
    <%--return flag;--%>
    <%--}--%>



    <%--function change() {--%>
    <%--var date = new Date().getTime();--%>
    <%--$("#img").attr("src", "${pageContext.request.contextPath}/checkCode?" + date);--%>
    <%--}--%>

</script>
</body>
</html>

