<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="UTF-8">
<%--引入头部，样式等--%>
<head>
<%@ include file="/WEB-INF/include-head.jsp"%>

<link rel="stylesheet" href="css/pagination.css" />
<script src="script/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="script/my-role.js"></script>
<script type="text/javascript">
	$(function() {
		initGlobalVariable();
		showPage();

		$("#searchBtn").click(function() {
			var keyword = $.trim($("#keywordInput").val());
			if (keyword == null || keyword == "") {
				layer.msg("请输入关键词！");
				return;
			}
			window.keyword = keyword;

			showPage();
		});

		$("#summaryBox").click(function() {
			//debugger;
			var currentStatus = this.checked;
			$(".itemBox").prop("checked", currentStatus);
		});

		$("#batchRemoveBtn").click(function() {
			var length = $(".itemBox:checked").length;

			if (length == 0) {
				layer.msg("请至少选择一条！！");
				return;
			}

			window.roleIdArray = new Array();
			$(".itemBox:checked").each(function() {
				var roleId = $(this).attr("roleid");
				window.roleIdArray.push(roleId);
			});

			showRemoveConfirmModal();
		});

		$("#confirmModalBtn").click(function() {
			var requestBody = JSON.stringify(window.roleIdArray);
			$.ajax({
				"url" : "role/batch/remove.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (response.result == "SUCCESS") {
						layer.msg("操作成功！！");
						$("#summaryBox").prop("checked", false);
						showPage();
					}
					if (response.result == "FAILED") {
						layer.msg(response.message);
					}
				}
			});
			$("#confirmModal").modal("hide")

		});

		//动态生成的html标签，不能使用动态的class，id绑定事件，需要其祖先中的静态标签绑定事件
		$("#roleTableBody").on("click", ".removeBtn", function() {
			//debugger;
			var roleId = $(this).attr("roleid");
			window.roleIdArray = new Array();
			window.roleIdArray.push(roleId);
			showRemoveConfirmModal();
		});

		$("#addBtn").click(function() {
			$("#addModal").modal("show");
		});
		$("#addModalBtn").click(function() {
			var roleName = $.trim($("#roleNameInput").val());
			if (roleName == null || roleName == "") {
				layer.msg("请输入有效角色名称！！");
				return;
			}
			$.ajax({
				"url" : "role/save/role.json",
				"type" : "post",
				"data" : {
					"roleName" : roleName
				},

				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (response.result == "SUCCESS") {
						layer.msg("操作成功！！");
						window.pageNum = 999999;
						showPage();
					}
					if (response.result == "FAILED") {
						layer.msg(response.message);
					}
					$("#confirmModal").modal("hide");
					$("#roleNameInput").val("");
				}
			});
		});

		$("#roleTableBody").on("click", ".editBtn", function() {
			window.roleId = $(this).attr("roleid");
			var roleName = $(this).parents("tr").children("td:eq(2)").text();
			$("#roleNameInputEdit").val(roleName);
			$("#eidtModal").modal("show");
		});
		$("#editModalBtn").click(function() {
			var roleName = $.trim($("#roleNameInputEdit").val());
			if (roleName == null || roleName == "") {
				layer.msg("请输入有效角色名称！！");
				return;
			}
			$.ajax({
				"url" : "role/update/role.json",
				"type" : "post",
				"data" : {
					"id" : window.roleId,
					"name" : roleName
				},
				"dataType" : "json",
				"success" : function(response) {
					var result = response.result;
					if (response.result == "SUCCESS") {
						layer.msg("操作成功！！");
						showPage();
					}
					if (response.result == "FAILED") {
						layer.msg(response.message);
					}
					$("#eidtModal").modal("hide");
					$("#roleNameInputEdit").val("");
				}
			});

		});

		$("#roleTableBody").on("click", ".checkBtn", function() {
			window.roleId = $(this).attr("roleid");
			$("#roleAssignAuthModal").modal("show");
			var setting = {
					"data":{
						"simpleData":{
							"enable":true,
							"pIdKey": "categoryId"
						},
						"key":{
							"name":"title"
						}
					},
					"check":{
						"enable":true
					}
			};
			var ajaxResult = $.ajax({
				"url" : "assign/get/all/auth.json",
				"type" : "post",
				"dataType" : "json",
				"async" : false
			});
			
			if (ajaxResult.responseJSON.result == "FAILED") {
				layer.msg(ajaxResult.responseJSON.message);
				return ;
			}
			var zNodes = ajaxResult.responseJSON.data;
			$.fn.zTree.init($("#treeDemo"),setting,zNodes);
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);;
			
			//根据以前的信息勾选树形节点
			var ajaxResult = $.ajax({
				"url" : "assign/get/assigned/auth/id/list.json",
				"type" : "post",
				"dataType" : "json",
				"data":{
					"roleId":$(this).attr("roleId"),
					"random":Math.random()
				},
				"async" : false
			});
			if (ajaxResult.responseJSON.result == "FAILED") {
				layer.msg(ajaxResult.responseJSON.message);
				return ;
			}
			var authIdList = ajaxResult.responseJSON.data;
			//便利authIdList 勾选对应的树形节点
			for (var i = 0; i < authIdList.length; i++) {
				var authId = authIdList[i];
				var key = "id";
				var treeNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key,authId);
				//找到对应的节点，treeNode当前需要勾选的节点，true表示设置为勾选状态， false表示不联动
				$.fn.zTree.getZTreeObj("treeDemo").checkNode(treeNode,true,false);
			}
		});
		
		$("#roleAssignAuthBtn").click(function() {
			var authIdArray = new Array();
			var checkeNodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes();
			
			for (var i = 0; i < checkeNodes.length; i++) {
				var node = checkeNodes[i];
				var authId = node.id;
				authIdArray.push(authId);
			}
			//[window.roleId]方便后台接收数据
			var resquestBody = {"roleIdList":[window.roleId],"authIdList":authIdArray};
			var ajaxResult = $.ajax({
				"url":"assign/do/assign.json",
				"type":"post",
				"data":JSON.stringify(resquestBody),
				"contentType":"application/json;charset=UTF-8",
				"dataType":"json",
				"async":false
			});
			var resultEntity = ajaxResult.responseJSON;
			if (resultEntity.result == "SUCCESS") {
				return resultEntity.data;
			}
			if (resultEntity.result == "FAILED") {
				layer.msg(resultEntity.message);
			}
			$("#roleAssignAuthModal").modal("hide");
		});
	})
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
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										placeholder="请输入查询条件" id="keywordInput">
								</div>
							</div>
							<button type="button" class="btn btn-warning" id="searchBtn">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger" id="batchRemoveBtn"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary" id="addBtn"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox" id="summaryBox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="roleTableBody">
									<!-- js处理，动态添加tr标签 -->
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination" id="pagination">
												<!-- 显示分页 -->
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


	<%@ include file="/WEB-INF/include-modal-role-confirm.jsp"%>
	<%@ include file="/WEB-INF/include-modal-role-add.jsp"%>
	<%@ include file="/WEB-INF/include-modal-role-edit.jsp"%>
	<%@ include file="/WEB-INF/include-modal-assign-auth.jsp"%>
</body>
</html>
