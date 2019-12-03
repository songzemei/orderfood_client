<%@ page contentType="text/html;charset=UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>结果</title>
</head>
<c:if test="${result.result}">
    <body style="background: url('${pageContext.request.contextPath}/img/success.jpg');background-size: 100%">
    <h1>${result.message}</h1>
    </body>
</c:if>

<c:if test="${!result.result}">
    <body style="background: url('${pageContext.request.contextPath}/img/img1.jpg');background-size: 100%">
    <h1>${result.message}</h1>
    </body>
</c:if>

</html>
