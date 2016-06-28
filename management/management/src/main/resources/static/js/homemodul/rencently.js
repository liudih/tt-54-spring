var indexs=0;
var currentIndex;
var indexc=0;
var currentIndexc;
var isAdd = true;
var isAddc = true;

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

var validate = $("#sku-form").validate({
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
		var surl;
		var param = param || {};
		param.client_id = $("[name='client_idS']").val();
		param.language_id = $("[name='language_idS']").val();
		param.sku = $("[name='sku']").val();
		param.is_enabled = $($("[name='is_enabled']")[0]).val();
		param._csrf=$("input[name='_csrf']").val();
		var sku = $("[name='sku']").val();
		var curl = "/base/layoutModule/validateskuid/"+sku;
		$.get(curl,function(data) {
			if (data=="") {
				alert('SKU无效！')
				return false;
			}else{
				var p = p || {};
				p.client_id = $("[name='client_idS']").val();
				p.language_id = $("[name='language_idS']").val();
				p.sku = sku;
				p.id = $($("[name='id']")[0]).val();
				if(p.id==''){
					p.id = 0 ;
				}
				var purl = "/homepage/recent/validateSku?_csrf="+param._csrf;
				$.post(purl, p, function(data) {
					if(data){
						$("#checkRecentMessageButton").click();
						return false;
					}else{
						if (isAdd) {
							$("[name='listing_id']").val(data);
							param.listing_id = $("[name='listing_id']").val();
							surl = "/homepage/recent/addS";
							$.post(surl, param, function(data) {
								if (data) {
									$("#closeB").click();
									$("#addSuccessButton").click();
									$("#searchS").click();
								}
							});
						} else {
							surl = "/homepage/recent/updateS";
							param.id = $($("[name='id']")[0]).val();
							$.post(surl, param, function(data) {
								if (data) {
									$("#closeB").click();
									$("#updateSuccessButton").click();
									$("#searchS").click();
								}
							});
						}
					}
				});
			}
		});
		
	},

	rules : {
		sku : {
			required : true
		}
	},
	messages : {
		sku : {
			required : "必填"
		}
	}
});

var validate2 = $("#country-form").validate({
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
		param.client_id = $("[name='client_idC']").val();
		param.language_id = $("[name='language_idC']").val();
		param.is_enabled = $($("[name='is_enabled']")[1]).val();
		param.country_id = $("[name='country_id']").val();
		param._csrf=$("input[name='_csrf']").val();
		var country_id = $("[name='country_id']").val();
		var id = $($("[name='id']")[1]).val();
		var p = p || {};
		p.country_id = $("[name='country_id']").val();
		p.language_id  = $("[name='language_idC']").val();
		p.client_id = $("[name='client_idC']").val();
		if(id==''){
			p.id = 0;
		}else{
			p.id = id ;
		}
				$.post("/homepage/recent/validate?_csrf="+param._csrf, p, function(data) {
					if (data) {
						$("#checkRecentCountryMessageButton").click();
						return false;
					}else{
						if (isAddc) {
							purl = "/homepage/recent/addC";
							$.post(purl, param, function(data) {
								if (data) {
									$("#addSuccessButton").click();
									$("#searchS").click();
								}

							});
						} else {
							purl = "/homepage/recent/updateC";
							param.id = $($("[name='id']")[1]).val();
							$.post(purl, param, function(data) {
								if (data) {
									$("#updateSuccessButton").click();
									$("#searchS").click();
								}
							});
						}
					}
				});
	},

	rules : {
		country_id : {
			required : true
		}
	},
	messages : {
		country_id : {
			required : "必选"
		}
	}
	
	
});

	$("#searchS").click(function() {
		var param = param || {}
		param.clients = getClientValue();
		param.languages = getLanguageValue();
		param._csrf=$("input[name='_csrf']").val();
		var url = "/homepage/recent/list";
		$.post(url, param, function(data) {
			var content = "";
			var content2 = "";
			var isEnabled = "";
			$("#sample_editable_1 tbody").empty();
			$("#sample_editable_2 tbody").empty();
			if (data.liOskus.length > 0) {
				for (var i = 0; i < data.liOskus.length; i++) {
					if (data.liOskus[i].is_enabled == 1) {
						isEnabled = "Enabled";
					} else {
						isEnabled = "Disabled";
					}
					content += '<tr class="odd gradeX">'
							+ '<td id="mcSId'+ i+ '" class="msSId hide">'+ data.liOskus[i].id+ '</td>'
							+ '<td class="sku" id="sku'+i+'">'+ data.liOskus[i].sku+ '</td>'
							+ '<td class="sta" id="de'+ i+ '">'+ isEnabled+ '</td>'
							+ '<td class="who1" id="who1'+i+'">'+ data.liOskus[i].whoCreated+ '</td>'
							+ '<td class="when1" id="when1'+i+'">'+ data.liOskus[i].createTime+ '</td>'
							+ '<td class="whom1" id="whom1'+i+'">'+ data.liOskus[i].whoModified+ '</td>'
							+ '<td class="whenm2" id="whenm2'+i+'">'+ data.liOskus[i].updateTime+ '</td>'
							+ '<td><span href="javascript:;" class="updS label label-sm label-success" data-id='
							+ i + ' > 编辑</span>&nbsp;<span href="javascript:;" class="delS label label-sm label-success" data-id='
							+ i + ' > 删除</span></td>' + '</tr>';
					indexs++;
				}
			}
			if (data.liOCountries.length > 0) {
				for (var i = 0; i < data.liOCountries.length; i++) {
					if (data.liOCountries[i].is_enabled == 1) {
						isEnabled = "Enabled";
					} else {
						isEnabled = "Disabled";
					}
					content2 += '<tr class="odd gradeX">'
							+ '<td id="mcCId'+ i+ '" class="mcCId hide">'+ data.liOCountries[i].id+ '</td>'
							+ '<td class="country" id="country'+i+'">'+ (data.liOCountries[i].country ? data.liOCountries[i].country.name : '')+ '</td>'
							+ '<td class="status" id="dec'+ i+ '">'+ isEnabled+ '</td>'
							+ '<td class="who2" id="who2'+i+'">'+ data.liOCountries[i].whoCreated+ '</td>'
							+ '<td class="when2" id="when2'+i+'">'+ data.liOCountries[i].createTime+ '</td>'
							+ '<td class="whom2" id="whom2'+i+'">'+ data.liOCountries[i].whoModified+ '</td>'
							+ '<td class="whenm2" id="whenm2'+i+'">'+ data.liOCountries[i].updateTime+ '</td>'
							+ '<td><span href="javascript:;" class="updC label label-sm label-success" data-id='
							+ i + ' > 编辑</span>&nbsp;<span href="javascript:;" class="delC label label-sm label-success" data-id='
							+ i + ' > 删除</span></td>' + '</tr>';
					indexc++;
				}
			}
			$("#sample_editable_1 tbody").append(content);
			$("#sample_editable_2 tbody").append(content2);
		});
	});
	$("#searchS").click();
	$("#addS").click(function() {
		isAdd=true;
		$($("[name='id']")[0]).val(null);
		$("[name='sku']").val(null);
		$("[name='remark']").val(null);
	});
	
	$("#addC").click(function() {
		isAddc=true;
		validate2.resetForm();
		$($("[name='id']")[1]).val(null);
		$("[name='country_id']").val(null);
	});
	
	$(document).on("click", ".updS", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcSId" + did).html();
		var url = "/homepage/recent/getSById/" + id;
		$.get(url, function(data) {
			$($("[name='id']")[0]).val(data.id);
			$("#client_idS").val(data.client_id);
			$("#language_idS").val(data.language_id);
			$("[name='sku']").val(data.sku);
			$($("[name='is_enabled']")[0]).val(data.is_enabled);
			currentIndex = indexs;
			isAdd = false;
		});
		$("#tt1").click();
	});
	
	$(document).on("click", ".updC", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcCId" + did).html();
		var url = "/homepage/recent/getCById/" + id;
		$.get(url, function(data) {
			$($("[name='id']")[1]).val(data.id);
			$("#client_idC").val(data.client_id);
			$("#language_idC").val(data.language_id);
			$("[name='country_id']").val(data.country_id);
			$($("[name='is_enabled']")[1]).val(data.is_enabled);
			isAddc=false;
			currentIndexc = indexc;
		});
		$("#tt2").click();
	});
	
	$(document).on("click", ".delS", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcSId" + did).html();
		var status = $("#de"+did).html();
		if(status=="Enabled"){
			$("#delCheckMessageButton").click();
			return false;
		}else{
			var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/homepage/recent/delSById/"+id+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
		}
	});
	
	$(document).on("click", ".delC", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcCId" + did).html();
		var status = $("#dec"+did).html();
		if(status=="Enabled"){
			$("#delCheckMessageButton").click();
			return false;
		}else{
			var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/homepage/recent/delCById/"+id+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
		}
	});
	
	$("addBtnC").click(function(){
		$("#country-form").submit();
	});
	
	$("addBtnS").click(function(){
		$("#sku-form").submit();
	})
	
