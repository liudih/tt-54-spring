var currentIndex;
var index = 0;
var isBCAddType = true;

var getFormCategory = function(){
	if($("#mcThirdCategory").val() != null && $("#mcThirdCategory").val() != ""){
		return $("#mcThirdCategory").val();
	}else if($("#mcSecondCategory").val() != null && $("#mcSecondCategory").val() != ""){
		return $("#mcSecondCategory").val();
	}else if($("[name='mcFirstCategory']").val() != null && $("[name='mcFirstCategory']").val() != ""){
		return $("[name='mcFirstCategory']").val();
	}else{
		return null;
	}
}

var getFormCategoryShow = function(){
	if($("#mcThirdCategory").val() != null && $("#mcThirdCategory").val() != ""){
		return $("#mcThirdCategory").find("option:selected").text();
	}else if($("#mcSecondCategory").val() != null && $("#mcSecondCategory").val() != ""){
		return $("#mcSecondCategory").find("option:selected").text();
	}else if($("[name='mcFirstCategory']").val() != null && $("[name='mcFirstCategory']").val() != ""){
		return $("[name='mcFirstCategory']").find("option:selected").text();
	}else{
		return "";
	}
}

var setCategory = function(categoryId){
	if(categoryId == "null" || categoryId == "" || categoryId == 0){
		return;
	}
	var url = "/base/category/getCategory/"+categoryId;
	$.get(url,function(category){
		if(category.ilevel == 1){
			$("[name='mcFirstCategory']").val(category.iid);
		}
		if(category.ilevel == 2){
			$("[name='mcFirstCategory']").val(category.iparentid);
			$("[name='mcFirstCategory']").change();
			$("#mcSecondCategory").val(category.iid);
		}
		if(category.ilevel == 3){
			var url = "/base/category/getCategory/"+category.iparentid;
			$.get(url,function(data){
				$("[name='mcFirstCategory']").val(data.iparentid);
				$("[name='mcFirstCategory']").change();
				$("#mcSecondCategory").val(data.iid);
				$("#mcSecondCategory").change();
				$("#mcThirdCategory").val(category.iid);
			});
		}
	});
}

var getBCList = function(){
	var bcList = [];
	var bctrs = $(".bctr");
	if(index > 0){
		for(var i=0;i<bctrs.length;i++){
			bcList[i] = {"category_id" : $($(".bcCategoryValue")[i]).html()};
			bcList[i].name = $($(".bcName")[i]).html();
			bcList[i].title = $($(".bcTitle")[i]).html();
			bcList[i].url = $($(".bcUrl a")[i]).attr("href");
			bcList[i].img_url = $($(".bcImgurlValue")[i]).attr("src");
			bcList[i].sort = $($(".bcSort")[i]).html();
			bcList[i].is_enabled = $($(".bcIsEnabled")[i]).html();
			bcList[i].id = $($(".bcId")[i]).html();
		}
	}
	return bcList;
}

var getClientValue = function(type){
	var clientValue = null;
	clientValue = $("[name='client_id']").val();
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
	languageValue = $("[name='language_id']").val();
	var languageString = "";
	if(languageValue != null && languageValue != "" && languageValue.length > 0){
		for(var i=0;i<languageValue.length;i++){
			languageString += ","+languageValue[i];
		}
		return languageString.substring(1);
	}
	return languageString;
}

var resetBannerForm = function(){
	$("#bannerClientCheck").hide();
	$("#bannerLanguageCheck").hide();
}

var bannerValidateData=$("#bannerForm").validate({
	rules : {
	    code : {
		    required : true
	    },
	    name : {
		    required : true
	    },
	    layout_code : {
		    required : true
	    }
	},		
	messages:{
		 code : {
			 required : $("#bannerNameCheck").html()
		  },
		  name : {
			  required : $("#bannerCodeCheck").html()
		  },
		  layout_code : {
			  required : $("#bannerLayoutCheck").html()
		  }
	},
	errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",
	submitHandler:function(){
		var banners={};
		banners.position_id=$("select[name='position_id']").val();
		banners.layout_code=$("select[name='layout_code']").val();
		banners.code=$("input[name='code']").val();
		banners.name=$($("input[name='name']")[0]).val();	
		banners.is_enabled=$($("select[name='is_enabled']")[0]).val();
		banners.bcList = getBCList();
		if($("input[name='id']").val() != ""){
			if($("select[name='client_id'").val() == null || $("select[name='client_id'").val() == ""){
				$("#bannerClientCheck").show();
				return;
			}else{
				$("#bannerClientCheck").hide();
			}
			if($("select[name='language_id'").val() == null || $("select[name='language_id'").val() == ""){
				$("#bannerLanguageCheck").show();
				return;
			}else{
				$("#bannerLanguageCheck").hide();
			}
			resetBannerForm();
			banners.id=$("input[name='id']").val();
			banners.client_id=$("select[name='client_id']").val();
			banners.language_id=$("select[name='language_id']").val();
			var url = "";
			if($("#typeValue").val() == "update"){
				url = "/base/banner/updateBanner?_csrf="+$("input[name='_csrf']").val();
				$.ajax({
					url : url,
					type : "POST",
					data : banners,
					success : function(msg){
						if(msg){
							$("#updateSuccessButton").click();
						}else{
							$("#updateFailedButton").click();
						}
					}
				});
			}else{
				url = "/base/banner/update?_csrf="+$("input[name='_csrf']").val();
				$.ajax({
					url : url,
					type : "POST",
					data : $.toJSON(banners),
					contentType :"application/json",
					success : function(msg){
						if(msg=="SUCCESS"){
							$("#updateSuccessButton").click();
						}else{
							$("#updateFailedButton").click();
						}
					}
				});
			}
		}else{
			if(getClientValue() == ""){
				$("#bannerClientCheck").show();
				return;
			}else{
				$("#bannerClientCheck").hide();
			}
			if(getLanguageValue() == ""){
				$("#bannerLanguageCheck").show();
				return;
			}else{
				$("#bannerLanguageCheck").hide();
			}
			resetBannerForm();
			var param = {};
			param.code = $("[name='code']").val();
			param.layout_code = $("[name='layout_code']").val();
			param.clients = getClientValue();
			param.languages = getLanguageValue();
			$.post("/base/banner/bannerUV"+"?_csrf="+$("input[name='_csrf']").val(),param,function(data){
				if(data == "false"){
					$("#repeatCodeButton").click();
					return;
				}
				banners.clients=getClientValue();
				banners.languages=getLanguageValue();
				var url = "/base/banner/add?_csrf="+$("input[name='_csrf']").val();
				$.ajax({
					url : url,
					type : "POST",
					data : $.toJSON(banners),
					contentType :"application/json",
					success : function(msg){
						if(msg=="SUCCESS"){
							$("#addSuccessButton").click();
						}else{
							$("#addFailedButton").click();
						}
					}
				});
			});
			}
		}
   });

var updateBC = function(index){
	currentIndex = index;
	isBCAddType = false;
	validator.resetForm();
	$("#bannerImgCheck").hide();
	$("[name='file']").val(null);
	$("#bcId").val($("#bcId"+index).html());
	setCategory($("#bcCategoryValue"+index).html());
	$($("[name='name']")[1]).val($("#bcName"+index).html());
	$($("[name='title']")[0]).val($("#bcTitle"+index).html());
	$($("[name='url']")[0]).val($("#bcUrl"+index+" a").attr("href"));
	$($("[name='sort']")[0]).val($("#bcSort"+index).html());
	$($("[name='is_enabled']")[1]).val($("#bcIsEnabled"+index).html());
	$("#imgUrl").val($("#bcImgurlValue"+index).attr("src"));
	$("#bcImgSrc").attr("src",$("#bcImgurlValue"+index).attr("src"));
	$("#bannerContentShowButton").click();
}

var deleteBC = function(i){
	if($("#typeValue").val() != "update"){
		$("#bcTr"+i).remove();
		index--;
	}else{
		var param = {};
		param.id = $("#bcId"+i).html();
		$.ajax({
			url : "/base/banner/deleteBannerContent/"+$("#bcId"+i).html()+"?_csrf="+$("input[name='_csrf']").val(),
			method : "delete",
			success : function(data){
				if(data){
					refreshBC();
				}
			}
		});
	}
}



var validator = $("#bannerContentForm").validate({  
    rules : {  
    	name : "required",
    	title : "required",
    	url : "url"
    },
    messages : {  
    	name : $("#bannerContentNameCheck").html(),
    	title : $("#bannerTitleCheck").html(),
    	url : $("#bannerUrlCheck").html()
    },
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",
    submitHandler : function(){
    	$("[name='category_id']").val(getFormCategory());
    	if($("#typeValue").val() == "update"){
    		var url = null;
    		var param = {};
    		param.client_id = $("[name='client_id']").val();
    		param.language_id = $("[name='language_id']").val();
    		param.layout_code = $("[name='layout_code']").val();
    		param.banner_code = $("[name='code']").val();
    		param.category_id = $("[name='category_id']").val();
    		param.name = $($("[name='name']")[1]).val();
    		param.title = $("[name='title']").val();
    		param.url = $("[name='url']").val();
    		param.sort = $("[name='sort']").val();
    		param.is_enabled = $($("[name='is_enabled']")[1]).val();
    		if($("[name='file']").val() != ""){
    			$("#imgUploadForm").ajaxSubmit({
    				success : function (data){
    					if(data.succeed == true){
    						$("#imgUrl").val(data.path);
    						param.img_url = $("#imgUrl").val();
    						if(isBCAddType){
    							url = "/base/banner/addBannerContent"+"?_csrf="+$("input[name='_csrf']").val();
    							$.post(url,param,function(data){
    								if(data){
    									refreshBC();
    									$("#imgUrl").val("");
    									$("#bcAddCancel").click();
    								}
    							});
							}else{
								param.id = $("#bcId").val();
								url = "/base/banner/updateBannerContent"+"?_csrf="+$("input[name='_csrf']").val();
    							$.post(url,param,function(data){
    								if(data){
    									refreshBC();
    									$("#imgUrl").val("");
    									$("#bcAddCancel").click();
    								}
    							});
							}
						}else{
							$("#imageUploadFailedButton").click();
						}
    				}
    			});
    		}else{
    			param.img_url = $("#imgUrl").val();
    			if(isBCAddType){
					url = "/base/banner/addBannerContent"+"?_csrf="+$("input[name='_csrf']").val();
					$.post(url,param,function(data){
						if(data){
							refreshBC();
							$("#imgUrl").val("");
							$("#bcAddCancel").click();
						}
					});
				}else{
					param.id = $("#bcId").val();
					url = "/base/banner/updateBannerContent"+"?_csrf="+$("input[name='_csrf']").val();
					$.post(url,param,function(data){
						if(data){
							refreshBC();
							$("#imgUrl").val("");
							$("#bcAddCancel").click();
						}
					});
				}
    		}
    	}else{
			if($("[name='file']").val() != ""){
				$("#imgUploadForm").ajaxSubmit({
					success : function (data){
						if(data.succeed == true){
							$("#imgUrl").val(data.path);
							if(isBCAddType){
								var tr = "<tr class='bctr' id='bcTr"+index+"'>" +
										"<td class='bcId hide' id='bcId"+index+"'></td>" +
										"<td class='bcCategoryValue hide' id='bcCategoryValue"+index+"'>"+getFormCategory()+"</td>" +
										"<td class='bcCategory' id='bcCategory"+index+"'>"+getFormCategoryShow()+"</td>" +
										"<td class='bcName' id='bcName"+index+"'>"+$($("[name='name']")[1]).val()+"</td>" +
										"<td class='bcTitle' id='bcTitle"+index+"'>"+$($("[name='title']")[0]).val()+"</td>" +
										"<td class='bcUrl tabl_min_200' id='bcUrl"+index+"'>"+"<a href='"+$($("[name='url']")[0]).val()+"' target='_blank'>"+$($("[name='url']")[0]).val()+"</a>"+"</td>" +
										"<td class='bcImgurl' id='bcImgurl"+index+"'><img width='80px' height='60px' class='bcImgurlValue' id='bcImgurlValue"+index+"' src='"+$("#imgUrl").val()+"'></td>" +
										"<td class='bcSort' id='bcSort"+index+"'>"+$($("[name='sort']")[0]).val()+"</td>" +
										"<td class='bcIsEnabled hide' id='bcIsEnabled"+index+"'>"+$($("[name='is_enabled']")[1]).val()+"</td>" +
										"<td class='bcIsEnabledShow' id='bcIsEnabledShow"+index+"'>"+$($("[name='is_enabled']")[1]).find("option:selected").text()+"</td>" +
										"<td>" +
											"<i class='fa fa-edit btn_edit' onclick='updateBC("+index+")'></i>" +
											"<i class='fa fa-trash btn_sha' onclick='deleteBC("+index+")'></i>" +
										"</td>" +
										"</tr>";
								$("#dataTable tbody").append(tr);
								index++;
								$("#imgUrl").val("");
								$("#bcAddCancel").click();
							}else{
								$("#bcCategoryValue"+currentIndex).html(getFormCategory());
								$("#bcCategory"+currentIndex).html(getFormCategoryShow());
								$("#bcName"+currentIndex).html($($("[name='name']")[1]).val());
								$("#bcTitle"+currentIndex).html($($("[name='title']")[0]).val());
								$("#bcUrl"+currentIndex).html("<a href='"+$($("[name='url']")[0]).val()+"' target='_blank'>"+$($("[name='url']")[0]).val()+"</a>");
								$("#bcImgurlValue"+currentIndex).attr("src", $("#imgUrl").val());
								$("#bcSort"+currentIndex).html($($("[name='sort']")[0]).val());
								$("#bcIsEnabled"+currentIndex).html($($("[name='is_enabled']")[1]).val());
								$("#bcIsEnabledShow"+currentIndex).html($($("[name='is_enabled']")[1]).find("option:selected").text());
								$("#imgUrl").val("");
								$("#bcAddCancel").click();
							}
						}else{
							$("#imageUploadFailedButton").click();
						}
					}
				});
			}else{
				if(isBCAddType){
					var tr = "<tr class='bctr' id='bcTr"+index+"'>" +
							"<td class='bcId hide' id='bcId"+index+"'></td>" +
							"<td class='bcCategoryValue hide' id='bcCategoryValue"+index+"'>"+getFormCategory()+"</td>" +
							"<td class='bcCategory' id='bcCategory"+index+"'>"+getFormCategoryShow()+"</td>" +
							"<td class='bcName' id='bcName"+index+"'>"+$($("[name='name']")[1]).val()+"</td>" +
							"<td class='bcTitle' id='bcTitle"+index+"'>"+$($("[name='title']")[0]).val()+"</td>" +
							"<td class='bcUrl tabl_min_200' id='bcUrl"+index+"'>"+"<a href='"+$($("[name='url']")[0]).val()+"' target='_blank'>"+$($("[name='url']")[0]).val()+"</a>"+"</td>" +
							"<td class='bcImgurl' id='bcImgurl"+index+"'><img width='80px' height='60px' class='bcImgurlValue' id='bcImgurlValue"+index+"' src='"+$("#imgUrl").val()+"'></td>" +
							"<td class='bcSort' id='bcSort"+index+"'>"+$($("[name='sort']")[0]).val()+"</td>" +
							"<td class='bcIsEnabled hide' id='bcIsEnabled"+index+"'>"+$($("[name='is_enabled']")[1]).val()+"</td>" +
							"<td class='bcIsEnabledShow' id='bcIsEnabledShow"+index+"'>"+$($("[name='is_enabled']")[1]).find("option:selected").text()+"</td>" +
							"<td>" +
								"<i class='fa fa-edit btn_edit' onclick='updateBC("+index+")'></i>" +
								"<i class='fa fa-trash btn_sha' onclick='deleteBC("+index+")'></i>" +
							"</td>" +
							"</tr>";
					$("#dataTable tbody").append(tr);
					index++;
					$("#imgUrl").val("");
					$("#bcAddCancel").click();
				}else{
					$("#bcCategoryValue"+currentIndex).html(getFormCategory());
					$("#bcCategory"+currentIndex).html(getFormCategoryShow());
					$("#bcName"+currentIndex).html($($("[name='name']")[1]).val());
					$("#bcTitle"+currentIndex).html($($("[name='title']")[0]).val());
					$("#bcUrl"+currentIndex).html("<a href='"+$($("[name='url']")[0]).val()+"' target='_blank'>"+$($("[name='url']")[0]).val()+"</a>");
					$("#bcImgurlValue"+currentIndex).attr("src", $("#imgUrl").val());
					$("#bcSort"+currentIndex).html($($("[name='sort']")[0]).val());
					$("#bcIsEnabled"+currentIndex).html($($("[name='is_enabled']")[1]).val());
					$("#bcIsEnabledShow"+currentIndex).html($($("[name='is_enabled']")[1]).find("option:selected").text());
					$("#imgUrl").val("");
					$("#bcAddCancel").click();
				}
			}
		}
    }
});

var dataRenderFunction = function(data){
	index = 0;
	$("#dataTable tbody").empty();
	var tr = "";
	for(var i=0;i<data.length;i++){
		tr += "<tr class='bctr' id='bcTr"+i+"'>" +
				"<td class='bcId hide' id='bcId"+i+"'>"+data[i].id+"</td>" +
				"<td class='bcCategoryValue hide' id='bcCategoryValue"+i+"'>"+data[i].category_id+"</td>" +
				"<td class='bcCategory' id='bcCategory"+i+"'>"+stringShow(data[i].categoryName)+"</td>" +
				"<td class='bcName' id='bcName"+i+"'>"+stringShow(data[i].name)+"</td>" +
				"<td class='bcTitle' id='bcTitle"+i+"'>"+stringShow(data[i].title)+"</td>" +
				"<td class='bcUrl tabl_min_200' id='bcUrl"+i+"'>"+"<a href='"+stringShow(data[i].url)+"' target='_blank'>"+stringShow(data[i].url)+"</a>"+"</td>" +
				"<td class='bcImgurl' id='bcImgurl"+i+"'><img width='80px' height='60px' class='bcImgurlValue' id='bcImgurlValue"+i+"' src='"+stringShow(data[i].img_url)+"'></td>" +
				"<td class='bcSort' id='bcSort"+i+"'>"+stringShow(data[i].sort)+"</td>" +
				"<td class='bcIsEnabled hide' id='bcIsEnabled"+i+"'>"+data[i].is_enabled+"</td>" +
				"<td class='bcIsEnabledShow' id='bcIsEnabledShow"+i+"'>"+stringShow(data[i].isEnabled)+"</td>" +
				"<td>" +
					"<i class='fa fa-edit btn_edit' onclick='updateBC("+i+")'></i>" +
					"<i class='fa fa-trash btn_sha' onclick='deleteBC("+i+")'></i>" +
				"</td>" +
				"</tr>";
		index++;
	}
	$("#dataTable tbody").append(tr);
}

var refreshBC = function(){
	var url = "/base/banner/getBCByBanner";
	var param = {};
	param.pageNo = 1;
	param.client_id = $("[name='client_id']").val();
	param.language_id = $("[name='language_id']").val();
	param.layout_code = $("[name='layout_code']").val();
	param.code = $("[name='code']").val();
	param._csrf=$("input[name='_csrf']").val();
	paging(url,param,dataRenderFunction);
}

$(function(){
	$(".filter-option").html($("#commonAll").html());
	if($("#typeValue").val() == "update"){
		var url = "/base/banner/getId/"+$("#bcIdValue").val();
		$.get(url,function(data){
			$("select[name='client_id']").val(data.client_id);
			$("input[name='code']").val(data.code);
			$("input[name='id']").val(data.id);
			$($("input[name='name']")[0]).val(data.name);
			$("select[name='language_id']").val(data.language_id);
			$("select[name='layout_code']").val(data.layout_code);
			$("select[name='position_id']").val(data.position_id);
			$($("select[name='is_enabled']")[0]).val(data.is_enabled);
			$("input[name='code']").attr("readonly","readonly");
			$("select[name='client_id']").prop("disabled", true);
			$("select[name='language_id']").prop("disabled", true);
			$("select[name='layout_code']").prop("disabled", true);
			url = "/base/banner/getBCByBanner";
			var param = {};
			param.pageNo = 1;
			param.code = data.code;
			param.layout_code = data.layout_code;
			param.client_id = data.client_id;
			param.language_id = data.language_id;
			param._csrf=$("input[name='_csrf']").val();
			paging(url,param,dataRenderFunction);
			/*$.post(url,param,function(data){
				$("#dataTable tbody").empty();
				var tr = "";
				for(var i=0;i<data.length;i++){
					tr += "<tr class='bctr' id='bcTr"+i+"'>" +
							"<td class='bcId hide' id='bcId"+i+"'>"+data[i].id+"</td>" +
							"<td class='bcCategoryValue hide' id='bcCategoryValue"+i+"'>"+data[i].category_id+"</td>" +
							"<td class='bcCategory' id='bcCategory"+i+"'>"+stringShow(data[i].categoryName)+"</td>" +
							"<td class='bcName' id='bcName"+i+"'>"+stringShow(data[i].name)+"</td>" +
							"<td class='bcTitle' id='bcTitle"+i+"'>"+stringShow(data[i].title)+"</td>" +
							"<td class='bcUrl tabl_min_200' id='bcUrl"+i+"'>"+"<a href='"+stringShow(data[i].url)+"' target='_blank'>"+stringShow(data[i].url)+"</a>"+"</td>" +
							"<td class='bcImgurl' id='bcImgurl"+i+"'><img width='80px' height='60px' class='bcImgurlValue' id='bcImgurlValue"+i+"' src='"+stringShow(data[i].img_url)+"'></td>" +
							"<td class='bcSort' id='bcSort"+i+"'>"+stringShow(data[i].sort)+"</td>" +
							"<td class='bcIsEnabled hide' id='bcIsEnabled"+i+"'>"+data[i].is_enabled+"</td>" +
							"<td class='bcIsEnabledShow' id='bcIsEnabledShow"+i+"'>"+stringShow(data[i].isEnabled)+"</td>" +
							"<td>" +
								"<i class='fa fa-edit btn_edit' onclick='updateBC("+i+")'></i>" +
								"<i class='fa fa-trash btn_sha' onclick='deleteBC("+i+")'></i>" +
							"</td>" +
							"</tr>";
					index++;
				}
				$("#dataTable tbody").append(tr);
			});*/
		});
	}
	
	$("#addBannerContent").click(function(){
		isBCAddType = true;
		validator.resetForm();
		$("#bannerImgCheck").hide();
		$("[name='file']").val(null);
		$("#bcImgSrc").attr("src","");
		$("#bannerContentShowButton").click();
	});
	
	$("#bannerSave").click(function(){
		$("#bannerForm").submit();
	});
	
	/*$("[name='layout_code']").change(function(){
		if($(this).val() == "CATEGORY"){
			$(".bcCategory").show();
		}else{
			$(".bcCategory").hide();
		}
	});*/
	
	$("[name='mcFirstCategory']").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$("#mcSecondCategory").empty();
			$("#mcThirdCategory").empty();
			$.ajax({
				url : url,
				type : "GET",
				async : false,
				success : function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#mcSecondCategory").append(options);
				}
			}});
		}else{
			$("#mcSecondCategory").empty();
			$("#mcThirdCategory").empty();
		}
	});
	
	$("#mcSecondCategory").change(function(){
		if($(this).val() != null && $(this).val() != ""){
			var options = "<option></option>";
			var url = "/base/category/getCategoryByPId/"+$(this).val();
			$("#mcThirdCategory").empty();
			$.ajax({
				url : url,
				type : "GET",
				async : false,
				success : function(data){
				if(data != null && data.length > 0){
					for(var i=0;i<data.length;i++){
						options += "<option value='"+data[i].iid+"'>"+data[i].cpath+"</option>";
					}
					$("#mcThirdCategory").append(options);
				}
			}});
		}else{
			$("#mcThirdCategory").empty();
		}
	});
});