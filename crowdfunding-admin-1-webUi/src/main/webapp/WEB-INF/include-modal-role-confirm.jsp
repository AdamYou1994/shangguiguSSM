<%@ page language="java" 
    pageEncoding="UTF-8"%>
<div id="confirmModal" class="modal fade table table-bordered" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">尚筹网系统弹窗</h4>
				</div>
				<div class="modal-body">
					<p>您确定要删除下面的显示内容吗？</p>

					<table class="table table-bordered">
						<thead>
							<tr>
								<th width="30">#</th>
								<th>名称</th>
							</tr>
						</thead>
						<tbody id="confirmModalTableBody">
							<!-- js处理，动态添加tr标签 -->
						</tbody>
					</table>
				</div>

				<div class="modal-footer">
					<button id="confirmModalBtn" type="button" class="btn btn-primary">OK</button>
				</div>
			</div>
		</div>
	</div>
