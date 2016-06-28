
var dataRenderFunction = function(list) {
	var content = "";
	var isEnabled = "";
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
			clientName = list[i].client == null ? "" : list[i].client.name;
			languageName = list[i].language == null ? "" : list[i].language.name;
			content += '<tr class="odd gradeX">'
					+ '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="rCheckbox'+ i+ '"/> </td>'
					+ '<td id="rId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td>'+ clientName+ '</td>'
					+ '<td>'+ languageName+ '</td>'
					+ '<td>'+ stringShow(list[i].key)+ '</td>'
					+ '<td>'+ stringShow(list[i].value)+ '</td>'
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

var validator = $("#resourceForm").validate({  
    rules : {  
    	client_id : "required",
    	language_id : "required",
    	key : {
    		required : true/*,
    		remote: {
			    url: "/base/resource/resourceKeyUV"+"?_csrf="+$("input[name='_csrf']").val(), 
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
			        resourceKey : function(){
			        	return $("[name='key']").val();
			        }
			    }
			}*/
    	},
    	value : "required"
    },
    messages : {  
    	client_id : $("#clientCheck").html(),  
    	language_id : $("#languageCheck").html(),
    	key : {
    		required : $("#keyCheck").html()/*,
			remote : $("#keyRepeatCheck").html()*/
		},
    	value : $("#valueCheck").html()
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
    	param.key = $("[name='key']").val();
    	param.value = $("[name='value']").val();
    	param.is_enabled = $("[name='is_enabled']").val();
    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
    		url = "/base/resource/update"+"?_csrf="+$("input[name='_csrf']").val();
    		param.client_id = $("[name='client_id']").val();
    		param.language_id = $("[name='language_id']").val();
    		param.id = $("[name='id']").val();
    		$.post(url,param,function (data){
    				if(data == "success"){
    					$("#saveCancel").click();
    					$("#dataSearch").click();
    					$("#updateSuccessButton").click();
    				}else{
    					$("#updateFailedButton").click();
    				}
    			}
    		);
    	}else{
    		url = "/base/resource/add"+"?_csrf="+$("input[name='_csrf']").val();
    		param.clients = getClientValue("form");
    		param.languages = getLanguageValue("form");
    		if(param.clients == ""){
    			$("#resourceClientCheckAdd").show();
    			return;
    		}else{
    			$("#resourceClientCheckAdd").hide();
    		}
    		if(param.languages == ""){
    			$("#resourceLanguageCheckAdd").show();
    			return;
    		}else{
    			$("#resourceLanguageCheckAdd").hide();
    		}
    		$.post("/base/resource/resourceUV"+"?_csrf="+$("input[name='_csrf']").val(),param,function(data){
    			if(data){
    				$("#repeatKeyButton").click();
    				return;
    			}
    			$.post(url,param,function (data){
    				if(data == "success"){
    					$("#saveCancel").click();
    					$("#dataSearch").click();
    					$("#addSuccessButton").click();
    				}else{
    					$("#addFailedButton").click();
    				}
    			});
    		});
    	}
    }
});

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

var update = function(i){
	validator.resetForm();
	resetMultipleSelect();
	$($(".show-tick")[2]).hide();
	$($(".show-tick")[3]).hide();
	$("[name='client_add_id']").hide();
	$("[name='client_id']").show();
	$("[name='language_add_id']").hide();
	$("[name='language_id']").show();
	$("#resourceClientCheckAdd").hide();
	$("#resourceLanguageCheckAdd").hide();
	var url = "/base/resource/get/"+$("#rId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='client_id']").val(data.client_id);
		$("[name='language_id']").val(data.language_id);
		$("[name='key']").val(data.key);
		$("[name='value']").val(data.value);
		$("[name='is_enabled']").val(data.is_enabled);
		$("[name='client_id']").prop("disabled", true);
		$("[name='language_id']").prop("disabled", true);
		$("[name='key']").prop("readonly", true);
		$("#resourceShowButton").click();
	});
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

$(function() {
	paging("/base/resource/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/base/resource/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.clients = getClientValue("search");
		searchParam.languages = getLanguageValue("search");
		searchParam.key = $("#key").val();
		searchParam.value = $("#value").val();
		searchParam.is_enabled = $("#is_enabled").val();
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
		$("#resourceClientCheckAdd").hide();
		$("#resourceLanguageCheckAdd").hide();
		$($(".show-tick")[2]).show();
		$($(".show-tick")[3]).show();
		$("[name='key']").prop("readonly", false);
		$("[name='id']").val(null);
		$("#resourceShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#rCheckbox"+i).prop("checked")){
				Ids += ","+$("#rId"+i).html();
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
			if($("#rCheckbox"+i).prop("checked")){
				Ids += ","+$("#rId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/base/resource/delete/"+Ids.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(data){
			if(data == "success"){
				$("#dataSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		})
	});
	
	$("#downloadButton").click(function(){
		$("#downloadShowButton").click();
	});
	
	$("#downloadConfirmButton").click(function(){
		$("#downloadCancelButton").click();
		var url = "/base/resource/download?fileType="+$("#fileType").val()+"&clients="+getClientValue("search")+"&languages="+getLanguageValue("search")+"&key="+$("#key").val()+"&value="+$("#value").val()+"&is_enabled="+$("#is_enabled").val();
		window.location=url;
	});
});