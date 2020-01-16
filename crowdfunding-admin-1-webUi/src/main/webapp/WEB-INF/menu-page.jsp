<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="UTF-8">
<%--引入头部，样式等--%>
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="script/my-menu.js"></script>
<script type="text/javascript">
	$(function() {
		initWholeTree();
		$("#menuAddBtn").click(function() {
			var name = $.trim($("#menuAddModal [name='name']").val());
			var url = $.trim($("#menuAddModal [name='url']").val());
			var icon = $.trim($("#menuAddModal [name='icon']:checked").val());
			if (name == null || name == "") {
				layer.msg("请填写菜单项名称");
				return;
			}

			if (url == null || url == "") {
				layer.msg("请填写菜单项对应的访问地址");
				return;
			}
			$.ajax({
				"url" : "menu/save.json",
				"type" : "post",
				"data" : {
					"name" : name,
					"url" : url,
					"pid" : window.menuId,
					"icon" : icon
				},
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (result == "SUCCESS") {
						layer.msg("操作成功！！");
						initWholeTree();
					}
					if (result == "FAILED") {
						layer.msg("加载菜单数据失败！！原因是" + response.message);
					}

				},
				"error" : function(response) {
					layer.msg("加载菜单数据失败！！原因是" + response.message);
				}
			});
			$("#menuAddModal").modal("hide");
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
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include-modal-menu-add.jsp"%>
	<%@ include file="/WEB-INF/include-modal-menu-edit.jsp"%>
</body>