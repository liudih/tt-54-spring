var dataRenderFunction = function(list) {
	var content = "";
	var isEnabled = "";
	var videoUrl = "";
	var clientName = "";
	var languageName = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].is_enabled == 1) {
				isEnabled = "Enabled";
			}
			if(list[i].is_enabled == 0){
				isEnabled = "Disabled";
			}
			if(list[i].video_url.length > 30){
				videoUrl = list[i].video_url.substring(0,30);
			}else{
				videoUrl = list[i].video_url;
			}
			clientName = list[i].client == null ? "" : list[i].client.name;
			languageName = list[i].language == null ? "" : list[i].language.name;
			content += '<tr class="odd gradeX">'
					+ '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="nvCheckbox'+ i+ '"/> </td>'
					+ '<td id="nvId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td>'+ clientName+ '</td>'
					+ '<td>'+ languageName+ '</td>'
					+ '<td>'+ stringShow(list[i].title)+ '</td>'
					+ '<td>'+ stringShow(list[i].sku)+ '</td>'
					+ '<td title="'+stringShow(list[i].video_url)+'">'+ videoUrl+ '</td>'
					+ '<td>'+ stringShow(list[i].video_by)+ '</td>'
					+ '<td>'+ stringShow(list[i].country)+ '</td>'
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
var validator = $("#newestVideoForm").validate({  
    rules : {  
    	client_id : "required",
    	language_id : "required",
    	title : "required",
    	sku : {
    		required : true,
    		remote : {
			    url: "/homepage/newestVideo/skuIsAvailable"+"?_csrf="+$("input[name='_csrf']").val(), 
			    type: "post",               
			    dataType: "json"  
    		}
		},
    	video_url : {required : true, url : true},
    	video_by : "required",
    	country : "required"
    },
    messages : {  
    	client_id : $("#clientCheck").html(),  
    	language_id : $("#languageCheck").html(),
    	title : $("#videoTitleCheck").html(),
    	sku : {required : $("#skuCheck").html(), remote : $("#skuIsNotAvailiableCkeck").html()},
    	video_url : {required : $("#urlCheck").html(),url : $("#urlFormatCheck").html()},
    	video_by : $("#createByCheck").html(),
    	country : $("#countryCheck").html()
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
    		url = "/homepage/newestVideo/update";
    		$("#newestVideoForm").ajaxSubmit({
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
    		if(getClientValue("form") == ""){
    			$("#newestVideoClientCheckAdd").show();
    			return;
    		}else{
    			$("#newestVideoClientCheckAdd").hide();
    		}
    		if(getLanguageValue("form") == ""){
    			$("#newestVideoLanguageCheckAdd").show();
    			return;
    		}else{
    			$("#newestVideoLanguageCheckAdd").hide();
    		}
    		var param = {};
    		param.title = $("[name='title']").val();
    		param.sku = $("[name='sku']").val();
    		param.video_url = $("[name='video_url']").val();
    		param.video_by = $("[name='video_by']").val();
    		param.country = $("[name='country']").val();
    		param.is_enabled = $("[name='is_enabled']").val();
    		param.clients = getClientValue("form");
    		param.languages = getLanguageValue("form");
    		url = "/homepage/newestVideo/newestVideoUV"+"?_csrf="+$("input[name='_csrf']").val();
    		$.post(url,param,function(data){
    			if(data){
    				$("#repeatNewestVideoShowButton").click();
    				return;
    			}
    			url = "/homepage/newestVideo/add"+"?_csrf="+$("input[name='_csrf']").val();
        		$.post(url,param,function (data){
        				if(data == "success"){
        					$("#saveCancel").click();
        					$("#dataSearch").click();
        					$("#addSuccessButton").click();
        				}else{
        					$("#addFailedButton").click();
        				}
        			}
        		);
    		});
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
	$("#newestVideoClientCheckAdd").hide();
	$("#newestVideoLanguageCheckAdd").hide();
	var url = "/homepage/newestVideo/get/"+$("#nvId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='client_id']").val(data.client_id);
		$("[name='language_id']").val(data.language_id);
		$("[name='title']").val(data.title);
		$("[name='sku']").val(data.sku);
		$("[name='video_url']").val(data.video_url);
		$("[name='video_by']").val(data.video_by);
		$("[name='country']").val(data.country);
		$("[name='is_enabled']").val(data.is_enabled);
		$("[name='client_id']").prop("disabled", true);
		$("[name='language_id']").prop("disabled", true);
		$("[name='title']").prop("readonly", true);
		$("#newestVideoShowButton").click();
	});
}

$(function() {
	paging("/homepage/newestVideo/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/homepage/newestVideo/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.clients = getClientValue("search");
		searchParam.languages = getLanguageValue("search");
		searchParam.video_by = $("#video_by").val();
		searchParam.country = $("#country").val();
		searchParam.sku = $("#sku").val();
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
		$("#newestVideoClientCheckAdd").hide();
		$("#newestVideoLanguageCheckAdd").hide();
		$("[name='title']").prop("readonly", false);
		$("[name='id']").val(null);
		$("#newestVideoShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#nvCheckbox"+i).prop("checked")){
				Ids += ","+$("#nvId"+i).html();
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
			if($("#nvCheckbox"+i).prop("checked")){
				Ids += ","+$("#nvId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/homepage/newestVideo/delete/"+Ids.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
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