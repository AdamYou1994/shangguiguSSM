<%--
  Created by ZWQ
  Time: 2019/12/5-15:17
  Version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
   
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
	<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#backBtn").click(function(){
				window.history.back();
			});
		});
	</script>
 	<h1>网页访问错误</h1>
    <h4>错误原因</h4>
    <p>${requestScope.exception.message}</p>
    <button id="backBtn">后退</button>
</div>
</body>
</html>
