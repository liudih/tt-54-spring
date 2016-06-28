
var dataRenderFunction = function(list) {
	var content = "";
	var status = "";
	$("#dataTable tbody").empty();
	if (list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].status == "1") {
				status = "Enabled";
			}
			if(list[i].status == "2"){
				status = "Disabled";
			}
			content += '<tr class="odd gradeX">';
			if(list[i].userName != "admin"){
				content += '<td>'+ '<input type="checkbox" class="checkboxes" value="1" id="uCheckbox'+ i+ '"/> </td>'
				+ '<td><span onclick="update('+i+')">'
				+ '<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i></span>&nbsp;&nbsp;'
				+ '<span onclick="resetPassword('+i+')"><i class="fa fa-rotate-left btn_edit" data-toggle="modal" data-target="#static"></i>'
				+ '</span></td>';
			}else{
				content += '<td></td><td></td>';
			}
			content += '<td id="uId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
					+ '<td>'+ stringShow(list[i].userName)+ '</td>'
					+ '<td>'+ stringShow(list[i].jobNumber)+ '</td>'
					+ '<td>'+ stringShow(list[i].email)+ '</td>'
					+ '<td>'+ stringShow(list[i].phone)+ '</td>'
					+ '<td>'+ status+ '</td>'
					+ '<td>'+ stringShow(list[i].whoCreated)+ '</td>'
					+ '<td>'+ stringShow(list[i].crateTime)+ '</td>'
					+ '<td>'+ stringShow(list[i].whoModified)+ '</td>'
					+ '<td>'+ stringShow(list[i].updateTime)+ '</td>'
					+ '</tr>';
		}
	}
	$("#dataTable tbody").append(content);
}

var getRoleValue = function(){
	var roles = $(".adminRoleCheckbox");
	var roleIds = "";
	for(var i=0;i<roles.length;i++){
		if($(roles[i]).parent().hasClass("checked")){
			roleIds += ","+$(roles[i]).val();
		}
	}
	if(roleIds != ""){
		roleIds = roleIds.substring(1);
	}
	return roleIds;
}
var getSiteValue = function(){
	var sites = $(".siteCheckbox");
	var siteIds = "";
	for(var i=0;i<sites.length;i++){
		if($(sites[i]).parent().hasClass("checked")){
			siteIds += ","+$(sites[i]).val();
		}
	}
	if(siteIds != ""){
		siteIds = siteIds.substring(1);
	}
	return siteIds;
}

var resetRoleAndSite = function(){
	var roles = $(".adminRoleCheckbox");
	for(var i=0;i<roles.length;i++){
		if($(roles[i]).parent().hasClass("checked")){
			$(roles[i]).parent().removeClass("checked");
		}
	}
	var sites = $(".siteCheckbox");
	for(var i=0;i<sites.length;i++){
		if($(sites[i]).parent().hasClass("checked")){
			$(sites[i]).parent().removeClass("checked");
		}
	}
}

jQuery.validator.addMethod("isMobilePhone", function(value, element) {   
    var tel = /^1[0-9]{10}$/;
    return this.optional(element) || (tel.test(value));
}, $("#phoneFormatCheck").html());

jQuery.validator.addMethod("isSecurityPassword", function(value, element) {   
	var numberRegExp = /[0-9]/;
	var charRegExp = /[a-zA-Z]/;
	var check = value.length >= 6 && numberRegExp.test(value) && charRegExp.test(value);
    return this.optional(element) || (check);
}, $("#passwordSecurityCheck").html());

var validator = $("#userForm").validate({  
    rules : {  
    	userName : {
    		required : true
    	},
    	password : {required : true, isSecurityPassword:true},
    	jobNumber : {
    		required : true, 
    		remote: {
			    url: "/authority/user/jobNumberUV"+"?_csrf="+$("input[name='_csrf']").val(), 
			    type: "post",               
			    dataType: "json",           
			    data: {                  
			        id: function() {
			        	if($("[name='id']").val() == null || $("[name='id']").val() == ""){
			        		return 0;
			        	}else{
			        		return $("[name='id']").val();
			        	}
			        }
			    }
			}
    	},
    	email : {email:true},
    	phone : {isMobilePhone:true}
    },
    messages : {  
    	userName : {
    		required : $("#userNameCheck").html()
		},
		password : {
			required : $("#passwordCheck").html()
		},
		jobNumber : {
			required : $("#jobNumberCheck").html(),
			remote : $("#jobNumberRepeatCheck").html()
		},
		email : {email : $("#emailFormatCheck").html()}
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
    	var url = null;
    	var param = {};
    	param.userName = $("[name='userName']").val();
    	param.jobNumber = $("[name='jobNumber']").val();
    	param.email = $("[name='email']").val();
    	param.phone = $("[name='phone']").val();
    	param.roleIds = getRoleValue();
    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
    		param.id = $("[name='id']").val();
    		url = "/authority/user/update"+"?_csrf="+$("input[name='_csrf']").val();
    		$.post(url,param,function (data){
    				if(data == "success"){
    					var param = {};
    					param.userName = $("[name='userName']").val();
    					param.roleIds = getRoleValue();
    					param.siteIds = getSiteValue();
    					param._csrf=$("input[name='_csrf']").val();
    					$.post("/authority/user/updateRoleAndSite",param,function(data){
    						if(data == "success"){
    							$("#saveCancel").click();
    	    					$("#dataSearch").click();
    	    					$("#updateSuccessButton").click();
    						}
    					});
    				}else{
    					$("#updateFailedButton").click();
    				}
    			}
    		);
    	}else{
    		param.password = $("[name='password']").val();
    		url = "/authority/user/add"+"?_csrf="+$("input[name='_csrf']").val();
    		$.post(url,param,function (data){
    				if(data == "success"){
    					var param = {};
    					param.userName = $("[name='userName']").val();
    					param.roleIds = getRoleValue();
    					param.siteIds = getSiteValue();
    					param._csrf=$("input[name='_csrf']").val();
    					$.post("/authority/user/addRoleAndSite",param,function(data){
    						if(data == "success"){
    							$("#saveCancel").click();
    	    					$("#dataSearch").click();
    	    					$("#addSuccessButton").click();
    						}
    					});
    				}else{
    					$("#addFailedButton").click();
    				}
    			}
    		);
    	}
    }
});

var passwordValidator = $("#resetPasswordForm").validate({
	rules : {
		newPassword : {required : true, isSecurityPassword:true},
		confirmPassword : {required : true, equalTo : "#newPassword"}
		
	},
	messages : {
		newPassword : {
			required : $("#newPasswordCheck").html()
		},
		confirmPassword : {
			required : $("#passwordConfirmCheck").html(),
			equalTo : $("#passwordEqualCheck").html()
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
	submitHandler : function(){
		var url = "/authority/user/resetPassword";
		var param = {};
		param.id = $("[name='pid']").val();
		param.password = $("[name='confirmPassword']").val();
		param._csrf=$("input[name='_csrf']").val();
		$.post(url,param,function(data){
			if(data){
				$("#passwordSaveCancel").click();
				$("#updateSuccessButton").click();
			}else{
				$("#updateFailedButton").click();
			}
		});
	}
});

var update = function(i){
	validator.resetForm();
	resetRoleAndSite();
	$("#passwordForm").hide();
	var url = "/authority/user/get/"+$("#uId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='userName']").val(data.userName);
		$("[name='password']").val(data.password);
		$("[name='jobNumber']").val(data.jobNumber);
		$("[name='email']").val(data.email);
		$("[name='phone']").val(data.phone);
		$.get("/authority/user/getRole/"+data.id,function(roleData){
			if(roleData != null && roleData != "" && roleData.length > 0){
				for(var i=0;i<roleData.length;i++){
					if($("#adminRole"+roleData[i].roleId)){
						$("#adminRole"+roleData[i].roleId).parent().addClass("checked");
					}
				}
			}
			$.get("/authority/user/getSite/"+data.id,function(siteData){
				if(siteData != null && siteData != "" && siteData.length > 0){
					for(var i=0;i<siteData.length;i++){
						if($("#site"+siteData[i].site_id)){
							$("#site"+siteData[i].site_id).parent().addClass("checked");
						}
					}
				}
				$("#userShowButton").click();
			});
		});
	});
}

var resetPassword = function(i){
	passwordValidator.resetForm();
	$("[name='pid']").val($("#uId"+i).html());
	$("#passwordShowButton").click();
}

$(function() {
	paging("/authority/user/get", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/authority/user/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.userName = $("#userName").val();
		searchParam.jobNumber = $("#jobNumber").val();
		searchParam.role = $("#role").val();
		searchParam._csrf=$("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
	});
	
	$("#addButton").click(function(){
		validator.resetForm();
		resetRoleAndSite();
		$("#passwordForm").show();
		$("[name='id']").val(null);
		$("#userShowButton").click();
	});
	
	$("#mainCheckbox").click(function(){
		$(".checkboxes").prop("checked",this.checked);
	});
	
	$("#deleteButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#uCheckbox"+i).prop("checked")){
				Ids += ","+$("#uId"+i).html();
			}
		}
		if(Ids == ""){
			$("#noDeleteButton").click();
		}else{
			$("#deleteOperateButton").click();
		}
	});
	
	$("#deleteConfirmButton").click(function(){
		var Ids = "";
		for(var i=0;i<pageLimit;i++){
			if($("#uCheckbox"+i).prop("checked")){
				Ids += ","+$("#uId"+i).html();
			}
		}
		$("#deleteCancelButton").click();
		var url = "/authority/user/delete/"+Ids.substring(1)+"?_csrf="+$("input[name='_csrf']").val();
		$.get(url,function(data){
			if(data == "success"){
				$("#dataSearch").click();
				$("#deleteSuccessButton").click();
			}else{
				$("#deleteFailedButton").click();
			}
		})
	});
	
	$("#getPasswordButton").click(function(){
		$.get("/authority/user/getRandomPassword",function(data){
			$("[name='password']").val(data);
		});
	});
});