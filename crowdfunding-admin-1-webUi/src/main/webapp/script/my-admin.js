function doBatchRemove(adminIdArray) {
	
	
	var requestBody = JSON.stringify(adminIdArray);
	//发送ajax请求
	$.ajax({
		"url":"admin/batch/remove.json",//服务器接受请求url
		"type":"post",
		"contentType":"application/json;charset=UTF-8",
		"data":requestBody,
		"dataType":"json",//把服务器端返回的数据当作json格式解析
		"success":function(response){
			window.location.href = "admin/query/for/search.html?pageNum="+window.pageNum +  
			"&keyword="+window.keyword;
		},
		"error":function(response){
			alert(response.message);
			return;
		}
	});
}

function initPagination() {
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
	$("#pagination").pagination(window.totalRecord,paginationProperties);
}

//每次点击上一页下一页时执行函数
function pageselectCallback(pageIndex, jq){
	//pageIndex从0开始，pageNum从1开始
	//var pageNum = ;
	window.pageNum = pageIndex + 1;
	//跳转页面
	window.location.href = "admin/query/for/search.html?pageNum=" + (pageIndex + 1) 
			+ "&keyword="+window.keyword;
	return false;
}
	
