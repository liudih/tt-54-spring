var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="dailydealId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td>'+list[i].client.name+'</td>'+
                    '<td>'+list[i].language.name+'</td>'+
                    '<td>'+stringShow(list[i].sku)+'</td>'+
                    '<td>'+stringShow(list[i].startTime)+'</td>'+
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
	paging("/homepage/dailydeal/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/homepage/dailydeal/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue();
		param.sku = $("#daily_deal_sku_id").val();
	    param.startTime=$("#daily_deal_start_date_id").val()	
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
				delIds = $("#dailydealId"+id).html()+ ","+delIds;
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
				delIds = $("#dailydealId"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/homepage/dailydeal/delete/"+delIds;
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
		validateData.resetForm();
		$("#dailyDeal_title_id").html("新增每日促销管理信息");
		$("input[name='startTime_date'").val(null);
		$("#price_id").html("");
		$("#cost_price").html("");
		$("input[name='sku'").val(null);
		$("input[name='id'").val(null);
		$("input[name='startTime_date'").val(null);
		$("select[name='client_id'").val(null);
		$("select[name='language_id'").val(null);
		$("#daily_deal_edit_data").click();
	});
	
	 var validateData=$("#daily_deal_form_Id").validate({
		 rules : {
			 startTime_date : {
				 required : true
			  },
			  sku : {
				  required : true
			  },
			  client_id : {
				  required : true
			  },
			  language_id : {
				  required : true
			  },
			  discount:{
				  required : true
			  },
			  fSalePrice:{
				  required : true
			  }
			},		
			messages:{
				startTime_date : {
					 required : "开始日期不能为空！"
				  },
				  sku : {
					  required : "Sku不能为空！"
				  },
				  client_id : {
					  required : "客户端不能为空"
				  },
				  language_id : {
					  required : "语言不能为空"
				  },
				  discount:{
					  required : "折扣不能为空！"
				  },
				  fSalePrice:{
					  required : "折扣价不能低于成本价！"
				  }
			},
			submitHandler:function(){
				var dailyDeals={};
				dailyDeals._csrf=$("input[name='_csrf']").val();
				dailyDeals.client_id=$("select[name='client_id'").val();
				dailyDeals.language_id=$("select[name='language_id'").val();
				dailyDeals.sku=$("input[name='sku'").val();
				dailyDeals.discount=$("input[name='discount'").val();
				dailyDeals.startTime_date=$("input[name='startTime_date'").val();	
				dailyDeals.is_deleted=$("input[name='is_deleted']").val();
				dailyDeals.listing_id=$("#listting_id").val();
				dailyDeals.is_enabled=$("input[name='is_enabled']").val();
				 var price=$("#price_id").html();
				 var discount=$("input[name='discount']").val();
				 if(price!="" && costprice!=""){
					 var costprice=$("#cost_price").html();
					 if(discount>=0 && discount<=100){
						 var count=(100-discount)*price/100; 
						 if(costprice>count){
							 $("input[name='fSalePrice']").val(count.toFixed(2));
							 $("#validatediscountprice").click();
						 }else{
							 $("input[name='fSalePrice']").val(count.toFixed(2));
							 	dailyDeals.salePrice=$("input[name='fSalePrice']").val();
								var url_validate= "/homepage/dailydeal/validateSkuAndStartDate";
								$.post(url_validate,dailyDeals,function(data){
									if(data=="SUCCESS"){
										var url = "/homepage/dailydeal/add";
										$.post(url,dailyDeals,function(msg){
											$("#addParam_close").click();
											if(msg=="SUCCESS"){
												$("#addSuccessButton").click();
												$("#mcSearch").click();
											}else{
												$("#addFailedButton").click();
											}
										});
									}else{
										$("#validateClientAndSkuAndDate").click();
									}
								});
						 }
					 }else{
						 $("#validatediscountpricearea").click();
					 }
				 }
			}
	    });
	 
	 $("input[name='sku']").blur(function(){
		 	var sku=$(this).val();
		 	var id=$("input[name='id']").val();
			var url = "/homepage/dailydeal/validateSku/"+sku;
			if(id==""){
				$("#price_id").html("");
				$("#cost_price").html("");
				$("#listting_id").html("");
				$.get(url,function(data){
					if(data!=""){
						$("#price_id").html(data.fprice);
						$("#cost_price").html(data.fcostprice);
						$("#listting_id").val(data.clistingid);
					}else{
						$("#price_id").html("");
						$("#cost_price").html("");
						$("#listting_id").html("");
						$("input[name='fSalePrice']").val(null);
						return false;
					}
				});
			}else{
				return false;
			}
		});
	 
	 
	 $("input[name='discount']").blur(function(){
		 var price=$("#price_id").html();
		 var discount=$("input[name='discount']").val();
		 if(price!="" && costprice!=""){
			 var costprice=$("#cost_price").html();
			 if(discount>=0 && discount<=100){
				 var count=(100-discount)*price/100; 
				 if(costprice>count){
					 $("input[name='fSalePrice']").val(count.toFixed(2));
					 $("#validatediscountprice").click();
				 }else{
					 $("input[name='fSalePrice']").val(count.toFixed(2));
					 $("input[name='discount']").next().html("");
				 }
			 }else{
				 $("#validatediscountpricearea").click();
			 }
		 }
	 });
	 
		function timeStamp2String (time){
	        var datetime = new Date();
	         datetime.setTime(time);
	         var year = datetime.getFullYear();
	         var month = datetime.getMonth() + 1;
	         var date = datetime.getDate();
	         return month + "/" + date + "/" + year;
		};
});
function clientAndSkuAndDateClick(){
	setTimeout(function(){$("#clientAndSkuAndDate_id").click();},1500);  
}
function discountPriceClick(){
	setTimeout(function(){$("#discountprice_id").click();},1500);  
}
function discountPriceAreaClick(){
	setTimeout(function(){$("#discountpricearea_id").click();},1500);  
}

