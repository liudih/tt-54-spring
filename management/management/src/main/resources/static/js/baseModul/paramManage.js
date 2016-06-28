var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="paramId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+list[i].client.name+'</td>'+
                    '<td>'+list[i].language.name+'</td>'+
                    '<td>'+stringShow(list[i].type)+'</td>'+
                    '<td>'+stringShow(list[i].value)+'</td>'+
                    '<td>'+stringShow(list[i].name)+'</td>'+
                    '<td>'+stringShow(list[i].sort)+'</td>'+
                    '<td>'+stringShow(list[i].remark)+'</td>'+
                    '<td>'+stringShow(list[i].whoCreated)+'</td>'+
                    '<td>'+stringShow(list[i].createTime)+'</td>'+
                    '<td>'+stringShow(list[i].whoModified)+'</td>'+
                    '<td>'+stringShow(list[i].updateTime)+'</td>'+
                    '</tr>';
		}
	}
	$("#table_form_id tbody").append(content);
}

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

$(function(){
	paging("/base/param/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/base/param/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue();
		param.type = $("#type_name_id").val();
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
				delIds = $("#paramId"+id).html()+ ","+delIds;
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
				delIds = $("#paramId"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/base/param/delete/"+delIds;
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#deleteSuccessButton").click();
				$("#mcSearch").click();
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});
	
	
	$("#sample_editable_1_new").click(function(){
		validateParam.resetForm();
		$("#param_title_id").html('新增参数管理信息');
		$("input[name='name'").val(null);
		$("input[name='type'").val(null);
		$("input[name='id'").val(null);
    	$("input[name='value'").val(null);	
		$("input[name='sort']").val(null);
		$("textarea[name='remark'").val(null);
		$("#param_edit_data").click();
	});
	
	var validateParam=$("#param_form_id").validate({
		rules:{
			type:{
				required:true,
				wType:true
			},
			value:{
				required:true
			},
			client_id:{
				required:true
			}
		},
		messages:{
			type:{
				required:"参数类型不能为空！"
			},
			value:{
				required:"参数值不能为空！"
			},
			client_id:{
				required:"客户端不能为空！"
			}
		},
		submitHandler:function(){
			var params={};
			params.client_id=$("select[name='client_id'").val();
			params.language_id=$("select[name='language_id'").val();
			params.type=$("input[name='type'").val();
			params.value=$("input[name='value']").val();
			params.name=$("input[name='name'").val();
			params.sort=$("input[name='sort'").val();
			params.remark=$("textarea[name='remark'").val();
			params._csrf=$("input[name='_csrf']").val();
			params.is_deleted=$("input[name='is_deleted']").val();
			params.is_enabled=$("select[name='is_enabled'").val();
			if($("input[name='id']").val() != ""){
				params.id=$("input[name='id']").val();
				var url = "/base/param/update";
				$.post(url,params,function(msg){
					if(msg=="SUCCESS"){
						$("#updateSuccessButton").click();
						$("#addParam_close").click();
						$("#mcSearch").click();
					}else{
						return false;
					}
				});
			}else{
				var url = "/base/param/validateParams";
				$.post(url,params,function(msg){
					if(msg!="SUCCESS"){
						$("#checkMessageButton").click();
						return false;
					}else{
						var url = "/base/param/add";
						$.post(url,params,function(msg){
							if(msg=="SUCCESS"){
	    						$("#addSuccessButton").click();
								$("#addParam_close").click();
								$("#mcSearch").click();
							}else{
								alert("Add Fail ! ");
								return false;
							}
						});
					}
				})
			}
		}
	});	
	
	//布局标识格式
	 jQuery.validator.addMethod("wType", function(value, element) {  
	     var chrnum =/^[A-Z-]+$/;  
	     return this.optional(element) || (chrnum.test(value));  
	 }, "参数类型格式不正确！"); 
	
	$(document).on("click",".up",function(){
		validateParam.resetForm();
		var pid = $(this).attr("data-id");
		var id=$("#paramId"+pid).html();
		var url = "/base/param/getId/"+id;
		$.get(url,function(data){
			$("#param_title_id").html('修改参数管理信息');
			$("select[name='client_id'").val(data.client_id);
			$("select[name='language_id'").val(data.language_id);
			$("input[name='name'").val(data.name);
			$("input[name='type'").val(data.type);
			$("input[name='id'").val(data.id);
			$("input[name='value'").val(data.value);	
			$("input[name='sort']").val(data.sort);
			$("textarea[name='remark'").val(data.remark);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("select[name='is_enabled'").val(data.is_enabled);
			$("#param_edit_data").click();
		});
	});
});