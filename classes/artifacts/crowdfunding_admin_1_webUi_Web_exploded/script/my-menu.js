function showMyIcon(treeId,treeNode) {
	var currentNodeId = treeNode.tId;
	var newClass = treeNode.icon;
	var targetSpanId = currentNodeId+"_ico";
	$("#"+targetSpanId).removeClass().addClass(newClass).css("background","");
}

function initWholeTree() {
	var setting = {
			"view":{
				"addDiyDom":showMyIcon,
				"addHoverDom":addHoverDom,
				"removeHoverDom":removeHoverDom
			},
			"data":{
				"key":{
					"url":"xURL"
				}
			}
	};
	
	$.ajax({
		"url":"menu/get/whole/tree.json",
		"type":"post",
		"dataType":"json",
		"success":function(response){
			var result = response.result;
			if (result == "SUCCESS") {
				var zNodes = response.data;
				$.fn.zTree.init($("#treeDemo"),setting,zNodes);
			}
			if (result == "FAILED") {
				layer.msg("加载菜单数据失败！！原因是"+response.message);
			}
		},
		"error":function(response){
			layer.msg("加载菜单数据失败！！原因是"+response.message);
		}
	});
}

function addHoverDom(treeId, treeNode) {
	var btnGrpSpanId = treeNode.tId+"_btnGrp";
	var btnGrpSpanLength = $("#"+btnGrpSpanId).length;
	if (btnGrpSpanLength > 0) {
		return ;
	}
	
	var anchorId = treeNode.tId+"_a";
	var $btnGrpSpan =generateBtnGrp(treeNode);
	$("#"+anchorId).after($btnGrpSpan);
}

function removeHoverDom(treeId, treeNode) {
	var btnGrpSpanId = treeNode.tId+"_btnGrp";
	$("#"+btnGrpSpanId).remove(); 
}

function generateBtnGrp(treeNode) {
	var treeNodeId = treeNode.tId;
	var menuId = treeNode.id;
	var btnGrpSpanId = treeNodeId +"_btnGrp";
	var $span = $("<span id='"+btnGrpSpanId+"'></span>");
	var level = treeNode.level;
	var addBtn = '<a onclick="showAddModal(this)" id="'+menuId+'" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
	var editBtn =  '<a onclick="showEditModal(this)" id="'+menuId+'" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
	var removeBtn = '<a onclick="showAddModal(this)" id="'+menuId+'" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
	if(level == 0){
		$span.append(addBtn);
	}
	if(level ==1){
		if (treeNode.children.length > 0) {
			$span.append(addBtn+" "+removeBtn);
		}else {
			$span.append(addBtn+" "+editBtn+" "+removeBtn);
		}
	}
	if(level ==2){
		$span.append(editBtn+" "+removeBtn);
	}
	return $span;
}

function showAddModal(currentBtn) {
	$("#menuAddModal").modal("show");
	window.menuId = currentBtn.id;
}
function showEditModal(currentBtn) {
	$("#menuEditModal").modal("show");
	window.menuId = currentBtn.id;
	$.ajax({
		"url":"menu/get/"+window.menuId+".json",
		"type":"get",
		"dataType":"json",
		"success":function(response){
			var result = response.result;
			if (result == "SUCCESS") {
				var menu = response.data;
				var name = menu.name;
				var url = menu.url;
				var icon = menu.icon;
				
				$("#menuEditModal [name='name']").val(name);
				$("#menuEditModal [name='url']").val(url);
				$("#menuEditModal [name='icon'][value='"+icon+"']").attr("checked",true);
			}
			if (result == "FAILED") {
				layer.msg("加载菜单数据失败！！原因是"+response.message);
			}
		},
		"error":function(response){
			layer.msg("加载菜单数据失败！！原因是"+response.message);
		}
	});
}