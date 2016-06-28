var validateData=$("#currency_form_Id").validate({
	 rules : {
		 code : {
			 required : true
		  },
		  name : {
			  required : true
		  },
		  symbol_code : {
			 required : true
		  },
		  current_rate : {
			  required : true,
			  Wname:true
		  }
		},		
		messages:{
			 code : {
				 required : "币种标识不能为空！"
			  },
			  name : {
				  required : "币种名称不能为空！"
			  },
			  symbol_code : {
				 required : "符号不能为空！"
			  },
			  current_rate : {
				  required : "当前汇率不能为空！"
			  }
		},
		submitHandler:function(){
			var currencys={};
			currencys.code=$("input[name='code']").val();
			currencys.sort=$("input[name='sort']").val();
			currencys.name=$("input[name='name']").val();	
			currencys.symbol_code=$("input[name='symbol_code']").val();
			currencys.current_rate=$("input[name='current_rate']").val();
			currencys.new_rate=$("input[name='new_rate']").val();
			currencys.decimal_places=$("input[name='decimal_places']").val();
			currencys.is_deleted=$("input[name='is_deleted']").val();
			currencys.is_enabled=$("select[name='is_enabled']").val();
			currencys.symbol_positions=$("select[name='symbol_positions']").val();
			currencys._csrf=$("input[name='_csrf']").val();

			$("input[name='name']").next().html("");
			if($("input[name='id']").val() != null && $("input[name='id']").val() != ""){
				currencys.id=$("input[name='id']").val();
				var url = "/base/currency/update";
				$.post(url,currencys,function(msg){
					if(msg=="SUCCESS"){
						$("#add_currency_close").click();
						$("#updateSuccessButton").click();
						$("#mcSearch").click();
					}else{
						$("#updateFailedButton").click();
					}
				});
			}else{
				var url_v = "/base/currency/validateName";
				$.post(url_v,currencys,function(msg){
					if(msg=="FAIL"){
						$("#checkMessageButton").click();
						return false;
					}else{
						var url = "/base/currency/add";
						$.post(url,currencys,function(da){
							if(da=="SUCCESS"){
								$("#add_currency_close").click();
								$("#addSuccessButton").click();
								$("#mcSearch").click();
							}else{
								$("#addFailedButton").click();
							}
						});
					}
				})

			}
		}
    });
//数字格式验证  
jQuery.validator.addMethod("Wname", function(value, element) {  
    var chrnum =/(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))/;  
    return this.optional(element) || (chrnum.test(value));  
}, "只能输入数字"); 
var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="currency_id'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td id="currency_name_id'+i+'">'+list[i].name+'</td>'+
                    '<td>'+list[i].code+'</td>'+
                    '<td>'+stringShow(list[i].symbolPositionStr)+'</td>'+
                    '<td>'+stringShow(list[i].symbol_code)+'</td>'+
                    '<td>'+stringShow(list[i].current_rate)+'</td>'+
                    '<td>'+stringShow(list[i].new_rate)+'</td>'+
                    '<td>'+stringShow(list[i].decimal_places)+'</td>'+
                    '<td>'+stringShow(list[i].synchroTime)+'</td>'+
                    '<td>'+stringShow(list[i].enabledStatus)+'</td>'+
                    '<td>'+stringShow(list[i].sort)+'</td>'+
                    '<td>'+stringShow(list[i].whoCreated)+'</td>'+
                    '<td>'+stringShow(list[i].createTime)+'</td>'+
                    '<td>'+stringShow(list[i].whoModified)+'</td>'+
                    '<td>'+stringShow(list[i].updateTime)+'</td>'+
                '</tr>';
		}
	}
	$("#table_form_id tbody").append(content);
}

$(function(){
	paging("/base/currency/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/base/currency/getList";
		var param = {};
		param.pageNo = 1;
		param.name = $("#currency_name").val();
		param.code = $("#currency_code").val();
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
				delIds = $("#currency_id"+id).html()+ ","+delIds;
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
				delIds = $("#currency_id"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/base/currency/delete/"+delIds;
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#deleteSuccessButton").click();
				$("#mcSearch").click();
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});
	
	$("#currency_synchro").click(function(){
		var url = "/base/currency/getSynchroList";
		$("#synchroButtonid").click();
		$.get(url,function(msg){
			$("#close_synchro_id").click();
			if(msg=="SUCCESS"){
				$("#synchroButtonidS").click();
				$("#mcSearch").click();
			}else{
				$("#synchroButtonidF").click();
			}
		});
	});
	
	$("#currency_synchro_id").click(function(){
		var currencys="";
		var ids="";
		$('#currency_name_id').each(function(i){
				var id =$(this).val();
				currencys = $("#currency_name_id"+id).html()+ ","+currencys;
				ids = $("#currency_id"+id).html()+ ","+ids;
		});
		
		var url = "/base/currency/getSynchroList?currencys="+currencys+"&ids="+ids;
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#delMessageButton").click();
				$("#mcSearch").click();
			}else{
				return false;
			}
		});
	});
	


	$("#sample_editable_1_new").click(function(){
		validateData.resetForm();
		$("#currency_title_id").html('新增汇率数据信息');
		$("input[name='id'").val(null);
		$("input[name='code'").val(null);
		$("input[name='name'").val(null);	
		$("input[name='symbol_code'").val(null);
		$("input[name='current_rate']").val(null);
		$("input[name='new_rate'").val(null);
		$("input[name='decimal_places'").val(null);
		$("input[name='sort'").val(null);
		$("#currency_data_id").click();
	});

	$(document).on("click",".up",function(){
		validateData.resetForm();
		var iid=$(this).attr("data-id");
		var id=$("#currency_id"+iid).html();
		var url = "/base/currency/getId/"+id;
		$.get(url,function(data){
			$("#currency_title_id").html('修改汇率数据信息');
			$("input[name='code']").val(data.code);
			$("input[name='sort']").val(data.sort);
			$("input[name='id'").val(data.id);
			$("input[name='name'").val(data.name);	
			$("input[name='symbol_code']").val(data.symbol_code);
			$("input[name='current_rate']").val(data.current_rate);
			$("input[name='new_rate']").val(data.new_rate);
			$("input[name='decimal_places']").val(data.decimal_places);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("select[name='is_enabled']").val(data.is_enabled);
			$("select[name='symbol_positions']").val(data.symbol_positions);
			$("#currency_data_id").click();
		});
		
	});
});