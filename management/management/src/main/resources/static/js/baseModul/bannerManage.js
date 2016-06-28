var mcmRenderFunction = function(list){
	var content = "";
	var clientName = "";
	var languageName = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			clientName = list[i].client == null ? "" : list[i].client.name;
			languageName = list[i].language == null ? "" : list[i].language.name;
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="bannerId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+clientName+'</td>'+
                    '<td>'+languageName+'</td>'+
                    '<td>'+stringShow(list[i].layout_code)+'</td>'+
                    '<td>'+stringShow(list[i].name)+'</td>'+
                    '<td>'+stringShow(list[i].code)+'</td>'+
                    '<td>'+stringShow(list[i].parameter.name)+'</td>'+
                    '<td>'+stringShow(list[i].enabledStatus)+'</td>'+
                    '<td>'+stringShow(list[i].whoCreated)+'</td>'+
                    '<td>'+stringShow(list[i].createTime)+'</td>'+
                    '<td>'+stringShow(list[i].whoModified)+'</td>'+
                    '<td>'+stringShow(list[i].updateTime)+'</td>'+
                '</tr>';
		}
	}
	$("#table_form_id tbody").append(content);
}

var validateData=$("#banner_form_Id").validate({
	 rules : {
		 code : {
			 required : true
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
				 required : "广告标识不能为空！"
			  },
			  name : {
				  required : "广告名称不能为空！"
			  },
			  client_id : {
				  required : "客户端不能为空"
			  }
		},
		submitHandler:function(){
			var banners={};
			banners.client_id=$("select[name='client_id'").val();
			banners.position_id=$("select[name='position_id']").val();
			banners.language_id=$("select[name='language_id'").val();
			banners.layout_code=$("select[name='layout_code'").val();
			banners.code=$("input[name='code'").val();
			banners.name=$("input[name='name'").val();	
			banners.is_enabled=$("select[name='is_enabled'").val();
			banners.is_deleted=$("input[name='is_deleted']").val();
			banners._csrf=$("input[name='_csrf']").val();
			if($("input[name='id']").val() != ""){
				banners.id=$("input[name='id']").val();
				var url = "/base/banner/update";
				$.post(url,banners,function(msg){
					if(msg=="SUCCESS"){
						$("#updateSuccessButton").click();
						$("#add_banner_close").click();
						$("#mcSearch").click();
					}else{
						return false;
					}
				});
			}else{
				var url = "/base/banner/add";
				$.post(url,banners,function(msg){
					if(msg=="SUCCESS"){
						$("#addSuccessButton").click();
						$("#add_banner_close").click();
						$("#mcSearch").click();
					}else{
						return false;
					}
				});
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

$(function(){
	paging("/base/banner/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);

	$("#mcSearch").click(function(){
		var url = "/base/banner/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue();
		param.name = $("#banner_name_id").val();
		param.layout_code = $("#layout_code_id").val();
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
				delIds = $("#bannerId"+id).html()+ ","+delIds;
		});
		if(delIds==""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var delIds="";
		$('input:checkbox[name=checkbox]:checked').each(function(i){
				var id =$(this).val();
				delIds = $("#bannerId"+id).html()+ ","+delIds;
		});
		var url = "/base/banner/delete/"+delIds+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#deleteCancelButton").click();
				$("#deleteSuccessButton").click();
				$("#mcSearch").click();
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});
	$("#sample_editable_1_new").click(function(){
		/*validateData.resetForm();
		$("#banner_title_id").html('<i class="fa fa-gift"></i>新增广告组管理信息');
		$("input[name='code'").val(null);
		$("input[name='id'").val(null);
		$("input[name='name'").val(null);	
		$("#banner_edit_data").click();*/
		var url = "/base/banner/toAdd?type=add";
		window.location=url;
	});
	
	$("input[name='code']").mouseleave(function(){
		var name=$(this).val();
		var banners={};
		banners.name=name;
		var url = "/base/banner/validateCode";
		$.post(url,banners,function(msg){
			if(msg=="FAIL"){
				alert("广告标识已经存在！");
				$("input[name='code']").mouseenter();
				return false;
			}else{
				$("input[name='code']").next().html("");
				$("input[name='code']").mouseenter();
			}
		})
	});
	
	$(document).on("click",".up",function(){
		validateData.resetForm();
		var iid=$(this).attr("data-id");
		var id=$("#bannerId"+iid).html();
		/*var url = "/base/banner/getId/"+id;
		$.get(url,function(data){
			$("#banner_title_id").html('<i class="fa fa-gift"></i>修改广告组管理信息');
			$("select[name='client_id'").val(data.client_id);
			$("input[name='code'").val(data.code);
			$("input[name='id'").val(data.id);
			$("input[name='name'").val(data.name);
			$("select[name='language_id'").val(data.language_id);
			$("select[name='layout_code'").val(data.layout_code);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("select[name='is_enabled'").val(data.is_enabled);
			$("#banner_edit_data").click();
		});*/
		var url = "/base/banner/toUpdate/"+id;
		window.location=url;
	});
});