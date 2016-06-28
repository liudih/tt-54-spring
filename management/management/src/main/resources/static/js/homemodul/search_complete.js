
	
	
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
						+ '<td><input type="checkbox" name="checkbox" class="checkboxes" value="'+ i+ '"/></td>'
						+ '<td><i class="upd  fa fa-edit btn_edit" data-id='+ i + ' ></i></td>'
						+ '<td id="mcId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
						+ '<td>'+ list[i].client.name+ '</td>'
						+ '<td>'+ list[i].language.name+ '</td>'
						+ '<td>'+ list[i].keyword+ '</td>'
						+ '<td id="de'+ i+ '">'+ isEnabled+ '</td>'
						+ '<td>'+ list[i].whoCreated+ '</td>'
						+ '<td>'+ list[i].createTime+ '</td>'
						+ '<td>'+ list[i].whoModified+ '</td>'
						+ '<td>'+ list[i].updateTime+ '</td>'
						+ '</tr>';
			}
		}
		$("#bodyT tbody").append(content);
	}
	var validate = $("#complete-form").validate({
		debug : true,
		focusInvalid : false,
		onkeyup : false,
		errorClass:"help-block",
        errorPlacement: function(error, element) {  
        	$(element.parent().parent()).addClass("has-error");
            error.appendTo(element.parent());  
        },
        success : function(element){
        	$(element.parent().parent()).removeClass("has-error");
        },
		submitHandler : function(form) {
			var purl;
			if ($("[name='id']").val() == "") {
				var curl ="/homepage/searchcomplete/validate"+"?_csrf="+$("input[name='_csrf']").val(); 
				var p = p || {};
				p.client_id = $("[name='client_id']").val();
				p.language_id = $("[name='language_id']").val();
				p.keyword = $("[name='keyword']").val();
				p.id=0;
				$.post(curl, p, function(data) {
					if(data){
						$("#checkHotkeyMessageButton").click();
						return false;
					}else{
				purl = "/homepage/searchcomplete/add?_csrf="+$("input[name='_csrf']").val();
				$("#complete-form").ajaxSubmit({
					url : purl,
					success : function(data){
						if(data == "success"){
							$("#closeSC").click();
							$("#addSuccessButton").click();
							$("#searchS").click();
						}else{
							$("#closeSC").click();
							$("#addFailedButton").click();
						}
					}
				});
			  }
			 });
			} else {
				purl = "/homepage/searchcomplete/update?_csrf="+$("input[name='_csrf']").val();
				$("#complete-form").ajaxSubmit({
					url : purl,
					success : function(data){
						if(data == "success"){
							$("#closeSC").click();
							$("#updateSuccessButton").click();
							$("#searchS").click();
						}else{
							$("#closeSC").click();
							$("#updateFailedButton").click();
						}
					}
				});
			}
		},

		rules : {
			client_id : {
				required : true
			},
			language_id : {
				required : true
			},
			keyword : {
				required : true
			}
		},
		messages : {
			client_id : {
				required : "必选"
			},
			language_id : {
				required : "必选"
			},
			keyword : {
				required : "必填"
			}
		}

	});
	var getClientValue = function(){
		var clientValue = null;
		clientValue = $("#client_id").val();
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
		languageValue = $("#language_id").val();
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
		paging("/homepage/searchcomplete/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
		
	$("#addT").click(function() {
		$("[name='id']").val(null);
		$("[name='keyword']").val(null);
	});

	$("#mcMainCheckbox").click(function() {
		$("input:checkbox").prop("checked", this.checked);
	});

	$(document).on("click", ".upd", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcId" + did).html();
		var url = "/homepage/searchcomplete/getById/" + id;
		$.get(url, function(data) {
			$("[name='id']").val(data.id);
			$("[name='client_id']").val(data.client_id);
			$("[name='language_id']").val(data.language_id);
			$("[name='keyword']").val(data.keyword);
			$("[name='is_enabled']").val(data.is_enabled);
		});
		$("#ta").click();
	});

	$("#delT").click(function() {
		var delIds = "";
		var count = 0 ;
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() + "," + delIds;
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
			delIds = $("#mcId" + id).html() + "," + delIds;
			
		});
		var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/homepage/searchcomplete/delete/"+delIds+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
	});

	$("#tong").click(function(){
		var url = "/homepage/searchcomplete/synchronized";
		$("#t").show();
		$.get(url, function(data) {
			if(data==true){
				$("#importKeyMessageButton").click();
				$("#t").hide();
			}else{
				alert('同步失败！');
			}
		});
	});
	
	$("#upload").click(function(){
		$("#fileForm").ajaxSubmit({
    		url : "/homepage/searchcomplete/upload",
    		success : function (data){
    			if(data){
    				$("#uploadMessageButton").click();
    				$("#closeB").click();
    			}
			}
		});
	});
	
	$("#searchS").click(function() {
				var keyword = $("#keyword").val();
				var param = param || {}
				param.clients = getClientValue();
				param.languages = getLanguageValue();
				param.keyword = keyword;
				param.pageNo = 1;
				param._csrf=$("input[name='_csrf']").val();
				var url = "/homepage/searchcomplete/list";
				paging(url, param, mcmRenderFunction);
			});
});
	
