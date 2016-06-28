$(function() {
	$("#searchS")
			.click(
					function() {
						var url = "/base/layoutModule/list";
						var limit = 10;
						var param = param || {};
						param.pageNo = 1;
						param.pageLimit = limit;
						param.client_id = $("#client_id").val();
						param.language_id = $("#language_id").val();
						param.layout_id = $("#layout_id").val();
						param.position_id = $("#position_id").val();
						param.name = $("#name").val();
						param._csrf=$("input[name='_csrf']").val();
						paging(url, param, mcmRenderFunction);
					});
	
	var mcmRenderFunction = function(list) {
		var content = "";
		var isEnabled = "";
		$("#bodyT tbody").empty();
		if (list != null) {
			for (var i = 0; i < list.length; i++) {
				if(list[i].is_enabled == 1){
					isEnabled = "Enabled";
				}else{
					isEnabled = "Disabled";
				}
				content += '<tr class="odd gradeX">'
						+ '<td>'
						+ '<input type="checkbox" name="checkbox" class="checkboxes" value="'
						+ i
						+ '"/>'
						+ '</td>'
						+'<td><i class="upd  fa fa-edit btn_edit" data-id='
						+ i + ' ></i></td>'
						+ '<td id="LoyId'
						+ i
						+ '" class="hide">'
						+ list[i].id
						+ '</td>'
						+ '<td>'
						+ (list[i].client ? list[i].client.name : '')
						+ '</td>'
						+ '<td>'
						+ (list[i].language ? list[i].language.name : '')
						+ '</td>'
						+ '<td>'
						+ (list[i].layout ? list[i].layout.name : '')
						+ '</td>'
						+ '<td>'
						+ list[i].name
						+ '</td>'
						+ '<td>'
						+ list[i].code
						+ '</td>'
						+ '<td>'
						+ (list[i].parameter ? list[i].parameter.name : '')
						+ '</td>'
						+ '<td>'
						+ list[i].url
						+ '</td>'
						+ '<td>'
						+ list[i].number
						+ '</td>'
						+ '<td>'
						+ list[i].sort
						+ '</td>'
						+ '<td id="de'+i+'">'
						+ isEnabled
						+ '</td>'
						+ '<td>'
						+ list[i].whoCreated
						+ '</td>'
						+ '<td>'
						+ list[i].createTime
						+ '</td>'
						+ '<td>'
						+ list[i].whoModified
						+ '</td>'
						+ '<td>'
						+ list[i].updateTime
						+ '</td>'
						+ '</tr>';
			}
		}
		$("#bodyT tbody").append(content);
	}
	
	paging("/base/layoutModule/list", {"pageNo":1,"pageLimit":10,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);
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
        		param.client_id = $("[name='client_id']").val();
        		param.language_id = $("[name='language_id']").val();
        		param.layout_id = $("[name='layout_id']").val();
        		param.name = $("[name='name']").val();
        		param.code = $("[name='code']").val();
        		param.position_id = $("[name='position_id']").val();
        		param.url = $("[name='url']").val();
        		param.number = $("[name='number']").val();
        		param.sort = $("[name='sort']").val();
        		param.is_enabled = $("[name='is_enabled']").val();
        		param._csrf=$("input[name='_csrf']").val();
        		if ($("[name='id']").val() == "") {
        			purl = "/base/layoutModule/add";
        			$.post(purl, param, function(data) {
        				if(data){
        					$("#closeB").click();
    						$("#addSuccessButton").click();
    						$("#searchS").click();
    					}
        			});
        		} else {
        			purl = "/base/layoutModule/update";
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
        	client_id:{
                required:true
            },
            language_id:{
                required:true
            },
            layout_id:{
                required:true
            },
            name:{
                required:true
            },
            code:{
                required:true
            }
        },
        messages:{
        	client_id:{
                required:"必填"
            },
            language_id:{
                required:"必填"
            },
            layout_id:{
                required: "必选"
            },
            name:{
                required:"必填"
            },
            code:{
                required:"必填"
            }
        }
                  
    });
        $("#closeB").click(function() {  
        	validate.resetForm();  
	    });
});
	
	$("#addT").click(function() {
		$("[name='id']").val(null);
		$("[name='client_id']").val(null);
		$("[name='language_id']").val(null);
		$("[name='layout_id']").val(null);
		$("[name='name']").val(null);
		$("[name='code']").val(null);
		$("[name='layout_id']").val(null);
		$("[name='url']").val(null);
		$("[name='number']").val(null);
		$("[name='sort']").val(1);
	});

	$(document).on("click", ".upd", function() {
		var did = $(this).attr("data-id");
		var id = $("#LoyId" + did).html();
		var url = "/base/layoutModule/getById/"+id;
		$.get(url,function(data) {
			$("[name='id']").val(data.id);
			$("[name='client_id']").val(data.client_id);
			$("[name='language_id']").val(data.language_id);
			$("[name='layout_id']").val(data.layout_id);
			$("[name='name']").val(data.name);
			$("[name='code']").val(data.code);
			$("[name='position_id']").val(data.position_id);
			$("[name='url']").val(data.url);
			$("[name='number']").val(data.number);
			$("[name='sort']").val(data.sort);
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
			delIds = $("#LoyId" + id).html() ? $("#LoyId" + id).html()+","+delIds:delIds;
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
			delIds = $("#LoyId" + id).html() ? $("#LoyId" + id).html()+","+delIds:delIds;
			
		});
		var csrf =$("input[name='_csrf']").val();
			$.ajax({
				   type: "DELETE",
				   url: "/base/layoutModule/delete/"+delIds+"?_csrf="+csrf,
				   success: function(data){
					   if(data){
						   $("#mcMCDeleteCancelButton").click();
						   $("#deleteSuccessButton").click();
						   $("#searchS").click();
					   }
				   }
				});
	});
	
	$("[name='name']").blur(function(){
		var name = $("[name='name']").val();
		var id = $("[name='id']").val();
		if(id==""){
			var url = "/base/layoutModule/validate";
			var param = param || {};
			param.name = name;
			$.get(url, param, function(data) {
				if (data) {
					$("#checkMessageButton").click();
					$("[name='name']").focus();
					return false;
				}
			});
		}else{
			return false;
		}
		
	});

});