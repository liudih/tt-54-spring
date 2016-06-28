
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

var validator = $("#bannerContentForm").validate({  
    rules : {  
    	client_id : "required",
    	language_id : "required",
    	layout_code : "required",
    	banner_code : "required",
    	name : "required",
    	title : "required"
    },
    messages : {  
    	client_id : $("#clientCheck").html(),  
    	language_id : $("#languageCheck").html(),
    	layout_code : $("#layoutNameCheck").html(),
    	banner_code : $("#bannerNameCheck").html(),
    	name : $("#bannerContentNameCheck").html(),
    	title : $("#bannerTitleCheck").html()
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
    	$("[name='category_id']").val(getFormCategory());
    	var url = null;
    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
    		url = "/base/bannerContent/update";
    		if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						$("#bannerContentForm").ajaxSubmit({
    			        		url : url,
    			        		success : function (data){
    			    				if(data == "success"){
    			    					$("#saveCancel").click();
    			    					$("#dataSearch").click();
    			    					$("#updateSuccessButton").click();
    			    				}else{
    			    					$("#updateFailedButton").click();
    			    				}
    			    			}
    			    		});
    					}
    				}
    			});
    		}else{
	    		$("#bannerContentForm").ajaxSubmit({
	        		url : url,
	        		success : function (data){
	    				if(data == "success"){
	    					$("#saveCancel").click();
	    					$("#dataSearch").click();
	    					$("#updateSuccessButton").click();
	    				}else{
	    					$("#updateFailedButton").click();
	    				}
	    			}
	    		});
    		}
    	}else{
    		url = "/base/bannerContent/add";
    		if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						$("#bannerContentForm").ajaxSubmit({
    			        		url : url,
    			        		success : function (data){
    			    				if(data == "success"){
    			    					$("#saveCancel").click();
    			    					$("#dataSearch").click();
    			    					$("#addSuccessButton").click();
    			    				}else{
    			    					$("#addFailedButton").click();
    			    				}
    			    			}
    			    		});
    					}
    				}
    			});
    		}else{
    			$("#bannerContentForm").ajaxSubmit({
	        		url : url,
	        		success : function (data){
	    				if(data == "success"){
	    					$("#saveCancel").click();
	    					$("#dataSearch").click();
	    					$("#addSuccessButton").click();
	    				}else{
	    					$("#addFailedButton").click();
	    				}
	    			}
	    		});
    		}
    	}
    }
});

var dataRenderFunction = function(list){
	var content = "";
	var isEnabled = "";
	var clientName = "";
	var languageName = "";
	var categoryName = "";
	$("#dataTable tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
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
                        '<input type="checkbox" class="checkboxes" value="1" id="bcCheckbox'+i+'"/> </td>'+
                    '<td id="bcId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+clientName+'</td>'+
                    '<td>'+languageName+'</td>'+
                    '<td>'+categoryName+'</td>'+
                    '<td>'+list[i].layout_code+'</td>'+
                    '<td>'+list[i].banner_code+'</td>'+
                    '<td>'+list[i].title+'</td>'+
                    '<td>'+list[i].url+'</td>'+
                    '<td>'+'<img width="100%" src="'+list[i].img_url+'"></td>'+
                    '<td>'+list[i].sort+'</td>'+
                    '<td>'+isEnabled+'</td>'+
                    '<td>'+list[i].whoCreated+'</td>'+
                    '<td>'+list[i].crateTime+'</td>'+
                    '<td>'+list[i].whoModified+'</td>'+
                    '<td>'+list[i].updateTime+'</td>'+
                '</tr>';
		}
	}
	$("#dataTable tbody").append(content);
}

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
	$("#bannerImgCheck").hide();
	$("[name='file']").val(null);
	var url = "/base/bannerContent/get/"+$("#bcId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='client_id']").val(data.client_id);
		$("[name='language_id']").val(data.language_id);
		setCategory(data.category_id);
		$("[name='layout_code']").val(data.layout_code);
		$("[name='banner_code']").val(data.banner_code);
		$("[name='name']").val(data.name);
		$("[name='title']").val(data.title);
		$("[name='url']").val(data.url);
		$("[name='sort']").val(data.sort);
		$("[name='is_enabled']").val(data.is_enabled);
		$("#bannerContentShowButton").click();
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

$(function(){
	paging("/base/bannerContent/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function(){
		var url = "/base/bannerContent/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.client_id = $("#client_id").val();
		searchParam.language_id = $("#language_id").val();
		searchParam.layout_code = $("#layout_id").val();
		searchParam.banner_code = $("#bannerName").val();
		searchParam.category_id = getSearchCategory();
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url,searchParam,dataRenderFunction);
	});
	
	$("#addButton").click(function(){
		validator.resetForm();
		$("#bannerImgCheck").hide();
		$("[name='file']").val(null);
		$("[name='id']").val(null);
		$("#bannerContentShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var bcIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#bcCheckbox"+i).prop("checked")){
				bcIds += ","+$("#mcId"+i).html();
			}
		}
		if(bcIds == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var bcIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#bcCheckbox"+i).prop("checked")){
				bcIds += ","+$("#bcId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/base/bannerContent/delete/"+bcIds.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(data){
			if(data == "success"){
				$("#dataSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		})
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