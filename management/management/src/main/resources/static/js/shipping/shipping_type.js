var validate = $("#id-form").validate({
    debug: true, 
    focusInvalid: false, 
    onkeyup: false, 
    errorClass:"help-block",
    errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    submitHandler: function(form){
    	if($("[name='sdList[1].display_name']").val()==''||$("[name='sdList[2].display_name']").val()==''||$("[name='sdList[3].display_name']").val()==''){
    		alert('请补全前台展示名称多语言！');
    		return false;
    	}else if($("[name='sdList[4].display_name']").val()==''||$("[name='sdList[5].display_name']").val()==''||$("[name='sdList[6].display_name']").val()==''){
    		alert('请补全前台展示名称多语言！');
    		return false;
    	}else if($("[name='sdList[7].display_name']").val()==''||$("[name='sdList[8].display_name']").val()==''){
    		alert('请补全前台展示名称多语言！');
    		return false;
    	}else if($("[name='sdList[1].description']").val()==''||$("[name='sdList[2].description']").val()==''||$("[name='sdList[3].description']").val()==''){
    		alert('请补全描述多语言！');
    		return false;
    	}else if($("[name='sdList[4].description']").val()==''||$("[name='sdList[5].description']").val()==''||$("[name='sdList[6].description']").val()==''){
    		alert('请补全描述多语言！');
    		return false;
    	}else if($("[name='sdList[7].description']").val()==''||$("[name='sdList[8].description']").val()==''){
    		alert('请补全描述多语言！');
    		return false;
    	}
    	
    	var id = $("input[name='id']").val();
    	var tname = $("input[name='type_name']").val();
    	var curl = "/shipping/type/validate?_csrf="+$("input[name='_csrf']").val();
		var param = param || {};
		param.type_name = tname;
		param.id=id;
    	if(id==0){
 			$.ajax({
 				url : curl,
 				type : "POST",
 				data : $.toJSON(param),
 				dataType : "JSON",
 				contentType :"application/json",
 				success : function(msg){
 					if(msg){
 						$("#checkTypeMessageButton").click();
 						return false;
 					}else{
 						var url = "/shipping/type/add?_csrf="+$("input[name='_csrf']").val();
 				       	 $(form).ajaxSubmit({url:url,dataType:'json',type:'post',success:function(data) {
 				       		if(data.status == 1){
 				       		showInfoMessage("发货代码 "+data.desc+" 无效");
 								return;
 							}
				       		 if(data.status == 0){
				       			 $("#addSuccessButton").click();
				       		 }else{
				       			$("#addFailedButton").click();
				       		 }
 				       	   }
 				       	 });
 					}
 				}
 			});
    	}else{
    		$.ajax({
 				url : curl,
 				type : "POST",
 				data : $.toJSON(param),
 				dataType : "JSON",
 				contentType :"application/json",
 				success : function(msg){
 					if(msg){
 						$("#checkTypeMessageButton").click();
 						return false;
 					}else{
 						var url = "/shipping/type/update?_csrf="+$("input[name='_csrf']").val();
				       	 $(form).ajaxSubmit({url:url,dataType:'json',type:'post',success:function(data) {
				       		if(data.status == 1){
				       			showInfoMessage("发货代码 "+data.desc+" 无效");
								return;
							}
				       		 if(data.status == 0){
				       			$("#updateSuccessButton").click();
				       		 }else{
				       			$("#updateFailedButton").click();
				       		 }
				       	   }
				       	 });
 						}
 					}
    		});
 						
    	}
    },
	rules:{
		type_name:{
	        required:true
	    },
	    shipping_code:{
	        required:true
	    },
	    'sdList[0].display_name':{
	        required:true
	    },
	    'sdList[0].description':{
	        required:true
	    },
	    is_enabled:{
	        required:true
	    },
	    shipping_sequence:{
	        required:true
	    }
	},
	messages:{
		type_name:{
	        required:"必填"
	    },
	    shipping_code:{
	        required:"必填"
	    },
	    'sdList[0].display_name':{
	        required:"必填"
	    },
	    'sdList[0].description':{
	        required:"必填"
	    },
	    is_enabled:{
	        required:"必填"
	    },
	    shipping_sequence:{
	        required:"必填"
	    }
	}
          
});

var toUpdate=function(did){
	var id = $("#mcId" + did).html();
	var url = "/shipping/type/toUpdate/"+id;
	window.location=url;
}

$(function() {
	
	if($("[name='pageType']").val()!="add"){
		var id=$("[name='id']").val();
		var purl = "/shipping/type/getTypeByid/"+id;
		$.get(purl, function(data){
			if(null!=data){
				$("[name='type_name']").val(data.type_name);
				$("[name='shipping_code']").val(data.shipping_code);
				$("[name='is_enabled']").val(data.is_enabled);
				$("[name='shipping_sequence']").val(data.shipping_sequence);
				$("[name='sdList[0].display_name']").val(data.sdList[0].display_name);
				$("[name='sdList[0].description']").val(data.sdList[0].description);
				$("[name='type_name']").prop("readonly", true);
				for(var i = 1; i < data.sdList.length; i++){
					$("[name='sdList["+i+"].display_name']").val(data.sdList[i].display_name);
					$("[name='sdList["+i+"].description']").val(data.sdList[i].description);
				}
			}
		});
	}
	
	$("#searchS").click(function() {
						var type_name = $("#type_name").val();
						var param = param || {}
						param.type_name = type_name;
						param.pageNo = 1;
						param._csrf=$("input[name='_csrf']").val();
						var url = "/shipping/type/list";
						paging(url, param, mcmRenderFunction);
					});
	
	var mcmRenderFunction = function(list) {
		var content = "";
		var isEnabled = "";
		$("#bodyT tbody").empty();
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				if (list[i].is_enabled == 1) {
					isEnabled = "Enabled";
				} else {
					isEnabled = "Disabled";
				}
				content += '<tr class="odd gradeX">'
						+ '<td>'+ '<input type="checkbox" name="checkbox" class="checkboxes" value="'+ i+ '"/>'+ '</td>'
						+ '<td><i class="upd  fa fa-edit btn_edit" onclick="toUpdate('+i+')" ></i></td>'
						+ '<td id="mcId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
						+ '<td>'+ list[i].type_name+ '</td>'
						+ '<td>'+ (list[i].sdList ? list[i].sdList[0].display_name : '')+'</td>'
						+ '<td>'+ (list[i].sdList ? list[i].sdList[0].description : '')+ '</td>'
						+ '<td>'+ stringShow(list[i].shipping_sequence)+ '</td>'
						+ '<td id="de'+i+'">'+ isEnabled+ '</td>'
						+ '<td>'+ list[i].whoCreated+ '</td>'
						+ '<td>'+ list[i].createTime+ '</td>'
						+ '<td>'+ list[i].whoModified+ '</td>'
						+ '<td>'+ list[i].updateTime+ '</td>'
						+ '</tr>';
			}
		}
		$("#bodyT tbody").append(content);
	}
	
	paging("/shipping/type/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	
	$("#addT").click(function() {  
    	$("#id-form").submit();
    });
	
	$("#save_dis").click(function() {  
		$("#dis_static").submit();
    });
	
	$("#mcMainCheckbox").click(function() {
		$("input:checkbox").prop("checked", this.checked);
	});
	
	$("#delT").click(function() {
		var delIds = "";
		var count = 0 ;
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() ? $("#mcId" + id).html()+","+delIds:delIds;
			var status = $("#de"+id).html();
			if(status=="Enabled"){
				count++;
			}
		});
		if (delIds == "") {
			$("#noDeleteButton").click();
			return false;
		} else {
			if(count>0){
				$("#delCheckMessageButton").click();
				return false;
			}else{
				$("#mcOperateTipButton").click();
			}
		}
	});
	
	$("#mcMCDeleteButton").click(function(){
		var delIds = "";
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() ? $("#mcId" + id).html()+","+delIds:delIds;
		});
		var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/shipping/type/delete/"+delIds+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
	});
});