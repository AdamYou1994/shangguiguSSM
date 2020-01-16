<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="UTF-8">
<%--引入头部，样式等--%>
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>

<link rel="stylesheet" href="css/pagination.css"/>
<script src="script/jquery.pagination.js"></script>
<script src="script/my-admin.js"></script>
<script type="text/javascript">
$(function(){
	//初始化全局变量
	window.totalRecord = ${requestScope['PAGE-INFO'].total};
	window.pageSize = ${requestScope['PAGE-INFO'].pageSize};
	window.pageNum = ${requestScope['PAGE-INFO'].pageNum};
	
	window.keyword = "${param.keyword}";
	
	initPagination();
	
	//全选全不选功能
	$("#summaryBox").click(function() {
		$(".itemBox").prop("checked",this.checked);
	});
	
	//批量删除按钮绑定响应函数
	$("#batchRemoveBtn").click(function(){
		var adminIdArray = new Array();
		var loginacctArray = new Array();
		//通过jquery选择器定位到所有选中的itemBox，然后便利
		$(".itemBox:checked").each(function(){
			//this.adminId 拿不到值，原因是：this作为DOM对象无法读取到html标签本身没有的属性
			//将this转换为jquery对象调用attr()函数就能够获取到该值
			var adminId = $(this).attr("adminId");
			adminIdArray.push(adminId); 
			var loginacct = $(this).parent("td").next().text();//当前的对象的父类td的下一个td中的text元素
			loginacctArray.push(loginacct);
		});
		if (adminIdArray.length == 0) {
			alert("请勾选你要删除的数据");
			
			return;
		}
		var confirmResult = confirm("您真的要删除"+loginacctArray+"信息吗？操作不可逆，请慎重决定。");
		if (!confirmResult) {
			return;
		}
		doBatchRemove(adminIdArray,pageNum,keyword);
	});
	
	//单个删除
	$(".uniqueBatchRemoveBtn").click(function(){
		var adminIdArray = new Array();
		var loginacctArray = new Array();
		//this.adminId 拿不到值，原因是：this作为DOM对象无法读取到html标签本身没有的属性
		//将this转换为jquery对象调用attr()函数就能够获取到该值
		var adminId = $(this).attr("adminId");
		adminIdArray.push(adminId); 
		//alert(adminId);
		var loginacct = $(this).parents("tr").children("td:eq(2)").text();
		//alert(loginacct);
		loginacctArray.push(loginacct);
		doBatchRemove(adminIdArray,pageNum,keyword);
	});
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
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form action="admin/query/for/search.html" method="post" class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
									 name="keyword"	placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger" id="batchRemoveBtn"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<a href="admin/to/add/page.html" class="btn btn-primary"
							style="float: right;" >
							<i class="glyphicon glyphicon-plus"></i> 新增
						</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox" id="summaryBox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty requestScope['PAGE-INFO'].list }">
										<tr>
											<td colspan="6" style="text-align: center;">抱歉！没有符合您要求的查询结果</td>
										</tr>
									</c:if>
									<c:if test="${!empty requestScope['PAGE-INFO'].list }">
										<c:forEach items="${requestScope['PAGE-INFO'].list }" var="admin" varStatus="myStatus">
											<tr>
											<td>${myStatus.count }</td>
											<!-- adminId自行设定的标记 -->
											<td><input adminId="${admin.id}" class="itemBox" type="checkbox"></td>
											<td>${admin.loginacct }</td>
											<td>${admin.username }</td>
											<td>${admin.email }</td>
											<td>
												<a href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope['PAGE-INFO'].pageNum}" class="btn btn-success btn-xs">
													<i class=" glyphicon glyphicon-check"></i>
												</a>
												<a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope['PAGE-INFO'].pageNum}" class="btn btn-primary btn-xs">
													<i class=" glyphicon glyphicon-pencil"></i>
												</a>
												<button adminId="${admin.id}" type="button" class="btn btn-danger btn-xs uniqueBatchRemoveBtn">
													<i class=" glyphicon glyphicon-remove"></i>
												</button>
											</td>
										</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination" id="pagination"><!-- 显示分页 -->
											</ul>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>

