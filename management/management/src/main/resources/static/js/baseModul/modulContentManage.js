var validator = $("#modulContentForm").validate({  
    rules : {  
    	client_id : "required",
    	language_id : "required",
    	layout_id : "required",
    	layout_module_id : "required"
    },
    messages : {  
    	client_id : $("#clientCheck").html(),  
    	language_id : $("#languageCheck").html(), 
    	layout_id : $("#layoutNameCheck").html(), 
    	layout_module_id : $("#moduleNameCheck").html()
    },
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",
    submitHandler : function(form){
    	var url;
		var param = {};
		param.client_id = $("[name='client_id']").val();
		param.language_id = $("[name='language_id']").val();
		param.layout_id = $("[name='layout_id']").val();
		param.layout_module_id = $("[name='layout_module_id']").val();
		param.is_show = $("[name='is_show']").val();
		param.category_id = getFormCategory();
		param.sku = $("[name='sku']").val();
		param.sort = $("[name='sort']").val();
		param.is_enabled = $("[name='is_enabled']").val();
		param._csrf=$("input[name='_csrf']").val();
		if($("[name='id']").val() != null && $("[name='id']").val() != ""){
			param.id = $("[name='id']").val();
			url = "/base/modulContent/update";
			$.post(url,param,function(data){
				if(data == "success"){
					$("#mcAddCancel").click();
					$("#mcSearch").click();
					$("#updateSuccessButton").click();
				}else{
					$("#updateFailedButton").click();
				}
			}); 
		}else{
			url = "/base/modulContent/add";
			$.post(url,param,function(data){
				if(data == "success"){
					$("#mcAddCancel").click();
					$("#mcSearch").click();
					$("#addSuccessButton").click();
				}else{
					$("#addFailedButton").click();
				}
			});
		}
    }
 });

var setCategory = function(categoryId){
	var url = "/base/category/getCategory/"+categoryId;
	$.get(url,function(category){
		if(category.ilevel == 1){
			$("[name='mcFirstCategory']").val(category.iid);
		}
		if(category.ilevel == 2){
			$("[name='mcFirstCategory']").val(category.iparentid);
			$("#mcSecondCategory").val(category.iid);
		}
		if(category.ilevel == 3){
			var url = "/base/category/getCategory/"+category.iparentid;
			$.get(url,function(data){
				$("[name='mcFirstCategory']").val(data.iparentid);
				$("#mcSecondCategory").val(data.iid);
				$("#mcThirdCategory").val(category.iid);
			});
		}
	});
}
var update = function(i){
	validator.resetForm();
	var url = "/base/modulContent/get/"+$("#mcId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='client_id']").val(data.client_id);
		$("[name='language_id']").val(data.language_id);
		$("[name='layout_id']").val(data.layout_id);
		$("[name='layout_module_id']").val(data.layout_module_id);
		$("[name='is_show']").val(data.is_show);
		$("[name='sku']").val(data.sku);
		$("[name='sort']").val(data.sort);
		$("[name='is_enabled']").val(data.is_enabled);
		setCategory(data.category_id);
		$("#mcModulContentButton").click();
	});
}

var getSearchCategory = function(){
	if($("#thirdCategory").val() != null && $("#thirdCategory").val() != ""){
		return $("#thirdCategory").val();
	}else if($("#secondCategory").val() != null && $("#secondCategory").val() != ""){
		return $("#secondCategory").val();
	}else if($("#firstCategory").val() != null && $("#firstCategory").val() != ""){
		return $("#firstCategory").val();
	}else{
		return null;
	}
}

var getFormCategory = function(){
	if($("#mcThirdCategory").val() != null && $("#mcThirdCategory").val() != ""){
		return $("#mcThirdCategory").val();
	}else if($("#mcSecondCategory").val() != null && $("#mcSecondCategory").val() != ""){
		return $("#mcSecondCategory").val();
	}else if($("[name='mcFirstCategory']").val() != null && $("[name='mcFirstCategory']").val() != ""){
		return $("[name='mcFirstCategory']").val();
	}else{
		return null;
	}
}

var dataRenderFunction = function(list){
	var content = "";
	var isShow = "";
	var isEnabled = "";
	var clientName = "";
	var languageName = "";
	var categoryName = "";
	$("#mcTable tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			if(list[i].is_show == 1){
				isShow = "yes";
			}
			if(list[i].is_show == 0){
				isShow = "no";
			}
			if(list[i].is_enabled == 1){
				isEnabled = "enabled";
			}
			if(list[i].is_enabled == 0){
				isEnabled = "disabled";
			}
			clientName = list[i].client == null ? "" : list[i].client.name;
			languageName = list[i].language == null ? "" : list[i].language.name;
			categoryName = list[i].category == null ? "" : list[i].category.cpath;
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input type="checkbox" class="checkboxes" value="1" id="mcCheckbox'+i+'"/> </td>'+
                    '<td id="mcId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+clientName+'</td>'+
                    '<td>'+languageName+'</td>'+
                    '<td>'+list[i].layout_code+'</td>'+
                    '<td>'+list[i].layout_module_code+'</td>'+
                    '<td>'+isShow+'</td>'+
                    '<td>'+categoryName+'</td>'+
                    '<td>'+list[i].sku+'</td>'+
                    '<td>'+list[i].sort+'</td>'+
                    '<td>'+isEnabled+'</td>'+
                    '<td>'+list[i].whoCreated+'</td>'+
                    '<td>'+list[i].crateTime+'</td>'+
                    '<td>'+list[i].whoModified+'</td>'+
                    '<td>'+list[i].updateTime+'</td>'+
                '</tr>';
		}
	}
	$("#mcTable tbody").append(content);
}

$(function(){
	paging("/base/modulContent/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/base/modulContent/get";
		var param = {};
		param.pageNo = 1;
		param.client_id = $("#client_id").val();
		param.language_id = $("#language_id").val();
		param.layout_id = $("#layout_id").val();
		param.category_id = getSearchCategory();
		param.layout_module_id = $("#layout_module_id").val();
		param.sku = $("#sku").val();
		param._csrf=$("input[name='_csrf']").val();
		paging(url,param,dataRenderFunction);
	});
	
	$("#mcAddMCButton").click(function(){
		validator.resetForm();
		$("[name='id']").val(null);
		$("#mcModulContentButton").click();
	});
	
	$("#mcMainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#mcDeleteMCButton").click(function(){
		var mcIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#mcCheckbox"+i).prop("checked")){
				mcIds += ","+$("#mcId"+i).html();
			}
		}
		if(mcIds == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var mcIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#mcCheckbox"+i).prop("checked")){
				mcIds += ","+$("#mcId"+i).html();
			}
		}
		mcIds = mcIds.substring(1);
		var url = "/base/modulContent/delete/"+mcIds+"?_csrf="+$("input[name='_csrf']").val();
		$.ajax({
			url : url,
			type : "DELETE",
			success : function(data){
				$("#deleteCancelButton").click();
				if(data == "success"){
					$("#mcSearch").click();
					$("#deleteSuccessButton").click();
				}else{
					$("#deleteFailedButton").click();
				}
			}
		});
	});
	
	$("#firstCategory").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$.get(url,function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#secondCategory").empty();
					$("#thirdCategory").empty();
					$("#secondCategory").append(options);
				}
			});
		}else{
			$("#secondCategory").empty();
			$("#thirdCategory").empty();
		}
	});
	
	$("#secondCategory").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$.get(url,function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#thirdCategory").empty();
					$("#thirdCategory").append(options);
				}
			});
		}else{
			$("#thirdCategory").empty();
		}
	});
	
	$("[name='mcFirstCategory']").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$.get(url,function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#mcSecondCategory").empty();
					$("#mcThirdCategory").empty();
					$("#mcSecondCategory").append(options);
				}
			});
		}else{
			$("#mcSecondCategory").empty();
			$("#mcThirdCategory").empty();
		}
	});
	
	$("#mcSecondCategory").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$.get(url,function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#mcThirdCategory").empty();
					$("#mcThirdCategory").append(options);
				}
			});
		}else{
			$("#mcThirdCategory").empty();
		}
	});
	
	
});

