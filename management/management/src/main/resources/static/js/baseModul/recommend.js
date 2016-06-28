var index = 0;
var currentIndex;
var rowNum;
var skulist= new Array();
var isBCAddType = true;
var mcmRenderFunction = function(list) {
		var content = "";
		var isEnabled = "";
		$("#bodyT tbody").empty();
		if (list != null) {
			for (var i = 0; i < list.length; i++) {
				if(list[i].is_enabled == 1){
					isEnabled = "Enabled";
				}else{
					isEnabled = "Disabled";
				}
				content += '<tr class="odd gradeX">'
						+ '<td><input type="checkbox" name="checkbox" class="checkboxes" value="'+ i+ '"/></td>'
						+ '<td><i class="upd  fa fa-edit btn_edit" onclick="toUpdate('+i+')" ></i></td>'
						+ '<td id="LoyId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
						+ '<td>'+ (list[i].client ? list[i].client.name : '')+'</td>'
						+ '<td>'+ (list[i].language ? list[i].language.name : '')+ '</td>'
						+ '<td>'+ (list[i].layout ? list[i].layout.name : '')+ '</td>'
						+ '<td>'+ list[i].name+ '</td>'
						+ '<td>'+ list[i].code+ '</td>'
						+ '<td>'+ (list[i].parameter ? list[i].parameter.name : '')+ '</td>'
						+ '<td>'+ list[i].url+ '</td>'
						+ '<td>'+ list[i].number+ '</td>'
						+ '<td>'+ list[i].sort+ '</td>'
						+ '<td id="de'+i+'">'+ isEnabled+ '</td>'
						+ '<td>'+ list[i].whoCreated+ '</td>'
						+ '<td>'+ list[i].createTime+ '</td>'
						+ '<td>'+ list[i].whoModified+ '</td>'
						+ '<td>'+ list[i].updateTime+ '</td>'
						+ '</tr>';
			}
		}
		$("#bodyT tbody").append(content);
	}
var getClientValue = function(type){
	var clientValue = null;
	if(type === "search"){
		clientValue = $("#clients").val();
	}
	if(type === "form"){
		clientValue = $("#clients").val();
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
		languageValue = $("#languages").val();
	}
	if(type === "form"){
		languageValue = $("#languages").val();
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
var getClient = function(){
	var clientValue = $("#clients2").val();
	var clientString = [];
	if(clientValue != null && clientValue != "" && clientValue.length > 0){
		for(var i=0;i<clientValue.length;i++){
			clientString[i]=clientValue[i];
		}
	}
	return clientString;
}
var getLanguage = function(){
	var langValue = $("#languages2").val();
	var langString = [];
	if(langValue != null && langValue != "" && langValue.length > 0){
		for(var i=0;i<langValue.length;i++){
			langString[i]=langValue[i];
		}
	}
	return langString;
}

var getREList = function(){
	var bcList = [];
	var bctrs = $(".re");
	if(index > 0){
		for(var i=0;i<bctrs.length;i++){
			var status = "";
			if($($(".reable")[i]).html()=="Enabled"){
				status=1;
			}else{
				status = 0;
			}
			bcList[i] = {"sku" : $($(".resku")[i]).html().trim()};
			bcList[i].listingId = $($(".relistingid")[i]).html();
			bcList[i].sort = $($(".resort")[i]).html();
			bcList[i].is_enabled = status;
			bcList[i].id = $($(".reId")[i]).html();
		}
	}
	return bcList;
}

var updateRE = function(index){
	currentIndex = index;
	isBCAddType = false;
	var status = "";
	if($("#st"+index).html()=="Enabled"){
		status=1;
	}else{
		status = 0;
	}
	$("[name='sku']").val($("#sku"+index).html().trim());
	$($("[name='sort']")[1]).val($("#sort"+index).html());
	$($("[name='is_enabled']")[1]).val(status);
	$("[name='listingid']").val($("#listing"+index).html())
	$("[name='sku']").attr("readonly","readonly");
}

jQuery.validator.addMethod("isNumber", function(value, element) {   
	if (value!=null && value!="")
    {
        return !isNaN(value);
    }
    return false;
}, $("#numberCheck").html());

var validate = $("#id-form").validate({
    debug: true, 
    focusInvalid: false, 
    onkeyup: false, 
    errorClass:"help-block",
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    submitHandler: function(form){
		var recommend={};
    	var content = $("#skuBody tbody").html();
    	recommend.name = $("[name='name']").val();
    	recommend.code = $("[name='code']").val();
    	recommend.position_id = $("[name='position_id']").val();
    	recommend.url = $("[name='url']").val();
    	recommend.number = $("[name='number']").val();
    	recommend.sort = $($("input[name='sort']")[0]).val();
    	recommend.is_enabled = $($("select[name='is_enabled'")[0]).val();
    	recommend._csrf=$("input[name='_csrf']").val();
    	recommend.layout_id=$("[name='layout_id']").val();
    	recommend.layout_code=$("#layout_id2 option[value='"+$("[name='layout_id']").val()+"']").text();
    	recommend.skuContentValues=getREList();
    	if($("[name='id']").val()==0){
    		recommend.clients=getClient();
    		recommend.languages=getLanguage();
    		if(recommend.clients==''){
    			alert('请选择客户端！');
    			return false;
    		}
    		if(recommend.languages==''){
    			alert('请选择语言！');
    			return false;
    		}
    		var curl = "/base/layoutModule/validateName?_csrf="+$("input[name='_csrf']").val();
			var param = param || {};
			param.code = $("[name='code']").val();
			param.clients=getClient();
			param.languages=getLanguage();
			$.ajax({
				url : curl,
				type : "POST",
				data : $.toJSON(param),
				dataType : "JSON",
				contentType :"application/json",
				success : function(msg){
					if(msg){
						$("#checkRecommendMessageButton").click();
						return false;
					}
					var purl = "/base/layoutModule/add?_csrf="+$("input[name='_csrf']").val();
					$.ajax({
						url : purl,
						type : "POST",
						data : $.toJSON(recommend),
						dataType : "JSON",
						contentType :"application/json",
						success : function(msg){
							if(msg){
								$("#addSuccessButton").click();
							}
						}
					});
				}
			});
    	}else{
    		recommend.id=$("[name='id']").val();
    		recommend.client_id=$("#clients2").val();
    		recommend.language_id=$("#languages2").val();
    		var url = "/base/layoutModule/update?_csrf="+$("input[name='_csrf']").val();
			$.ajax(
					{url : url,
						data: $.toJSON(recommend),  
					    dataType: "json",  
					    type: "POST",
					    traditional: true,
					    contentType :"application/json",
					    success : function(msg){
				if(msg){
							$("#updateSuccessButton").click();
						}
			}});
    	}
    },
	rules:{
		client_id:{
	        required:true
	    },
	    language_id:{
	        required:true
	    },
	    layout_id:{
	        required:true
	    },
	    name:{
	        required:true
	    },
	    code:{
	        required:true
	    },
	    sort:{
	    	isNumber:true
	    },
	    number:{
	    	isNumber:true
	    }
	},
	messages:{
		client_id:{
	        required:"必填"
	    },
	    language_id:{
	        required:"必填"
	    },
	    layout_id:{
	        required: "必选"
	    },
	    name:{
	        required:"必填"
	    },
	    code:{
	        required:"必填"
	    }
	}
          
});
var validateSku = $("#sku-form").validate({
    debug: true, 
    focusInvalid: false, 
    onkeyup: false, 
    errorClass:"help-block",
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    submitHandler: function(form){
    	var content = "";
		var sku  = $("[name='sku']").val().trim();
		var url = "/base/layoutModule/validateskuid/"+sku;
		$.get(url,function(data) {
			if (data=="") {
				alert('SKU不存在！')
				return false;
			}else{
				var isEnabled = "";
				if($($("[name='is_enabled']")[1]).val()==1){
					isEnabled = "Enabled";
				}else{
					isEnabled = "Disabled";
				}
				if(isBCAddType){
					var row = {};
					$(form).find("input,select").each(function(){
						if(this.name){
							row[this.name] = $(this).val().trim(); 
						}
					});
					for(var i=0;i<skulist.length;i++){
						if(skulist[i].sku == row.sku){
							alert('sku已存在！');
							return false;
						}
					}
					skulist.push(row);
					content = '<tr id="reTr'+index+'" class="re odd" role="row">'
							+ '<td class="reId hide" id="reId'+index+'"></td>'
							+ '<td class="resku" id="sku'+index+'">'
							+ $("[name='sku']").val()
							+ '</td>'
							+ '<td class="relistingid hide" id="listing'+index+'">'+$("[name='listingid']").val()+'</td>'
							+ '<td class="resort" id="sort'+index+'">'
							+ $($("[name='sort']")[1]).val()
							+ '</td>'
							+ '<td class="reable" id="st'+index+'">'
							+ isEnabled
							+ '</td>'
							+ '<td><i onclick="updateRE('+index+')" data-id="'+index+'" class="upS fa fa-edit btn_edit" data-toggle="modal" data-target="#static01"></i><i data-id="'+index+'" class="delS fa fa-trash btn_sha"></i></td>'
							+ '</tr>';
					$("#skuBody tbody").append(content);
					index++;
					$("#cancle").click();
				}else{
					var isEnabled = "";
					if($($("[name='is_enabled']")[1]).val()==1){
						isEnabled = "Enabled";
					}else{
						isEnabled = "Disabled";
					}
					$("#sku"+currentIndex).html($("[name='sku']").val());
					$("#sort"+currentIndex).html($($("[name='sort']")[1]).val());
					$("#listing"+currentIndex).html($("[name='listingid']").val());
					$("#st"+currentIndex).html(isEnabled);
					$("#cancle").click();
				}
			}
		});
		
    },
	rules:{
		client_id:{
	        required:true
	    },
	    language_id:{
	        required:true
	    },
	    sku:{
	        required:true
	    },
	    sort:{
	    	isNumber:true
	    }
	},
	messages:{
		client_id:{
	        required:"必填"
	    },
	    language_id:{
	        required:"必填"
	    },
	    sku:{
	        required: "必填"
	    },
	    sort:{
	    	required:"必填"
	    }
	}
});

var toUpdate=function(did){
	var id = $("#LoyId" + did).html();
	var url = "/base/layoutModule/toUpdate/"+id;
	window.location=url;
}

$(function() {
	if($("[name='pageType']").val()!="add"){
		$("#clients2").attr("disabled","disabled");
		$("#languages2").attr("disabled","disabled");
		$("#layout_id2").attr("disabled","disabled");
		$("#code").attr("readonly","readonly");
		var id=$("[name='id']").val();
		var purl = "/base/layoutModule/getModuleByid/"+id;
		$.get(purl, function(data){
			$("#clients2").val(data.client_id);
			$("#languages2").val(data.language_id);
			$("#layout_id2").val(data.layout_id);
			$("#name").val(data.name);
			$("#code").val(data.code);
			$("#position_id").val(data.position_id);
			$("#url").val(data.url);
			$("#number").val(data.number);
			$("#sort").val(data.sort);
			$("#is_enabled").val(data.is_enabled);
			
			var param = param || {};
			param.client_id=$("#clients2").val();
			param.language_id=$("#languages2").val();
			param.layout_id=$("#layout_id2").val();
			param.layout_module_id=$("[name='id']").val();
			var lpurl = "/base/layoutModule/getModuleContentByid/"+id;
			$.get(lpurl,param,function(list){
				if(list!=null){
					skulist = list;
					$("#skuBody tbody").empty();
					var content = "";
					var enabledStatus = "";
					for (var i = 0; i < list.length; i++) {
						if(list[i].is_enabled==1){
							enabledStatus = "Enabled";
						}else{
							enabledStatus = "Disabled";
						}
						content += '<tr id="reTr'+i+'" class="re odd" role="row">'+
						'<td class="reId hide" id="reId'+list[i].id+'"></td>'+
	                    '<td class="resku" id="sku'+i+'">'+list[i].sku+'</td>'+
	                    '<td class="relistingid hide" id="listing'+i+'">'+list[i].listing_id+'</td>'+
	                    '<td class="resort" id="sort'+i+'">'+list[i].sort+'</td>'+
	                    '<td class="reable" id="st'+i+'">'+enabledStatus+'</td>'+
	                    '<td><i onclick="updateRE('+i+')" class="toUp fa fa-edit btn_edit" data-toggle="modal" data-target="#static01"></i><i data-id="'+i+'" class="toDel fa fa-trash btn_sha"></i></td>'+
	                    '</tr>';
						index++;
					}
					$("#skuBody tbody").append(content);
				}
			});
			
		},'json');
		
	}
	$("#searchS").click(function() {
							var url = "/base/layoutModule/list";
							var limit = 10;
							var param = param || {};
							param.pageNo = 1;
							param.clients = getClientValue("search");
							param.languages = getLanguageValue("search");
							param.layout_id = $("#layout_id").val();
							param.name = $("#name").val();
							param._csrf=$("input[name='_csrf']").val();
							paging(url, param, mcmRenderFunction);
						});
		
		paging("/base/layoutModule/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
		
		$("#mcMainCheckbox").click(function() {
			$("input:checkbox").prop("checked", this.checked);
		});
		
		$("#delT").click(function() {
			var delIds = "";
			var count = 0 ;
			$('input:checkbox[name=checkbox]:checked').each(function(i) {
				var id = $(this).val();
				delIds = $("#LoyId" + id).html() ? $("#LoyId" + id).html()+","+delIds:delIds;
				var status = $("#de"+id).html();
				if(status=="Enabled"){
					count++;
				}
			});
			if (delIds == "") {
				$("#noDeleteButton").click();
				return false;
			} else {
				if(count>0){
					$("#delCheckMessageButton").click();
					return false;
				}else{
					$("#mcOperateTipButton").click();
				}
			}
		});
		
		$("#mcMCDeleteButton").click(function(){
			var delIds = "";
			$('input:checkbox[name=checkbox]:checked').each(function(i) {
				var id = $(this).val();
				delIds = $("#LoyId" + id).html() ? $("#LoyId" + id).html()+","+delIds:delIds;
			});
			var csrf =$("input[name='_csrf']").val();
				$.ajax({
					   type: "DELETE",
					   url: "/base/layoutModule/delete/"+delIds+"?_csrf="+csrf,
					   success: function(data){
						   if(data){
							   $("#mcMCDeleteCancelButton").click();
							   $("#deleteSuccessButton").click();
							   $("#searchS").click();
						   }
					   }
					});
		});
		
		$("[name='sku']").blur(function(){
			var sku  = $("[name='sku']").val().trim();
				var url = "/base/layoutModule/validateskuid/"+sku;
				$.get(url,function(data) {
					if (data=="") {
						alert('SKU不存在！')
						return false;
					}else{
						$("[name='listingid']").val(data);
					}
				});
		});
	        
		$("#addT").click(function() {  
	        	$("#id-form").submit();
		    });
		$(document).on("click", ".toUp", function() {
			var id = $(this).data("id");
			if(skulist && skulist[id]){
				var sku = skulist[id];
				for(var name in sku){
					$("[name='"+name+"']").val(sku[name]);
				}
			}
		});
		
		$(document).on("click", ".delS,.toDel", function() {
			var rowCount = $(this).data("id");
			$("#reTr"+rowCount).remove();
			skulist.splice(rowCount,1)
		});
		
		$("#addB").click(function(){
			$("[name='sku']").val("");
			$($("[name='sort']")[1]).val("");
			$("#is_enabled2").val(1);
			$("[name='sku']").attr("readonly",false);
			isBCAddType = true;
		});
});