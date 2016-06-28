$(function() {$("#searchS").click(function() {
						var name = $("#name").val();
						var param = param || {}
						param.name = name;
						param.pageNo = 1;
						param._csrf=$("input[name='_csrf']").val();
						var url = "/base/client/list";
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
						+ '<td><input type="checkbox" name="checkbox" class="checkboxes" value="'+ i+ '"/></td>'
						+ '<td><i class="upd  fa fa-edit btn_edit" data-id='+ i + ' ></i></td>'
						+ '<td id="mcId'+ i+ '" class="hide">'+ list[i].id+ '</td>'
						+ '<td>'+ list[i].site.name+ '</td>'
						+ '<td>'+ (list[i].parameter ? list[i].parameter.platform : '')+ '</td>'
						+ '<td>'+ list[i].name+ '</td>'
						+ '<td>'+ (list[i].remark ? list[i].remark : '')+ '</td>'
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
	paging("/base/client/list", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
	$(function(){
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
            	var purl;
    			var param = param || {};
    			param.site_id = $("[name='site_id']").val();
    			param.platform = $("[name='platform']").val();
    			param.name = $("[name='name']").val();
    			param.is_enabled = $("[name='is_enabled']").val();
    			param.remark = $("[name='remark']").val();
    			param._csrf=$("input[name='_csrf']").val();
    			if ($("[name='id']").val() == "") {
    				var curl ="/base/client/checkOther"+"?_csrf="+$("input[name='_csrf']").val(); 
    				var p = p || {};
    				p.site_id = $("[name='site_id']").val();
    				p.platform = $("[name='platform']").val();
    				p.id=0;
    				$.post(curl, p, function(data) {
    					if(!data){
    						$("#checkClientMessageButton").click();
    						return false;
    					}else{
    						purl = "/base/client/add";
    						$.post(purl, param, function(data) {
    	    					if(data){
    	    						$("#closeB").click();
    	    						$("#addSuccessButton").click();
    	    						$("#searchS").click();
    	    					}
    	    				});
    					}
    				})
    			} else {
    				purl = "/base/client/update";
    				param.id = $("[name='id']").val();
    				$.post(purl, param, function(data) {
    					if(data){
    						$("#closeB").click();
    						$("#updateSuccessButton").click();
    						$("#searchS").click();
    					}
    				});
    			}
            },
        
        rules:{
        	site_id:{
                required:true
            },
            platform:{
                required:true
            },
            name:{
                required:true,
                remote: {
    			    url: "/base/client/validate"+"?_csrf="+$("input[name='_csrf']").val(), 
    			    type: "post",               
    			    dataType: "json",           
    			    data: {
    			    	id: function() {
    			        	if($("[name='id']").val() == null || $("[name='id']").val() == ""){
    			        		return 0;
    			        	}else{
    			        		return $("[name='id']").val();
    			        	}
    			        },
    			        name : function(){
    			        	return $("[name='name']").val();
    			        }
    			    }
    			}
            }
        },
        messages:{
        	site_id:{
                required:"必选"
            },
            platform:{
                required:"必选"
            },
            name:{
            	required:"必填",
            	remote : $("#nameRepeatCheck").html()
            }                                   
        }
                  
    });
        
        $("#closeB").click(function() {  
        	validate.resetForm();  
	    });
	});
	

	$(document).on("click", ".upd", function() {
		var did = $(this).attr("data-id");
		var id = $("#mcId" + did).html();
		var url = "/base/client/getClientById/"+id;
		$.get(url,function(data) {
			$("[name='id']").val(data.id);
			$("[name='site_id']").val(data.site_id);
			$("[name='platform']").val(data.platform);
			$("[name='name']").val(data.name);
			$("[name='remark']").val(data.remark);
			$("[name='is_enabled']").val(data.is_enabled);
		});
		$("#ta").click();
	});

	$("#mcMainCheckbox").click(function() {
		$("input:checkbox").prop("checked", this.checked);
	});

	$("#delT").click(function() {
		var delIds = "";
		var count = 0 ;
		$('input:checkbox[name=checkbox]:checked').each(function(i) {
			var id = $(this).val();
			delIds = $("#mcId" + id).html() + "," + delIds;
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
			delIds = $("#mcId" + id).html() + "," + delIds;
			
		});
		var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/base/client/delete/"+delIds+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
	});

	$("#addT").click(function() {
		$("[name='id']").val(null);
		$("[name='site_id']").val(null);
		$("[name='platform']").val(null);
		$("[name='name']").val(null);
		$("[name='remark']").val(null);
	});

});