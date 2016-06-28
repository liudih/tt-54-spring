$(function() {
	$("#searchS").click(function() {
						var category_id = getSearchCategory();
						var keyword = $("#keyword").val();
						var param = param || {}
						param.clients = getClientValue();
						param.languages = getLanguageValue();
						param.category_id = category_id;
						param.keyword = keyword;
						param.pageNo = 1;
						param._csrf=$("input[name='_csrf']").val();
						var url = "/homepage/hotsearch/list";
						paging(url, param, mcmRenderFunction);
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
						+ '<td><input type="checkbox" name="checkbox" class="checkboxes" value="'+ i+ '"/>'
						+ '</td>'
						+ '<td><i class="upd  fa fa-edit btn_edit" data-id='+ i + ' ></i></td>'
						+ '<td id="mcId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
						+ '<td>'+ list[i].client.name+ '</td>'
						+ '<td>'+ list[i].language.name+ '</td>'
						+ '<td>'+ (list[i].category ? list[i].category.cpath : '')+ '</td>'
						+ '<td>'+ list[i].keyword+ '</td>'
						+ '<td>'+ list[i].sort+ '</td>'
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
	paging("/homepage/hotsearch/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$(function() {
		var validate = $("#id-form").validate({
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
				var param = param || {};
				param.client_id = $("[name='client_id']").val();
				param.language_id = $("[name='language_id']").val();
				param.sort = $("[name='sort']").val();
				param.category_id = getFormCategory();
				param.is_enabled = $("[name='is_enabled']").val();
				param.keyword = $("[name='keyword']").val();
				param._csrf=$("input[name='_csrf']").val();
				if ($("[name='id']").val() == "") {
					var curl ="/homepage/hotsearch/validate"+"?_csrf="+$("input[name='_csrf']").val(); 
    				var p = p || {};
    				p.client_id = $("[name='client_id']").val();
    				p.language_id = $("[name='language_id']").val();
    				p.keyword = $("[name='keyword']").val();
    				p.id=0;
    				$.post(curl, p, function(data) {
    					if(!data){
    						$("#checkHotkeyMessageButton").click();
    						return false;
    					}else{
    						purl = "/homepage/hotsearch/add";
    						$.post(purl, param, function(data) {
    							if (data) {
    								$("#closeB").click();
    								$("#addSuccessButton").click();
    								$("#searchS").click();
    							}

    						});
    					}
    				});
				} else {
					purl = "/homepage/hotsearch/update";
					param.id = $("[name='id']").val();
					$.post(purl, param, function(data) {
						if (data) {
							$("#closeB").click();
							$("#updateSuccessButton").click();
							$("#searchS").click();
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

		$("#closeB").click(function() {
			validate.resetForm();
		});
	});

	$("#addT").click(function() {
		$("[name='id']").val(null);
		$("[name='keyword']").val(null);
		$("[name='sort']").val(1);
	});

	$("#mcMainCheckbox").click(function() {
		$("input:checkbox").prop("checked", this.checked);
	});

	$(document).on("click", ".upd", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcId" + did).html();
		var url = "/homepage/hotsearch/getById/" + id;
		$.get(url, function(data) {
			$("[name='id']").val(data.id);
			$("[name='client_id']").val(data.client_id);
			$("[name='language_id']").val(data.language_id);
			$("[name='keyword']").val(data.keyword);
			$("[name='sort']").val(data.sort);
			setCategory(data.category_id);
			$("[name='is_enabled']").val(data.is_enabled);
		});
		$("#ta").click();
	});

	$("#delT").click(function() {
		var delIds = "";
		var count = 0;
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() + "," + delIds;
			var status = $("#de" + id).html();
			if (status == "Enabled") {
				count++;
			}
		});
		if (delIds == "") {
			$("#noDeleteButton").click();
			return false;
		} else {
			if (count > 0) {
				$("#delCheckMessageButton").click();
				return false;
			} else {
				$("#mcOperateTipButton").click();
			}
		}
	});

	$("#mcMCDeleteButton").click(function() {
		var delIds = "";
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() + "," + delIds;

		});
		var csrf =$("input[name='_csrf']").val();
		$.ajax({
			type : "DELETE",
			url : "/homepage/hotsearch/delete/"+delIds+"?_csrf="+csrf,
			success : function(data) {
				if (data) {
					$("#mcMCDeleteCancelButton").click();
					$("#deleteSuccessButton").click();
					$("#searchS").click();
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

	var getFormCategory = function() {
		if ($("#mcThirdCategory").val() != null
				&& $("#mcThirdCategory").val() != "") {
			return $("#mcThirdCategory").val();
		} else if ($("#mcSecondCategory").val() != null
				&& $("#mcSecondCategory").val() != "") {
			return $("#mcSecondCategory").val();
		} else if ($("[name='mcFirstCategory']").val() != null
				&& $("[name='mcFirstCategory']").val() != "") {
			return $("[name='mcFirstCategory']").val();
		} else {
			return 0;
		}
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
				var url = "/homepage/hotsearch/getCategory/"+category.iparentid;
				$.get(url,function(data){
					$("[name='mcFirstCategory']").val(data.iparentid);
					$("#mcSecondCategory").val(data.iid);
					$("#mcThirdCategory").val(category.iid);
				});
			}
		});
	}

});