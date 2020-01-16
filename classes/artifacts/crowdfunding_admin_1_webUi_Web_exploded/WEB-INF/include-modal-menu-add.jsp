<%@ page language="java" pageEncoding="UTF-8"%>
<div class="modal fade" id="menuAddModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">尚筹网系统弹窗</h4>
			</div>
			<form action="">
				<div class="modal-body">
					请输入节点名称:<input type="text" name="name"/><br/>
					请输入URL地址:<input type="text" name="url"/><br/>
					<input type="radio" name="icon" value="glyphicon glyphicon-th-list" /><i class="glyphicon glyphicon-th-list"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-dashboard" /><i class="glyphicon glyphicon-dashboard"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon glyphicon-tasks" /><i class="glyphicon glyphicon glyphicon-tasks"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-user" /><i class="glyphicon glyphicon-user"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-king" /><i class="glyphicon glyphicon-king"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-lock" /><i class="glyphicon glyphicon-lock"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-check" /><i class="glyphicon glyphicon-check"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-th-large" /><i class="glyphicon glyphicon-th-large"></i>
					<br/>
					<input type="radio" name="icon" value="glyphicon glyphicon-picture" /><i class="glyphicon glyphicon-picture"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-equalizer" /><i class="glyphicon glyphicon-equalizer"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-random" /><i class="glyphicon glyphicon-random"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-hdd" /><i class="glyphicon glyphicon-hdd"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-comment" /><i class="glyphicon glyphicon-comment"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-list" /><i class="glyphicon glyphicon-list"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-tags" /><i class="glyphicon glyphicon-tags"></i>
					<input type="radio" name="icon" value="glyphicon glyphicon-list-alt" /><i class="glyphicon glyphicon-list-alt"></i>
					
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" id="menuAddBtn" class="btn btn-success" data-dismiss="modal"><i class="glyphicon glyphicon-plus"></i></button>
				<button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i></button>
			</div>

		</div>
	</div>
</div>