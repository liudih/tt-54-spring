var dataRenderFunction = function(list) {
	var content = "";
	var isEnabled = "";
	var description = "";
	var clientName = "";
	var languageName = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if(list[i].description != null){
				if(list[i].description.length > 30){
					description = list[i].description.substring(0,30);
				}else{
					description = list[i].description;
				}
			}
			if (list[i].is_enabled == 1) {
				isEnabled = "Enabled";
			}
			if(list[i].is_enabled == 0){
				isEnabled = "Disabled";
			}
			clientName = list[i].client == null ? "" : list[i].client.name;
			languageName = list[i].language == null ? "" : list[i].language.name;
			content += '<tr class="odd gradeX">'
					+ '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="bCheckbox'+ i+ '"/> </td>'
					+ '<td id="bId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td>'+ clientName+ '</td>'
					+ '<td>'+ languageName+ '</td>'
					+ '<td>'+ stringShow(list[i].name)+ '</td>'
					+ '<td>'+ stringShow(list[i].code)+ '</td>'
					+ '<td>'+'<img width="100%" src="'+stringShow(list[i].logo_url)+'"></td>'
					+ '<td class="tabl_min_200">'+ stringShow(list[i].url)+ '</td>'
					+ '<td title="'+stringShow(list[i].description)+'">'+ description+ '</td>'
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

var getClientValue = function(type){
	var clientValue = null;
	if(type === "search"){
		clientValue = $("#client_id").val();
	}
	if(type === "form"){
		clientValue = $("[name='client_id_add']").val();
	}
	var clientString = "";
	if(clientValue != null && clientValue != "" && clientValue.length > 0){
		for(var i=0;i<clientValue.length;i++){
			clientString += ","+clientValue[i];
		}
		return clientString.substring(1);
	}
	return clientString;
}

var getLanguageValue = function(type){
	var languageValue = null;
	if(type === "search"){
		languageValue = $("#language_id").val();
	}
	if(type === "form"){
		languageValue = $("[name='language_id_add']").val();
	}
	var languageString = "";
	if(languageValue != null && languageValue != "" && languageValue.length > 0){
		for(var i=0;i<languageValue.length;i++){
			languageString += ","+languageValue[i];
		}
		return languageString.substring(1);
	}
	return languageString;
}

var resetMultipleSelect = function(){
	$("[name='client_id_add']").val(null);
	$($(".filter-option")[2]).html($("#commonAll").html());
	$($("ul.inner")[2]).children().each(function(){
		if($(this).hasClass("selected")){
			$(this).removeClass("selected")
		}
	});
	$("[name='language_add_id']").val(null);
	$($(".filter-option")[3]).html($("#commonAll").html());
	$($("ul.inner")[3]).children().each(function(){
		if($(this).hasClass("selected")){
			$(this).removeClass("selected")
		}
	});
}

var validator = $("#brandForm").validate({  
    rules : {  
    	client_id : "required",
    	language_id : "required",
    	name : {
    		required : true,
    		remote: {
			    url: "/homepage/brand/brandNameUV"+"?_csrf="+$("input[name='_csrf']").val(), 
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
			        brandName : function(){
			        	return $("[name='name']").val();
			        }
			    }
			}
    	},
    	code : "required",
    	logoImg : "required"
    },
    messages : {  
    	client_id : $("#clientCheck").html(),  
    	language_id : $("#languageCheck").html(),
    	name : {
    		required : $("#nameCheck").html(),
			remote : $("#nameRepeatCheck").html()
		},
    	code : $("#codeCheck").html(),
    	logoImg : $("#logoCheck").html()
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
    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
    		url = "/homepage/brand/update";
    		if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						$("#brandForm").ajaxSubmit({
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
	    		$("#brandForm").ajaxSubmit({
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
    		if($("[name='file']").val() != null && $("[name='file']").val() != ""){
    			$("#logoCheck").hide();
    			if(getClientValue("form") == ""){
        			$("#brandClientCheckAdd").show();
        			return;
        		}else{
        			$("#brandClientCheckAdd").hide();
        		}
        		if(getLanguageValue("form") == ""){
        			$("#brandLanguageCheckAdd").show();
        			return;
        		}else{
        			$("#brandLanguageCheckAdd").hide();
        		}
        		var param = {};
				param.logo_url = $("[name='logo_url']").val();
				param.name = $("[name='name']").val();
				param.code = $("[name='code']").val();
				param.url = $("[name='url']").val();
				param.is_enabled = $("[name='is_enabled']").val();
				param.description = $("#description").val();
				param.clients = getClientValue("form");
	    		param.languages = getLanguageValue("form");
	    		url = "/homepage/brand/brandUV"+"?_csrf="+$("input[name='_csrf']").val();
	    		$.post(url, param, function(data){
	    			if(data){
	    				$("#repeatBrandShowButton").click();
	    				return;
	    			}
	    			$("#imgUploadForm").ajaxSubmit({
	    				success : function (data){
	    					if(data.succeed == true){
	    						$("#imgUrl").val(data.path);
	    						url = "/homepage/brand/add"+"?_csrf="+$("input[name='_csrf']").val();
	    			    		$.post(url,param,function (data){
				    				if(data == "success"){
				    					$("#saveCancel").click();
				    					$("#dataSearch").click();
				    					$("#addSuccessButton").click();
				    				}else{
				    					$("#addFailedButton").click();
				    				}
	    			    		});
	    					}else{
	    						$("#imageUploadFailedButton").click();
	    					}
	    				}
	    			});
	    		});
    		}else{
    			$("#logoCheck").show();
    		}
    	}
    }
});

var update = function(i){
	validator.resetForm();
	resetMultipleSelect();
	$($(".show-tick")[2]).hide();
	$($(".show-tick")[3]).hide();
	$("[name='client_add_id']").hide();
	$("[name='client_id']").show();
	$("[name='language_add_id']").hide();
	$("[name='language_id']").show();
	$("#brandClientCheckAdd").hide();
	$("#brandLanguageCheckAdd").hide();
	$("#logoCheck").hide();
	$("[name='file']").val(null);
	var url = "/homepage/brand/get/"+$("#bId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='client_id']").val(data.client_id);
		$("[name='language_id']").val(data.language_id);
		$("[name='name']").val(data.name);
		$("[name='code']").val(data.code);
		$("[name='url']").val(data.url);
		$("[name='description']").val(data.description);
		$("[name='is_enabled']").val(data.is_enabled);
		$("[name='logo_url']").val(data.logo_url);
		$("#bImgSrc").attr("src", data.logo_url);
		$("[name='client_id']").prop("disabled", true);
		$("[name='language_id']").prop("disabled", true);
		$("[name='code']").prop("readonly", true);
		$("#brandShowButton").click();
	});
}

$(function() {
	paging("/homepage/brand/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/homepage/brand/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.clients = getClientValue("search");
		searchParam.languages = getLanguageValue("search");
		searchParam.name = $("#name").val();
		searchParam.code = $("#code").val();
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
	});
	
	$("#addButton").click(function() {
		validator.resetForm();
		resetMultipleSelect();
		$("[name='client_add_id']").show();
		$("[name='client_id']").hide();
		$("[name='language_add_id']").show();
		$("[name='language_id']").hide();
		$($(".show-tick")[2]).show();
		$($(".show-tick")[3]).show();
		$("#logoCheck").hide();
		$("#brandClientCheckAdd").hide();
		$("#brandLanguageCheckAdd").hide();
		$("[name='file']").val(null);
		$("[name='id']").val(null);
		$("#bImgSrc").attr("src", "");
		$("[name='code']").prop("readonly", false);
		$("#brandShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#bCheckbox"+i).prop("checked")){
				Ids += ","+$("#bId"+i).html();
			}
		}
		if(Ids == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#bCheckbox"+i).prop("checked")){
				Ids += ","+$("#bId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/homepage/brand/delete/"+Ids.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
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