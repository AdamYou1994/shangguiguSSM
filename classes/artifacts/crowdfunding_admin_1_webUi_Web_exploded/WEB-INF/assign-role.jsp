
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="UTF-8">
<%--引入头部，样式等--%>
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript">
	$(function() {

	});
</script>
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
					<li><a href="${ctp}/#">首页</a></li>
					<li><a href="${ctp}/#">数据列表</a></li>
					<li class="active">分配角色</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
						<form role="form" class="form-inline" action="assign/role.html">
							<input type="hidden" name="adminId" value="${param.adminId}">
							<input type="hidden" name="pageNum" value="${param.pageNum}">
							<div class="form-group">
								<label for="exampleInputPassword1">未分配角色列表</label><br> 
								<select
									class="form-control" multiple size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.unAssignedRoleList }"
										var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<ul>
									<li class="btn btn-default glyphicon glyphicon-chevron-right"></li>
									<br>
									<li class="btn btn-default glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">已分配角色列表</label><br> 
								<select
									class="form-control" multiple size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.assignedRoleList }" var="role">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</div>
							<button id="submitBtn" type="submit" style="width: 200px;"
								class="btn btn-success btn-lg btn-block">分配</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>
</body>