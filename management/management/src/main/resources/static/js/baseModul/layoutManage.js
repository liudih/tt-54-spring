var validateData=$("#layout_form_Id").validate({
		 rules : {
			 code : {
				 required : true,
				 wCode:true
			  },
			  name : {
				  required : true
			  },
			  client_id : {
				  required : true
			  }
			},		
			messages:{
				 code : {
					 required : "布局标识不能为空！"
				  },
				  name : {
					  required : "布局名称不能为空！"
				  },
				  client_id : {
					  required : "客户端不能为空"
				  }
			},
			submitHandler:function(){
				var layouts={};
				layouts.code=$("input[name='code'").val();
				layouts.name=$("input[name='name'").val();	
				layouts.language_id=$("select[name='language_id'").val();
				layouts.url=$("input[name='url']").val();
				layouts.title=$("input[name='title'").val();
				layouts.keyword=$("input[name='keyword'").val();
				layouts.description=$("textarea[name='description'").val();
				layouts.is_deleted=$("input[name='is_deleted']").val();
				layouts.is_enabled=$("select[name='is_enabled'").val();
				layouts._csrf=$("input[name='_csrf']").val();
				if($("input[name='id']").val() != ""){
					if($("select[name='client_id'").val() == "" || $("select[name='client_id'").val() == null){
						$("#bannerClientCheckUpdate").show();
						return;
					}
					$("input[name='name']").next().html("");
					layouts.id=$("input[name='id']").val();
					layouts.client_id=$("select[name='client_id'").val();
					
					var url_validateLayout = "/base/layout/validateLayout";
					$.post(url_validateLayout,layouts,function(msg){
						var url = "/base/layout/update";
						$.post(url,layouts,function(msg){
							if(msg=="SUCCESS"){
								$("#addLayout_close").click();
								$("#updateSuccessButton").click();
								$("#mcSearch").click();
							}else{
								$("#updateFailedButton").click();
							}
						});
					});
				}else{
					if($("select[name='client_id_add'").val() == "" || $("select[name='client_id_add'").val() == null){
						$("#bannerClientCheckAdd").show();
						return;
					}
					layouts.clients=getClientValue("form");
					var url_validateLayout = "/base/layout/validateLayout";
					$.post(url_validateLayout,layouts,function(msg){
						if(msg=="FAIL"){
							$("#checkLayoutNameButton").click();
						}else{
							var url_validateCode = "/base/layout/validatecode";
							$.post(url_validateCode,layouts,function(data){
								if(data=="SUCCESS"){
									var url = "/base/layout/add";
									$.post(url,layouts,function(da){
										if(da=="SUCCESS"){
											$("#addLayout_close").click();
				    						$("#addSuccessButton").click();
											$("#mcSearch").click();
										}else{
											$("#addFailedButton").click();
										}
									});
								}else{
									$("#checkCodeButton").click();
								}
							});
						}
					})
				}				
			}
	    });

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

var resetMultipleSelect = function(){
	$("[name='client_id_add']").val(null);
	$($(".filter-option")[2]).html($("#commonAll").html());
	$($("ul.inner")[2]).children().each(function(){
		if($(this).hasClass("selected")){
			$(this).removeClass("selected")
		}
	});
}
var mcmRenderFunction = function(list){
	var content = "";
	$("#dataTable tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="layoutId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+list[i].client.name+'</td>'+
                    '<td>'+list[i].language.name+'</td>'+
                    '<td>'+stringShow(list[i].name)+'</td>'+
                    '<td>'+stringShow(list[i].code)+'</td>'+
                    '<td>'+stringShow(list[i].url)+'</td>'+
                    '<td>'+stringShow(list[i].title)+'</td>'+
                    '<td>'+stringShow(list[i].keyword)+'</td>'+
                    '<td>'+stringShow(list[i].description)+'</td>'+
                    '<td>'+stringShow(list[i].enabledStatus)+'</td>'+
                    '<td>'+stringShow(list[i].whoCreated)+'</td>'+
                    '<td>'+stringShow(list[i].createTime)+'</td>'+
                    '<td>'+stringShow(list[i].whoModified)+'</td>'+
                    '<td>'+stringShow(list[i].updateTime)+'</td>'+
                '</tr>';
		}
	}
	$("#dataTable tbody").append(content);
}

function closeLayoutBtn(){
	setTimeout(function(){$("#close_layout").click();},1500);  
}
function closeLayoutCodeBtn(){
	setTimeout(function(){$("#close_layoutcode").click();},1500);  
}

$(function(){
	paging("/base/layout/getList",{"pageNo":1,"_csrf":$("input[name='_csrf']").val()},mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/base/layout/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue("search");
		param.name = $("#banner_name_id").val();
		param._csrf=$("input[name='_csrf']").val();
		paging(url,param,mcmRenderFunction);
	});

	$("#mcMainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
		var checkboxs="";
	});
	
	$("#delete_id").click(function(){
		var delIds="";
		$('input:checkbox[name=checkbox]:checked').each(function(i){
				var id =$(this).val();
				delIds = $("#layoutId"+id).html()+ ","+delIds;
		});
		if(delIds == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});

	$("#deleteConfirmButton").click(function(){
		var delIds="";
		$('input:checkbox[name=checkbox]:checked').each(function(i){
				var id =$(this).val();
				delIds = $("#layoutId"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/base/layout/delete/"+delIds;
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#mcSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});
	 
	 
		//验证网址的格式
	 jQuery.validator.addMethod("Wurl", function(value, element) {  
	     var chrnum =/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;  
	     return this.optional(element) || (chrnum.test(value));  
	 }, "URL路由格式不正确！"); 
	 
		//布局标识格式
	 jQuery.validator.addMethod("wCode", function(value, element) {  
	     var chrnum =/^[A-Z-]+$/;  
	     return this.optional(element) || (chrnum.test(value));  
	 }, "布局标识格式不正确！"); 

		
		$("#sample_editable_1_new").click(function(){
			$("#clientUpdate").hide();
			$("#bannerClientCheckAdd").hide();
			resetMultipleSelect();
			$("#clientAdd").show();
			$($(".show-tick")[2]).show();
			validateData.resetForm();
			$("#home_layout_id").html('<i class="fa fa-gift"></i>添加首页布局数据信息');
			$("input[name='code'").removeAttr("disabled","disabled");
			$("select[name='language_id'").removeAttr("disabled","disabled");
			$("select[name='client_id'").removeAttr("disabled","disabled");
			$("input[name='code'").val(null);
			$("input[name='id'").val(null);
			$("input[name='name'").val(null);	
			$("input[name='url']").val(null);
			$("input[name='title'").val(null);
			$("input[name='keyword'").val(null);
			$("textarea[name='description'").val(null);
			$("#layout_data_id").click();
		});

	$(document).on("click",".up",function(){
		validateData.resetForm();
		$("#bannerClientCheckUpdate").hide();
		$("#clientUpdate").show();
		$("#clientAdd").hide();
		$($(".show-tick")[2]).hide();
		var iid=$(this).attr("data-id");
		var id=$("#layoutId"+iid).html();
		var url = "/base/layout/getId/"+id;
		$.get(url,function(data){
			$("#home_layout_id").html('<i class="fa fa-gift"></i>修改首页布局数据信息');
			$("input[name='code'").attr("disabled","disabled");
			$("select[name='language_id'").attr("disabled","disabled");
			$("select[name='client_id'").attr("disabled","disabled");
			$("select[name='client_id'").val(data.client_id);
			$("select[name='language_id'").val(data.language_id);
			$("input[name='code'").val(data.code);
			$("input[name='id'").val(data.id);
			$("input[name='name'").val(data.name);	
			$("input[name='url']").val(data.url);
			$("input[name='title'").val(data.title);
			$("input[name='keyword'").val(data.keyword);
			$("textarea[name='description'").val(data.description);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("select[name='is_enabled'").val(data.is_enabled);
			$("#layout_data_id").click();
		});
	});
});