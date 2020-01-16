<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="UTF-8">
<%--引入头部，样式等--%>
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script src="script/jquery.pagination.js"></script>
<script src="script/my-admin.js"></script>
</head>
<body>
	<%--引入公共标题栏--%>
	<%@ include file="/WEB-INF/include-nav.jsp"%>

	<div class="container-fluid">
		<div class="row">

			<%--引入公共导航栏--%>
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">修改</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">
						表单数据
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<form:form action="admin/update.html" method="post"
							modelAttribute="admin">
							<!-- 模型对象中包含的属性不能使用form：标签 -->
							<form:hidden path="id"/>
							<!-- 模型对象中没有的属性不能使用form：标签 -->
							<input type="hidden" name="pageNum" value="${param.pageNum}"/>
							<div class="form-group">
								<label for="exampleInputPassword1">登陆账号</label>
								<form:input cssClass="form-control" path="loginacct"/>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">登陆密码</label>
								<form:input cssClass="form-control" path="userpswd" text="password"/>
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">用户名称</label>
								<form:input cssClass="form-control" path="username"/>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">邮箱地址</label>
								<form:input cssClass="form-control" path="email"/>
								<br/>
									<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为：
										xxxx@xxxx.com</p>
							</div>
							<button type="submit" class="btn btn-success">
								<i class="glyphicon glyphicon-edit"></i> 修改
							</button>
							<button type="reset" class="btn btn-danger">
								<i class="glyphicon glyphicon-refresh"></i> 重置
							</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="jquery/jquery-2.1.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="script/docs.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
		});
	</script>
</body>
</html>