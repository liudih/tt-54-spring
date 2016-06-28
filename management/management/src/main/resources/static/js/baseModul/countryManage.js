
var validator = $("#countryForm").validate({
	rules : {
		language_id : "required",
		name : {
			required : true,
			remote: {
			    url: "/base/country/countryNameUV"+"?_csrf="+$("input[name='_csrf']").val(), 
			    type: "post",               
			    dataType: "json",           
			    data: {                  
			        id: function() {
			        	if($("[name='id']").val() == null || $("[name='id']").val() == ""){
			        		return 0;
			        	}else{
			        		return $("[name='id']").val();
			        	}
			        },
			        countryName : function(){
			        	return $("[name='name']").val();
			        }
			    }
			}
		},
		iso_code_two : "required"
	},
	messages : {
		language_id : $("#languageCheck").html(),
		name : {
			required : $("#nameCheck").html(),
			remote : $("#nameRepeatCheck").html()
		},
		iso_code_two : $("#codeTwoCheck").html()
	},
	errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",
	submitHandler : function() {
		var url = null;
		if ($("[name='id']").val() != null && $("[name='id']").val() != "") {
			url = "/base/country/update";
			if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						$("#countryForm").ajaxSubmit({
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
    					}else{
    						$("#imageUploadFailedButton").click();
    					}
    				}
    			});
    		}else{
	    		$("#countryForm").ajaxSubmit({
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
		} else {
			url = "/base/country/add";
			if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						$("#countryForm").ajaxSubmit({
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
    					}else{
    						$("#imageUploadFailedButton").click();
    					}
    				}
    			});
    		}else{
    			$("#countryForm").ajaxSubmit({
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

var dataRenderFunction = function(list) {
	var content = "";
	var isEnabled = "";
	var isRequiredPostcode = "";
	var language = "";
	var officialLanguage = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].is_enabled == 1) {
				isEnabled = "Enabled";
			}
			if(list[i].is_enabled == 0){
				isEnabled = "Disabled";
			}
			if (list[i].is_required_postcode == 1) {
				isRequiredPostcode = "Yes";
			}
			if (list[i].is_required_postcode == 0) {
				isRequiredPostcode = "No";
			}
			if(list[i].language != null){
				language = list[i].language.name;
			}
			if(list[i].officialLanguage != null){
				officialLanguage = list[i].officialLanguage.name;
			}
			content += '<tr class="odd gradeX">'
					+ '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="cCheckbox'+ i+ '"/> </td>'
					+ '<td id="cId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td>'+ language+ '</td>'
					+ '<td>'+ stringShow(list[i].name)+ '</td>'
					+ '<td>'+ stringShow(list[i].iso_code_two)+ '</td>'
					+ '<td>'+ stringShow(list[i].iso_code_three)+ '</td>'
					+ '<td>'+'<img width="100%" src="'+stringShow(list[i].national_flag_img_url)+'"></td>'
					+ '<td>'+ stringShow(list[i].address_format)+ '</td>'
					+ '<td>'+ stringShow(isRequiredPostcode)+ '</td>'
					+ '<td>'+ stringShow(list[i].currency)+ '</td>'
					+ '<td>'+ stringShow(officialLanguage)+ '</td>'
					+ '<td>'+ stringShow(list[i].length_unit)+ '</td>'
					+ '<td>'+ stringShow(list[i].weigth_unit)+ '</td>'
					+ '<td>'+ stringShow(list[i].sort)+ '</td>'
					+ '<td>'+ isEnabled+ '</td>'
					+ '<td>'+ stringShow(list[i].whoCreated)+ '</td>'
					+ '<td>'+ stringShow(list[i].crateTime)+ '</td>'
					+ '<td>'+ stringShow(list[i].whoModified)+ '</td>'
					+ '<td>'+ stringShow(list[i].updateTime)+ '</td>'
					+ '</tr>';
		}
	}
	$("#dataTable tbody").append(content);
}

var update = function(i){
	validator.resetForm();
	$("#nationalFlagCheck").hide();
	$("[name='file']").val(null);
	var url = "/base/country/get/"+$("#cId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='language_id']").val(data.language_id);
		$("[name='name']").val(data.name);
		$("[name='iso_code_two']").val(data.iso_code_two);
		$("[name='iso_code_three']").val(data.iso_code_three);
		$("[name='address_format']").val(data.address_format);
		$("[name='is_required_postcode']").val(data.is_required_postcode);
		$("[name='currency']").val(data.currency);
		$("[name='official_language_id']").val(data.official_language_id);
		$("[name='length_unit']").val(data.length_unit);
		$("[name='weigth_unit']").val(data.weigth_unit);
		$("[name='sort']").val(data.sort);
		$("[name='is_enabled']").val(data.is_enabled);
		$("#imgUrl").val(data.national_flag_img_url);
		$("#cImgSrc").attr("src",data.national_flag_img_url);
		$("#countryShowButton").click();
	});
}

$(function() {
	paging("/base/country/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/base/country/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.name = $("#countryName").val();
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
	});

	$("#addButton").click(function() {
		validator.resetForm();
		$("#cImgSrc").attr("src", "");
		$("#nationalFlagCheck").hide();
		$("[name='file']").val(null);
		$("[name='id']").val(null);
		$("#countryShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var cIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#cCheckbox"+i).prop("checked")){
				cIds += ","+$("#cId"+i).html();
			}
		}
		if(cIds == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var cIds = "";
		for(var i=0;i<pageLimit;i++){
			if($("#cCheckbox"+i).prop("checked")){
				cIds += ","+$("#cId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/base/country/delete/"+cIds.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(data){
			if(data == "success"){
				$("#dataSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		})
		
	});
});