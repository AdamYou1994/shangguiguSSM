//初始化全局变量
function initGlobalVariable(){
	//window.totalRecord = pageInfo.totalRecord;
	window.pageSize = 5;
	window.pageNum = 1;
	window.keyword = "";
}

function showPage() {
	//给服务器发送请求获取分页数据：PageInfo
	var pageInfo = getPageInfo();
	if(pageInfo == null){
		return ;
	}
	//在页面上的表格中tbody标签内显示分页的主体数据
	generateTableBody(pageInfo);
	//在页面上的表格中tfoot标签内显示分页的页码导航条
	initPagination(pageInfo);
}

//获取分页数据
function getPageInfo(){
	var ajaxResult = $.ajax({
		"url":"role/search/by/keyword.json",
		"type":"post",
		"data":{
			"pageNum": (window.pageNum == undefined) ? 1 : window.pageNum,
			"pageSize": (window.pageSize == undefined) ? 5 : window.pageSize,
			"keyword": (window.keyword == undefined) ? "" : window.keyword
		},
		"dataType":"json",
		"async":false//为了保证getPageInfo（）函数能够获取ajax请求之后的数据
		
	});
	
	//console.log(ajaxResult);
	var resultEntity = ajaxResult.responseJSON;
	//console.log(resultEntity);
	if (resultEntity.result == "SUCCESS") {
		return resultEntity.data;
	}
	if (resultEntity.result == "FAILED") {
		layer.msg(resultEntity.message);
	}
	
	return null;
}

function generateTableBody(pageInfo){
	$("#roleTableBody").empty();
	var list = pageInfo.list;
	if (list ==null || list.length == 0) {
		$("#roleTableBody").append("<tr><td colspan='4' style='text-aline:center;'>没有查询到数据</td></tr>");
		return;
	}
	
	for (var i = 0; i < list.length; i++) {
		var role = list[i];
		
		var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
		var pencilBtn = "<button roleid='"+role.id+"' type='button' class='btn btn-primary btn-xs editBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		var removeBtn = "<button roleid='"+role.id+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
		
		var numberTd = "<td>"+(i+1)+"</td>";
		var checkBoxTd = "<td><input roleid='"+role.id+"' type='checkbox' class='itemBox'></td>";
		var roleNameTd = "<td>"+role.name+"</td>";
		var btnTd = "<td>"+checkBtn +" "+pencilBtn+" "+removeBtn+"</td>";
		
		var tr = "<tr>" + numberTd +checkBoxTd+roleNameTd+btnTd+"</tr>";
		
		$("#roleTableBody").append(tr);
	}
}

function initPagination(pageInfo) {
	//声明变量存储总记录数
	//var totalRecord = ${requestScope['PAGE-INFO'].total};
	// 声明变量存储分页导航条显示时的属性设置
	
	var paginationProperties = {
			num_edge_entries: 2, //边缘页数
			num_display_entries: 3, //主体页数
			callback: pageselectCallback,//回调函数
			items_per_page:window.pageSize,//每页显示的数量
			current_page:(window.pageNum-1) ,
			prev_text:"上一页",
			next_text:"下一页"
	};
	
	//显示分页导航条
	$("#pagination").pagination(pageInfo.total,paginationProperties);
}

//每次点击上一页下一页时执行函数
function pageselectCallback(pageIndex, jq){
	//pageIndex从0开始，pageNum从1开始
	//var pageNum = ;
	window.pageNum = pageIndex + 1;
	//重新分页
	showPage();
	return false;
}
	
function getRoleListByRoleIdArray(roleIdArray) {
	var requestBody = JSON.stringify(roleIdArray);
	var ajaxResult = $.ajax({
		"url":"role/get/list/by/id/list.json",
		"type":"post",
		"data":requestBody,
		"contentType":"application/json;charset=UTF-8",
		"dataType":"json",
		"async":false//同步
	})
	var resultEntity = ajaxResult.responseJSON;
	var result = resultEntity.result;
	if (resultEntity.result == "SUCCESS") {
		return resultEntity.data;
	}
	if (resultEntity.result == "FAILED") {
		layer.msg(resultEntity.message);
	}
	
	return null;
}

function showRemoveConfirmModal() {
	$("#confirmModal").modal("show");
	var roleList = getRoleListByRoleIdArray(window.roleIdArray);
	$("#confirmModalTableBody").empty();
	for (var i = 0; i < roleList.length; i++) {
		var role = roleList[i];
		var id = role.id;
		var name = role.name;
		var trHtml = "<tr><td>" + id+"</td><td>"+name+"</td></tr>";
		$("#confirmModalTableBody").append(trHtml);
	}
}