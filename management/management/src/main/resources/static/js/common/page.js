//当前页
var pageNo = 1;
//每页显示多少条数据
var pageLimit = 20;
//共多少页
var pageTotal;
//加载数据的url
var pageUrl;
//加载数据的参数
var pageParam;
//渲染数据的方法
var pageRenderFunction;
//分页栏显示多少个分页项
var pageTotalShow = 10;

//分页方法
var paging = function(url,param,loadFunction){
	pageUrl = url;
	pageParam = param;
	pageParam.pageLimit = pageLimit;
	pageRenderFunction = loadFunction;
	$.post(pageUrl,pageParam,function(data){
		pageRenderFunction(data.list);
		pageNo = data.pageNo;
		pageTotal = data.totalPage;
		pageRefresh(pageNo);
	})
}

//刷新分页栏
var pageRefresh = function(no){
	$("#paginate").hide();
	var pageLi = "";
	var i;
	pageLi += '<ul class="pagination" style="visibility: visible;">'+
		'<li>'+
			'<a title="First" href="javascript:;" onclick="pageFirst()"><<</a>'+
		'</li>'+
		'<li id="pagePrev">'+
			'<a title="Prev" href="javascript:;" onclick="pagePrev()"><</a>'+
		'</li>';
	if(pageTotal <= pageTotalShow){
		for(i=1;i<=pageTotal;i++){
			if(i == pageNo){
				pageLi += '<li class="active">'+
				'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
				'</li>';
			}else{
				pageLi += '<li>'+
					'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
					'</li>';
			}
		}
	}else{
		if(pageNo <= parseInt(pageTotalShow/2)){
			for(i=1;i<=pageTotalShow;i++){
				if(i == pageNo){
					pageLi += '<li class="active">'+
					'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
					'</li>';
				}else{
					pageLi += '<li>'+
						'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
						'</li>';
				}
			}
		}else if(pageTotal-pageNo <= parseInt(pageTotalShow/2)){
			for(i=pageTotal-(pageTotalShow-1);i<=pageTotal;i++){
				if(i == pageNo){
					pageLi += '<li class="active">'+
					'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
					'</li>';
				}else{
					pageLi += '<li>'+
						'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
						'</li>';
				}
			}
		}else{
			for(i=pageNo-parseInt(pageTotalShow/2);i<=pageNo+parseInt(pageTotalShow/2);i++){
				if(i == pageNo){
					pageLi += '<li class="active">'+
					'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
					'</li>';
				}else{
					pageLi += '<li>'+
						'<a href="javascript:;" onclick="pageAssign(this)">'+i+'</a>'+
						'</li>';
				}
			}
		}
	}
	pageLi += '<li>'+
			'<a title="Next" href="javascript:;" onclick="pageNext()">></a>'+
			'</li>'+
			'<li>'+
				'<a title="Last" href="javascript:;" onclick="pageLast()">>></a>'+
			'</li>'
		'</ul>';
	$("#paginate").empty();
	$("#paginate").append(pageLi);
	$("#paginate").show();
}

//指定第几页
var pageAssign = function(o){
	pageParam.pageNo = $(o).html();
	pageParam.pageLimit = pageLimit;
	paging(pageUrl,pageParam,pageRenderFunction);
}

//第一页
var pageFirst = function(){
	if(pageNo == 1){
		return;
	}
	pageParam.pageNo = 1;
	pageParam.pageLimit = pageLimit;
	paging(pageUrl,pageParam,pageRenderFunction);
}
//前一页
var pagePrev = function(){
	if(pageNo == 1){
		return;
	}
	pageParam.pageNo--;
	pageParam.pageLimit = pageLimit;
	paging(pageUrl,pageParam,pageRenderFunction);
}
//后一页
var pageNext = function(){
	if(pageNo == pageTotal){
		return;
	}
	pageParam.pageNo++;
	pageParam.pageLimit = pageLimit;
	paging(pageUrl,pageParam,pageRenderFunction);
}
//最后一页
var pageLast = function(){
	if(pageNo == pageTotal){
		return;
	}
	pageParam.pageNo = pageTotal;
	pageParam.pageLimit = pageLimit;
	paging(pageUrl,pageParam,pageRenderFunction);
}

$(function(){
	$("#pageLimitSelect").change(function(){
		pageLimit = $(this).val();
		pageParam.pageNo = 1;
		pageParam.pageLimit = $(this).val();
		paging(pageUrl,pageParam,pageRenderFunction);
	});
});