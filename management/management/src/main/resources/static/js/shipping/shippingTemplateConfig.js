var dataRenderFunction = function(list) {
	var content = "";
	var isEespecial = "";
	var isFreeshipping = "";
	var isCountWeight = "";
	var filtername = "";
	var storageName = "";
	var shippingTemplateName = "";
	var shippingTypeName = "";
	var country = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if(list[i].is_especial == 1){
				isEespecial = "yes";
			}
			if(list[i].is_especial == 0){
				isEespecial = "no";
			}
			if(list[i].is_freeshipping == 1){
				isFreeshipping = "yes";
			}
			if(list[i].is_freeshipping == 0){
				isFreeshipping = "no";
			}
			if(list[i].is_calculate_weight == 0){
				isCountWeight = "no";
			}
			if(list[i].is_calculate_weight == 1){
				isCountWeight = "yes";
			}
			if(list[i].country_add_type == 2){
				country = "all";
			}else{
				country = list[i].country;
			}
			filtername = list[i].filter == null ? "" : list[i].filter.name;
			storageName = list[i].storage == null ? "" : list[i].storage.cstoragename;
			shippingTemplateName = list[i].shippingTemplate == null ? "" : list[i].shippingTemplate.template_name;
			shippingTypeName = list[i].shippingType == null ? "" : list[i].shippingType.type_name;
			content += '<tr class="odd gradeX">'
					+ '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="stcCheckbox'+ i+ '"/> </td>'
					+ '<td id="stcId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td>'+ shippingTemplateName + '</td>'
					+ '<td>'+ shippingTypeName + '</td>'
					+ '<td>'+ storageName + '</td>'
					+ '<td>'+ country+ '</td>'
					+ '<td>'+ filtername + '</td>'
					+ '<td class="tabl_min_200">'+ stringShow(list[i].priority_shipping_code) + '</td>'
					+ '<td>'+ isEespecial + '</td>'
					+ '<td>'+ isFreeshipping + '</td>'
					+ '<td>'+ isCountWeight + '</td>'
					+ '<td>'+ stringShow(list[i].amountLimit)+ '</td>'
					+ '<td>'+ stringShow(list[i].weightLimit)+ '</td>'
					+ '<td>'+ stringShow(list[i].extra_charge)+ '</td>'
					+ '<td>'+ stringShow(list[i].extra_charge_note)+ '</td>'
					+ '<td>'+ list[i].isEnabled+ '</td>'
					+ '<td>'+ stringShow(list[i].whoCreated)+ '</td>'
					+ '<td>'+ stringShow(list[i].crateTime)+ '</td>'
					+ '<td>'+ stringShow(list[i].whoModified)+ '</td>'
					+ '<td>'+ stringShow(list[i].updateTime)+ '</td>'
					+ '</tr>';
		}
	}
	$("#dataTable tbody").append(content);
}

var getCountryAddType = function(){
	if($("#includeCountry").parent().hasClass("checked")){
		return 0;
	}
	if($("#debarCountry").parent().hasClass("checked")){
		return 1;
	}
	if($("#allCountry").parent().hasClass("checked")){
		return 2;
	}
}

var validator = $("#stcForm").validate({  
    rules : {  
    	warehouse_id : {required : true},
    	shipping_template_id : {required : true},
    	shipping_type_id : {required : true},
    	country : {required : true},
    	is_especial : {required : true},
    	is_freeshipping : {required : true},
    	is_calculate_weight : {required : true},
    	priority_shipping_code : {required : true},
    	filter_id : {required : true},
    	is_enabled : {required : true}
    },
    messages : {  
    	warehouse_id : {required : $("#storageCheck").html()},  
    	shipping_template_id : {required : $("#shippingTemplateCheck").html()},
    	shipping_type_id : {required : $("#shippingTypeCheck").html()},
    	country : {required : $("#shippingCountryCheck").html()},
    	is_especial : {required : $("#isEspecialCheck").html()},
    	is_freeshipping : {required : $("#isFreeshippingCheck").html()},
    	is_calculate_weight : {required : $("#isCalculateCheck").html()},
    	priority_shipping_code : {required : $("#shippingCodeCheck").html()},
    	filter_id : {required : $("#filterConditionsCheck").html()},
    	is_enabled : {required : $("#isEnabledCheck").html()}
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
    	var url = null;
    	var param = {};
		param.warehouse_id = $("[name='warehouse_id']").val();
		param.shipping_template_id = $("[name='shipping_template_id']").val();
		param.shipping_type_id = $("[name='shipping_type_id']").val();
		param.country_add_type = getCountryAddType();
		param.country = $("[name='country']").val();
		param.is_especial = $("[name='is_especial']").val();
		param.is_freeshipping = $("[name='is_freeshipping']").val();
		param.is_calculate_weight = $("[name='is_calculate_weight']").val();
		param.priority_shipping_code = $("[name='priority_shipping_code']").val();
		param.filter_id = $("[name='filter_id']").val();
		param.start_amount = $("[name='start_amount']").val();
		param.amount_limit = $("[name='amount_limit']").val();
		param.start_weight = $("[name='start_weight']").val();
		param.weight_limit = $("[name='weight_limit']").val();
		param.extra_charge = $("[name='extra_charge']").val();
		param.extra_charge_note = $("[name='extra_charge_note']").val();
		param.is_enabled = $("[name='is_enabled']").val();
		param.id = $("[name='id']").val();
		url = "/shipping/templateConfig/shippingTemplateConfigUV"+"?_csrf="+$("input[name='_csrf']").val();
		$.post(url,param,function(data){
			if(data){
				$("#repeatSTCShowButton").click();
				return;
			}
	    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
	    		url = "/shipping/templateConfig/update"+"?_csrf="+$("input[name='_csrf']").val();
	    		$.post(url,param,function(data){
	    			if(data.status == 1){
	    				if(data.type == "country"){
	    					showInfoMessage("国家代码 "+data.desc+" 无效");
	    				}
	    				if(data.type == "shippingCode"){
	    					showInfoMessage("发货代码 "+data.desc+" 无效");
	    				}
						return;
					}
	    			if(data.status == 0){
						$("#saveCancel").click();
						$("#dataSearch").click();
						$("#updateSuccessButton").click();
					}else{
						$("#updateFailedButton").click();
					}
	    		});
	    	}else{
	    		url = "/shipping/templateConfig/add"+"?_csrf="+$("input[name='_csrf']").val();
	    		$.post(url,param,function(data){
	    			if(data.status == 1){
	    				if(data.type == "country"){
	    					showInfoMessage("国家 "+data.desc+" 无效");
	    				}
	    				if(data.type == "shippingCode"){
	    					showInfoMessage("发货代码 "+data.desc+" 无效");
	    				}
						return;
					}
	    			if(data.status == 0){
						$("#saveCancel").click();
						$("#dataSearch").click();
						$("#addSuccessButton").click();
					}else{
						$("#addFailedButton").click();
					}
	    		});
	    	}
		});
    }
});
var update = function(i){
	validator.resetForm();
	var url = "/shipping/templateConfig/get/"+$("#stcId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='warehouse_id']").val(data.warehouse_id);
		$("[name='shipping_template_id']").val(data.shipping_template_id);
		$("[name='shipping_type_id']").val(data.shipping_type_id);
		$("[name='country']").val(data.country);
		$("[name='is_especial']").val(data.is_especial);
		$("[name='is_freeshipping']").val(data.is_freeshipping);
		$("[name='is_calculate_weight']").val(data.is_calculate_weight);
		$("[name='priority_shipping_code']").val(data.priority_shipping_code);
		$("[name='filter_id']").val(data.filter_id);
		$("[name='start_amount']").val(data.start_amount);
		$("[name='amount_limit']").val(data.amount_limit);
		$("[name='start_weight']").val(data.start_weight);
		$("[name='weight_limit']").val(data.weight_limit);
		$("[name='extra_charge']").val(data.extra_charge);
		$("[name='extra_charge_note']").val(data.extra_charge_note);
		$("[name='is_enabled']").val(data.is_enabled);
		$("#includeCountry").parent().removeClass("checked");
		$("#debarCountry").parent().removeClass("checked");
		$("#allCountry").parent().removeClass("checked");
		if(data.country_add_type == 0){
			$("#includeCountry").parent().addClass("checked");
			$("#country").show();
		}
		if(data.country_add_type == 1){
			$("#debarCountry").parent().addClass("checked");
			$("#country").show();
		}
		if(data.country_add_type == 2){
			$("#allCountry").parent().addClass("checked");
			$("#country").hide();
		}
		$("[name='shipping_template_id']").prop("disabled", true);
		$("[name='shipping_type_id']").prop("disabled", true);
		$("#stcShowButton").click();
	});
}
$(function() {
	paging("/shipping/templateConfig/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/shipping/templateConfig/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.shipping_template_id = $("#templateName").val();
		searchParam.shipping_type_id = $("#shippingType").val();
		searchParam.country = $("#shippingCountry").val();
		searchParam.filter_id = $("#filterConditions").val();
		searchParam.is_freeshipping = $("#isFreeshipping").val();
		searchParam.is_especial = $("#isEspecial").val();
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
	});
	
	$("#addButton").click(function() {
		validator.resetForm();
		$("[name='id']").val(null);
		$("#includeCountry").parent().removeClass("checked");
		$("#debarCountry").parent().removeClass("checked");
		$("#allCountry").parent().removeClass("checked");
		$("#country").show();
		$("[name='shipping_template_id']").prop("disabled", false);
		$("[name='shipping_type_id']").prop("disabled", false);
		$("#stcShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$(".checkboxes").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var Ids = "";
		$(".checkboxes:checked").each(function(i){
			Ids += ","+$("#stcId"+i).html();
		});
		if(Ids == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#stcCheckbox"+i).prop("checked")){
				Ids += ","+$("#stcId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/shipping/templateConfig/delete/"+Ids.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(data){
			if(data){
				$("#dataSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		})
	});
	
	$("[name='country_add_type']").change(function(){
		if($("#allCountry").parent().hasClass("checked")){
			$("#country").hide();
		}else{
			$("#country").show();
		}
	});
});