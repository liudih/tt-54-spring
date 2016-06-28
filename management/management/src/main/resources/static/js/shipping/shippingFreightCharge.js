var dataRenderFunction = function(list) {
	var content = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			content += '<tr class="odd gradeX">'
				+ '<td>'+ stringShow(list[i].country) + '</td>'
				+ '<td>'+ stringShow(list[i].storageName) + '</td>'
				+ '<td>'+ stringShow(list[i].shippingTypeName) + '</td>'
				+ '<td>'+ stringShow(list[i].shippingCode) + '</td>'
				+ '<td>'+ stringShow(list[i].price) + '</td>'
				+ '</tr>';
		}
	}else{
		showInfoMessage("没有物流运费计算结果");
	}
	$("#dataTable tbody").append(content);
}
var validator = $("#sfcForm").validate({  
    rules : {  
    	country : {required : true},
    	weight : {required : true},
    	storage : {required : true},
    	shippingType : {required : true}
    },
    messages : {  
    	country : {required : $("#shippingCountryCheck").html()},
    	weight : {required : $("#weightCheck").html()},
    	storage : {required : $("#storageCheck").html()},
    	shippingType : {required : $("#shippingTypeCheck").html()}
    },
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",
    submitHandler : function(){
    	var url = "/shipping/freightCharge/freightCalculate";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.countryId = $("#country").val();
		searchParam.weight = $("#weight").val();
		searchParam.storageNameId = $("#storage").val();
		searchParam.shippingTypeId = $("#shippingType").val();
		searchParam.sort = "asc";
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
    }
});
$(function(){
	$("#tfreight").click(function(){
		if($(this).hasClass("sorting_asc")){
			$(this).removeClass("sorting_asc");
			$(this).addClass("sorting_desc");
			var url = "/shipping/freightCharge/freightCalculate";
			var searchParam = {};
			searchParam.pageNo = 1;
			searchParam.countryId = $("#country").val();
			searchParam.weight = $("#weight").val();
			searchParam.storageNameId = $("#storage").val();
			searchParam.shippingTypeId = $("#shippingType").val();
			searchParam.sort = "desc";
			searchParam._csrf=$("input[name='_csrf']").val();
			paging(url, searchParam, dataRenderFunction);
			return;
		}
		if($(this).hasClass("sorting_desc")){
			$(this).removeClass("sorting_desc");
			$(this).addClass("sorting_asc");
			var url = "/shipping/freightCharge/freightCalculate";
			var searchParam = {};
			searchParam.pageNo = 1;
			searchParam.countryId = $("#country").val();
			searchParam.weight = $("#weight").val();
			searchParam.storageNameId = $("#storage").val();
			searchParam.shippingTypeId = $("#shippingType").val();
			searchParam.sort = "asc";
			searchParam._csrf=$("input[name='_csrf']").val();
			paging(url, searchParam, dataRenderFunction);
			return;
		}
	});
});