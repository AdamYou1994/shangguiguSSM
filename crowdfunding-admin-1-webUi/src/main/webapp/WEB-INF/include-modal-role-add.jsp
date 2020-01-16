<%@ page language="java" pageEncoding="UTF-8"%>
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form action="" role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Modal title</h4>
				</div>
				<div class="modal-body">
					<input class="form-control has-success" type="text"
						placeholder="请输入新增角色" id="roleNameInput" /> 
					<!-- <input class="form-control has-success" type="text" 
						placeholder="请输入查询条件" id="keywordInput" /> -->
				</div>
				<div class="modal-footer">
					<button type="button" id="addModalBtn" class="btn btn-danger">
						<i class="glyphicon glyphicon-plus"></i>保存
					</button>
					<button type="reset" class="btn btn-primary">
						<i class="glyphicon glyphicon-refresh"></i>重置
					</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
