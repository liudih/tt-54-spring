var validateData=$("#shippingtemplate_form_Id").validate({
	 rules : {
		 template_name : {
			 required : true
		  }
		},		
		messages:{
			template_name : {
				 required : "模板名称不能为空！"
			  }
		},
		submitHandler:function(){
			var shippings={};
			shippings.template_name=$("input[name='template_name']").val();
			shippings.is_deleted=$("input[name='is_deleted']").val();
			shippings.is_enabled=$("select[name='is_enabled']").val();
			shippings._csrf=$("input[name='_csrf']").val();
			if($("input[name='id']").val() != null && $("input[name='id']").val() != ""){
				shippings.id=$("input[name='id']").val();
				var url = "/shipping/template/update";
				$.post(url,shippings,function(msg){
					if(msg=="success"){
						$("#add_shippingtemplate_close").click();
						$("#updateSuccessButton").click();
						$("#mcSearch").click();
					}else{
						$("#updateFailedButton").click();
					}
				});
			}else{
				var url_v = "/shipping/template/validateName";
				$.post(url_v,shippings,function(msg){
					if(msg=="fail"){
						$("#checkExistTemplateName").click();
						return false;
					}
					var url = "/shipping/template/add";
					$.post(url,shippings,function(da){
						if(da=="success"){
							$("#add_shippingtemplate_close").click();
							$("#addSuccessButton").click();
							$("#mcSearch").click();
						}else{
							$("#addFailedButton").click();
						}
					});
				})

			}
		}
    });
var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="shipping_Id'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+stringShow(list[i].template_name)+'</td>'+
                    '<td>'+stringShow(list[i].enabledStatus)+'</td>'+
                    '<td>'+stringShow(list[i].whoCreated)+'</td>'+
                    '<td>'+stringShow(list[i].crateTime)+'</td>'+
                    '<td>'+stringShow(list[i].whoModified)+'</td>'+
                    '<td>'+stringShow(list[i].updateTime)+'</td>'+
                '</tr>';
		}
	}
	$("#table_form_id tbody").append(content);
}

$(function(){
	paging("/shipping/template/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/shipping/template/getList";
		var param = {};
		param.pageNo = 1;
		param.template_name = $("#template_name_Id").val();
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
				delIds = $("#shipping_Id"+id).html()+ ","+delIds;
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
				delIds = $("#shipping_Id"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/shipping/template/delete/"+delIds;
		$.get(url,function(data){
			if(data.status == 0){
				$("#deleteSuccessButton").click();
				$("#mcSearch").click();
			}else if(data.status == 1){
				showInfoMessage(data.desc);
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});


	$("#sample_editable_1_new").click(function(){
		validateData.resetForm();
		$("#shippingtemplate_title_id").html('新增物流模板定义');
		$("input[name='id'").val(null);
		$("input[name='template_name'").val(null);
		$("#shippingtemplate_data_id").click();
	});

	$(document).on("click",".up",function(){
		validateData.resetForm();
		var iid=$(this).attr("data-id");
		var id=$("#shipping_Id"+iid).html();
		var url = "/shipping/template/getId/"+id;
		$.get(url,function(data){
			$("#shippingtemplate_title_id").html('修改物流模板定义');
			$("input[name='template_name']").val(data.template_name);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("input[name='id']").val(data.id);
			$("select[name='is_enabled']").val(data.is_enabled);
			$("#shippingtemplate_data_id").click();
		});
		
	});
});

function closeTemplateNameExistBtn(){
	setTimeout(function(){$("#closeExistTemplateNameId").click();},1500);  
}