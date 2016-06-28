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

	$("#searchS").click(function() {
						var img_by = $("#img_by").val();
						var country = $("#country").val();
						var sku = $("#sku").val();
						var param = param || {}
						param.clients = getClientValue();
						param.languages = getLanguageValue();
						param.img_by=img_by;
						param.country=country;
						param.sku = sku;
						param.pageNo = 1;
						param._csrf=$("input[name='_csrf']").val();
						var url = "/homepage/newestimg/list";
						paging(url, param, mcmRenderFunction);
					});
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
						+ '<td>'+ (list[i].client ? list[i].client.name : '')+ '</td>'
						+ '<td>'+ (list[i].language ? list[i].language.name : '')+ '</td>'
						+ '<td>'+ list[i].sku+ '</td>'
						+ '<td>'+ list[i].img_url+ '</td>'
						+ '<td>'+ list[i].img_by+ '</td>'
						+ '<td>'+ (list[i].countryy ? list[i].countryy.name : '')+ '</td>'
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
	
	paging("/homepage/newestimg/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$(function() {
		var validate = $("#img-form").validate({
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
				param.sku = $("[name='sku']").val();
				param.img_url = $("[name='img_url']").val();
				param.img_by=$("[name='img_by']").val();
				param.country = $("[name='country']").val();
				param.is_enabled = $("[name='is_enabled']").val();
				param._csrf=$("input[name='_csrf']").val();
				if ($("[name='id']").val() == "") {
					purl = "/homepage/newestimg/add";
					$.post(purl, param, function(data) {
						if (data) {
							$("#closeB").click();
							$("#addSuccessButton").click();
							$("#searchS").click();
						}

					});
				} else {
					purl = "/homepage/newestimg/update";
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
				sku : {
					required : true
				},
				img_url : {
					required : true
				},
				img_by : {
					required : true
				},
				country : {
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
				sku : {
					required : "必填"
				},
				img_url : {
					required : "必填"
				},
				img_by : {
					required : "必填"
				},
				country : {
					required : "必选"
				}
			}

		});

		$("#closeB").click(function() {
			validate.resetForm();
		});
	});

	$("#addT").click(function() {
		$("[name='id']").val(null);
		$("[name='sku']").val(null);
		$("[name='img_url']").val(null);
		$("[name='img_by']").val(null);
	});

	$("#mcMainCheckbox").click(function() {
		$("input:checkbox").prop("checked", this.checked);
	});

	$(document).on("click", ".upd", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcId" + did).html();
		var url = "/homepage/newestimg/getById/" + id;
		$.get(url, function(data) {
			$("[name='id']").val(data.id);
			$("[name='client_id']").val(data.client_id);
			$("[name='language_id']").val(data.language_id);
			$("[name='sku']").val(data.sku);
			$("[name='img_by']").val(data.img_by);
			$("[name='img_url']").val(data.img_url);
			$("[name='country']").val(data.country);
			$("[name='is_enabled']").val(data.is_enabled);
		});
		$("#tt").click();
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
				   url: "/homepage/newestimg/delete/"+delIds+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
	});

});