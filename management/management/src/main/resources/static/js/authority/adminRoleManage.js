var limit = 10;

var dataRenderFunction = function(list) {
	var content = "";
	var status = "";
	var createTm = "";
	var updateTm = "";
	$("#dataTable tbody").empty();
	if (list != null && list.length > 0) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].status == "1") {
				status = "enabled";
			} else {
				status = "disabled";
			}
			content += '<tr class="odd gradeX">'
					+ '<td>'
					+ '<input type="checkbox" class="checkboxes" value="1" id="uCheckbox'
					+ i
					+ '"/> '
					+'</td>'
					+ '<td><span onclick="update('+i+')" style="cursor:pointer;">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'
					+ '<td id="uId'
					+ i
					+ '" >'
					+ list[i].id
					+ '</td>'
					+ '<td>'
					+ list[i].roleName
					+ '</td>'
					+ '<td>'
					+ status
					+ '</td>'
					+ '<td>'
					+ list[i].whoCreated
					+ '</td>'
					+ '<td>'
					+ $.trim(list[i].whenCreated)
					+ '</td>'
					+ '<td>'
					+ list[i].whoModified
					+ '</td>'
					+ '<td>'
					+ $.trim(list[i].whenModified)
					+ '</td>';
		}
	}
	$("#dataTable tbody").append(content);
}

var validator = $("#roleForm").validate({  
    rules : {  
    	roleName : {
    		required : true,
    		remote: {
			    url: "/authority/role/exist"+"?_csrf="+$("input[name='_csrf']").val(), 
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
    	status : "required"
    },
    messages : {  
    	roleName : {
    		required : $("#roleNameCheck").html(),
			remote : $("#roleNameRepeatCheck").html()
		},
		status : $("#statusCheck").html()
    },
    submitHandler : function(){
    	var url = null;
    	if($("[name='id']").val() != null && $("[name='id']").val() != ""){
    		url = "/authority/role/update";
    		$("#roleForm").ajaxSubmit({
        		url : url,
        		success : function (data){
    				if(data == "Y"){
    					$("#saveCancel").click();
    					$("#dataSearch").click();
    					$("#updateSuccessButton").click();
    				}else{
    					$("#updateFailedButton").click();
    				}
    			}
    		});
    	}else{
    		url = "/authority/role/add";
    		$("#roleForm").ajaxSubmit({
        		url : url,
        		success : function (data){
    				if(data == "Y"){
    					$("#saveCancel").click();
    					$("#dataSearch").click();
    					$("#addSuccessButton").click();
    				}else{
    					$("#addFailedButton").click();
    				}
    			}
    		});
    	}
    }
});



var update = function(i){
	validator.resetForm();
	var url = "/authority/role/get/"+$("#uId"+i).html();
	$.get(url,function(data){
		$("[name='id']").val(data.id);
		$("[name='roleName']").val(data.roleName);
		$("[name='status']").val(data.status);
		$("#roleShowButton").click();
	});
}


$(function() {
	paging("/authority/role/get", {
		"pageNo" : 1,
		"pageSize" : limit,
		"_csrf" : $("input[name='_csrf']").val()
	}, dataRenderFunction);
	$("#dataSearch").click(function() {
		var url = "/authority/role/get";
		var searchParam = {};
		searchParam.pageNo = 1;
		searchParam.pageSize = limit;
		searchParam.roleName = $("#roleName").val();
		searchParam.status = $("#status").val();
		searchParam._csrf = $("input[name='_csrf']").val();
		paging(url, searchParam, dataRenderFunction);
	});

	$("#addButton").click(function() {
		validator.resetForm();
		$("[name='id']").val(null);
		$("#roleShowButton").click();
	});

	$("#mainCheckbox").click(function() {
		$(".checkboxes").prop("checked", this.checked);
	});

	$("#deleteButton").click(function() {
		var Ids = "";
		for (var i = 0; i < limit; i++) {
			if ($("#uCheckbox" + i).prop("checked")) {
				Ids += "," + $("#uId" + i).html();
			}
		}
		if (Ids == "") {
			$("#noDeleteButton").click();
		} else {
			$("#deleteOperateButton").click();
		}
	});
	$("#deleteConfirmButton").click(
			function() {
				var Ids = "";
				for (var i = 0; i < limit; i++) {
					if ($("#uCheckbox" + i).prop("checked")) {
						Ids += "," + $("#uId" + i).html();
					}
				}
				$("#deleteCancelButton").click();
				var url = "/authority/role/delete/" + Ids.substring(1)
						+ "?_csrf=" + $("input[name='_csrf']").val();
				$.get(url, function(data) {
					if (data == "Y") {
						$("#dataSearch").click();
						$("#deleteSuccessButton").click();
					} else {
						$("#deleteFailedButton").click();
					}
				})
			});

});