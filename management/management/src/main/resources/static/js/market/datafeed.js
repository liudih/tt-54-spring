var mcmRenderFunction = function(list) {
	var content = "";
	var isEnabled = "";
	$("#bodyT tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
		if (list[i].is_enabled == 1) {
			isEnabled = "Enabled";
		} else {
			isEnabled = "Disabled";
		}
		content += '<tr class="odd gradeX">'
				+ '<td>'+ list[i].sku+ '</td>'
				+ '<td>'+ list[i].title+ '</td>'
				+ '<td>'+ list[i].oldPrice+ '</td>'
				+ '<td>'+ list[i].price+ '</td>'
				+ '<td>'+ list[i].status+ '</td>'
				+ '<td>'+ list[i].url+ '</td>'
				+ '<td>'+ list[i].pictureUrl+ '</td>'
				+ '<td>'+ list[i].categoryName+ '</td>'
				+ '<td>'+ (list[i].brand ? list[i].brand : '')+'</td>'
				+ '</tr>';
		}
	}
	$("#bodyT tbody").append(content);
}

var getIsExportFreight = function(){
	if($("#isNotExportFreight").parent().hasClass("checked")){
		return 0;
	}
	if($("#isExportFreight").parent().hasClass("checked")){
		return 1;
	}
}

$(function() {
	$("#addBtn").click(function(){
		if($("[name='categoryId']").val()==''){
			alert('请选择品类！');
			return false;
		}else{
			$("#closeB").click();
			var url = "/market/datafeed/export?language_id="+$("[name='language_id']").val()
					+"&currency_code="+$("[name='currency_code']").val()+"&plat_form="+$("[name='plat_form']").val()
					+"&categoryId="+$("[name='categoryId']").val()+"&file_type="+$("[name='file_type']").val()
					+"&is_enabled="+$("#is_enabled").val()+"&isExportFreight="+getIsExportFreight()
					+"&storageId="+$("#storageId").val()+"&country="+$("#country").val();
			window.location=url;
		}
	});
	
	
	$("#searchS").click(function() {
		var cid = $("[name='categoryId']").val();
		if(cid==""){
			alert('请选择品类！');
			return false;
		}else{
			var param = param || {}
			param.categoryId = cid;
			param.pageNo = 1;
			param._csrf=$("input[name='_csrf']").val();
			var url = "/market/datafeed/list";
			paging(url, param, mcmRenderFunction);
		}
	});
	
	//paging("/market/datafeed/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val(),"categoryId":$("[name='categoryId']").val()}, mcmRenderFunction);
	
	$('#platForm').change(function(){ 
		var p1=$(this).children('option:selected').val();//这就是selected的值 
		if(p1=='admitad'){
			$('#fileType').val('xml');
		}else if(p1=='clixgalore'){
			$('#fileType').val('csv');
		}else if(p1=='linkshare'){
			$('#fileType').val('csv');
		}else if(p1=='shareasale'){
			$('#fileType').val('csv');
		}else if(p1=='webgains'){
			$('#fileType').val('csv');
		}else if(p1=='google'){
			$('#fileType').val('excel');
		}else if(p1=='facebook'){
			$('#fileType').val('excel');
		}else{
			$('#fileType').val('csv');
		}
	}); 
	
	$("[name='isExportFreight']").change(function(){
		if($("#isExportFreight").parent().hasClass("checked")){
			$("#storageForm").show();
			$("#countryForm").show();
		}else{
			$("#storageForm").hide();
			$("#countryForm").hide();
		}
	});
});