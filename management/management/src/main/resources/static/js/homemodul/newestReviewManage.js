var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="dailydealId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" >'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+list[i].client.name+'</td>'+
                    '<td>'+list[i].language.name+'</td>'+
                    '<td>'+stringShow(list[i].sku)+'</td>'+
                    '<td>'+stringShow(list[i].title)+'</td>'+
                    '<td>'+stringShow(list[i].review_content)+'</td>'+
                    '<td>'+stringShow(list[i].review_by)+'</td>'+
                    '<td>'+stringShow(list[i].country)+'</td>'+
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
var getCountryValue = function(type){
	var countryValue = null;
	countryValue = $("#country_id").val();
	var countryString = "";
	if(countryValue != null && countryValue != "" && countryValue.length > 0){
		for(var i=0;i<countryValue.length;i++){
			countryString += ","+countryValue[i];
		}
		return countryString.substring(1);
	}
	return countryString;
}

$(function(){
	paging("/homepage/newestreview/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$("#mcSearch").click(function(){
		var url = "/homepage/newestreview/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue();
		param.countrys = getCountryValue();
		param.sku = $("#newsReview_sku_id").val();
		param.review_by = $("#newsReview_name_id").val();
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
		var url = "/homepage/newestreview/delete/"+delIds;
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
		$("#newestReview_title_id").html('新增最新评论信息');
		$("input[name='start_date']").val(null);
		$("input[name='sku']").val(null);
		$("input[name='id'").val(null);
		$("select[name='client_id']").val(null);
		$("select[name='language_id']").val(null);
		$("#newestReview_edit_data").click();
	});
	
	 var validateData=$("#newestReview_form_Id").validate({
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
			  review_content : {
				  required : true
			  },
			  review_by : {
				  required : true
			  },
			  country : {
				  required : true
			  },
			  title : {
				  required : true
			  }
			},		
			messages:{
				  client_id : {
					  required : "客户端不能为空！"
				  },
				  language_id : {
					  required : "语言不能为空！"
				  },
				  sku : {
					  required : "Sku不能为空！"
				  },
				  review_content : {
					  required : "评论内容不能为空！"
				  },
				  review_by : {
					  required : "评论人不能为空！"
				  },
				  country : {
					  required : "国家不能为空！"
				  },
				  title : {
					  required : "标题不能为空！"
				  }
			},
			submitHandler:function(){
				var newestReviews={};
				newestReviews.client_id=$("select[name='client_id']").val();
				newestReviews.language_id=$("select[name='language_id']").val();
				newestReviews.sku=$("input[name='sku']").val();
				newestReviews.title=$("input[name='title']").val();
				newestReviews.review_content=$("textarea[name='review_content']").val();
				newestReviews.review_by=$("input[name='review_by']").val();
				newestReviews.country=$("select[name='country']").val();
				newestReviews.is_enabled=$("select[name='is_enabled']").val();
				newestReviews.is_deleted=$("input[name='is_deleted']").val();
				newestReviews._csrf=$("input[name='_csrf']").val();
				var sku=$("input[name='sku']").val();
				var validate_url= "/homepage/newestreview/validatesku/"+sku;
				$.get(validate_url,function(data){
					if(data!=""){
						$("#list_id").val(data.clistingid);
						newestReviews.listing_id=$("#list_id").val();
						if($("input[name='id']").val() != ""){
							newestReviews.id=$("input[name='id']").val();
							var url = "/homepage/newestreview/update";
							$.post(url,newestReviews,function(msg){
								if(msg=="SUCCESS"){
		    						$("#updateSuccessButton").click();
									$("#add_newestReview_close").click();
									$("#mcSearch").click();
								}else{
									return false;
								}
							});
						}else{
							var url = "/homepage/newestreview/add";
							$.post(url,newestReviews,function(msg){
								if(msg=="SUCCESS"){
									$("#addSuccessButton").click();
									$("#add_newestReview_close").click();
									$("#mcSearch").click();
								}else{
									return false;
								}
							});
						}
					}else{
						$("#checkIsSkuExit").click();
					}
				});
			}
	    });
	 
	$(document).on("click",".up",function(){
		validateData.resetForm();
		var iid=$(this).attr("data-id");
		var id=$("#dailydealId"+iid).html();
		var url = "/homepage/newestreview/getId/"+id;
		$.get(url,function(data){
			$("#newestReview_title_id").html('修改最新评论信息');
			$("select[name='client_id']").val(data.client_id);
			$("select[name='language_id']").val(data.language_id);
			$("input[name='sku']").val(data.sku);
			$("textarea[name='review_content']").val(data.review_content);
			$("input[name='review_by']").val(data.review_by);
			$("select[name='country']").val(data.country);
			$("input[name='id'").val(data.id);
			$("input[name='title']").val(data.title);
			$("input[name='is_deleted']").val(data.is_deleted);
			$("select[name='is_enabled']").val(data.is_enabled);
			$("#newestReview_edit_data").click();
		});
	});
	
});