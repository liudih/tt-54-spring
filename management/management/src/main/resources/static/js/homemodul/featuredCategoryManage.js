var editcategory=function(i){
	var id=$("#featureId"+i).html();
	var url = "/homepage/featurecategory/edit/"+id;
	window.location=url;
}

var rowCount=0;  
var rowKeyCount=0; 
var rowBannerCount=0;    

function addRow(){  
	var sku=$("#sku_id").val().trim();
	var id=$("#sku_name_id").val();
	var is_delete=$("#sku_is_delete_id").val();
	var is_enabled=$("#sku_is_enabled").val();
	var sort=$("#sku_sort_id").val();
	if(sku=="" || sort==""){
		return false;
	}
	
	var url= "/homepage/featurecategory/validateskuid/"+sku;
	$.get(url,function(data){
		if(data!=""){
			$("#list_id").val(data.clistingid);
			var list_id=$("#list_id").val();
			var status;
			if(is_enabled==1){
				status="Enabled";
			}else{
				status="Disabled";
			}
			if(id==null || id==""){
				   rowCount++;  
				   var newRow='<tr class="sku_class odd" role="row" id="option'+rowCount+'">';
				   newRow+='<input class="sku_listid_class" type="hidden" id="list_sku_id'+rowCount+'" value="'+list_id+'">';  
				   newRow+='<input class="sku_id_class" type="hidden" name="sku_id'+rowCount+'" >';
				   newRow+='<input class="sku_is_delete_class" type="hidden" name="sku_is_delete_id'+rowCount+'" value="'+is_delete+'">';  
				   newRow+='<td class="sku_sku_class" id="optionsku'+rowCount+'">'+sku+'</td>';
				   newRow+='<td class="sku_sort_class" id="optionskusort'+rowCount+'">'+sort+'</td>';
				   newRow+='<input class="sku_is_enabled_class" type="hidden" id="sku_is_enabled_id'+rowCount+'" value="'+is_enabled+'">';  
				   newRow+='<td>'+status+'</td>';
				   newRow+='<td><i class="fa fa-trash btn_sha" onclick=delRow('+rowCount+')></i></td></tr>';
				   $('#sku_data_tbody').append(newRow);  
				   $("#close_sku_id").click();
				}else{
					var i=$("#num").val();
				    $("#option"+i).remove();  
					   
				   var newRow='<tr class="sku_class odd" role="row" id="option'+i+'">';
				   newRow+='<input  class="sku_listid_class" type="hidden" id="list_sku_id'+i+'" value="'+list_id+'">';
				   newRow+='<input  class="sku_id_class" type="hidden" name="sku_id'+i+'" value="'+id+'">';
				   newRow+='<td  class="sku_sku_class" id="optionsku'+i+'">'+sku+'</td>';
				   newRow+='<input class="sku_is_delete_class" type="hidden" name="sku_is_delete_id'+i+'" value="'+is_delete+'">';
				   newRow+='<td  class="sku_sort_class" id="optionskusort'+i+'">'+sort+'</td>';
				   newRow+='<input class="sku_is_enabled_class" type="hidden" id="sku_is_enabled_id'+i+'" value="'+is_enabled+'">';  
				   newRow+='<td>'+status+'</td>';
				   newRow+='<td><i class="fa fa-edit btn_edit" onclick=editRow('+i+')></i>';
				   newRow+='<i class="fa fa-trash btn_sha" onclick=delRow('+i+')></i></td></tr>';	  
				   $('#sku_data_tbody').append(newRow);
				   $("#close_sku_id").click();
				   document.getElementById("sku_id").readOnly = false;
					$("#sku_id").val("");
					$("#sku_sort_id").val("");
					$("#list_id").val("");
					$("#sku_name_id").val("");
					$("#num").val("");
					$("#sku_id").next().html("");
				}
		}else{
			$("#checkExistSku").click();
		}
	});

}  

jQuery.validator.addMethod("isNumber", function(value, element) {   
	if (value!=null && value!="")
    {
        return !isNaN(value);
    }
    return false;
}, $("#numberCheck").html());

//删除行  
function delRow(rowIndex){  
    $("#option"+rowIndex).remove();  
    rowCount--;  
}  

//添加行  
function addKeyRow(){  
	var is_delete=$("#key_is_delete_id").val();
	var is_enabled=$("#key_is_enabled").val();
	var id=$("#key_name_id").val();
	var keyword=$("#keyword_id").val();
	var sort=$("#key_sort_id").val();
	if(keyword=="" || sort==""){
		return false;
	}
	var status;
	if(is_enabled==1){
		status="Enabled";
	}else{
		status="Disabled";
	}
	if(id=="" || id==null){
		rowKeyCount++;  

	   var newRow='<tr class="key_class odd" role="row" id="option_key'+rowKeyCount+'">';  
	   newRow+='<input class="key_id_class" type="hidden" name="key_id'+rowKeyCount+'">';
	   newRow+='<td class="key_keyword_class" id="option_keyword'+rowKeyCount+'">'+keyword+'</td>';
	   newRow+='<td class="key_sort_class" id="optionkeysort'+rowKeyCount+'">'+sort+'</td>';
	   newRow+='<input class="key_is_delete_class" type="hidden" id="key_is_delete'+rowKeyCount+'" value="'+is_delete+'">';  
	   newRow+='<input class="key_is_enabled_class"  type="hidden"  id="key_enabled_id'+rowKeyCount+'" value="'+is_enabled+'">';  
	   newRow+='<td >'+status+'</td>';
	   newRow+='<td><i class="fa fa-trash btn_sha" onclick=delKeyRow('+rowKeyCount+')></i></td></tr>';
	   $('#key_data_tbody').append(newRow);  
	   $("#close_key_id").click();
	}else{
		var i=$("#keynum").val();
		$("#option_key"+i).remove(); 
		var newRow='<tr class="key_class odd" role="row" id="option_key'+i+'">';  
		newRow+='<input class="key_id_class" type="hidden" name="key_id'+i+'" value="'+id+'">';
		newRow+='<td class="key_keyword_class" id="option_keyword'+i+'">'+keyword+'</td>';
		newRow+='<td class="key_sort_class" id="optionkeysort'+i+'">'+sort+'</td>';
	    newRow+='<input class="key_is_delete_class" type="hidden" id="key_is_delete'+i+'" value="'+is_delete+'">';  
	    newRow+='<input class="key_is_enabled_class"  type="hidden"  id="key_enabled_id'+i+'" value="'+is_enabled+'">';  
	    newRow+='<td >'+status+'</td>';
		newRow+='<td><i class="fa fa-edit btn_edit" onclick=editKeyRow('+i+')></i>';
		newRow+='<i class="fa fa-trash btn_sha" onclick=delKeyRow('+i+')></i></td></tr>';
		$('#key_data_tbody').append(newRow);  
	    $("#close_key_id").click();
		document.getElementById("keyword_id").readOnly = true;
	    $("#keynum").val("");
		$("#key_name_id").val("");
		$("#keyword_id").val("");
		$("#key_sort_id").val("");
	}
}  

//删除行  
function delKeyRow(rowIndex){  
    $("#option_key"+rowIndex).remove();  
    rowKeyCount--;  
}  

$("#close_sku_id").click(function(){
	$("#sku_id").next().html("");
	document.getElementById("sku_id").readOnly = false;
});

$("#close_key_id").click(function(){
	document.getElementById("keyword_id").readOnly = false;
});

//添加行  
function addBannerRow(){  
	var title=$("#title_id").val();
	var url=$("#url_id").val();
	var param=$("#param_id").val();
	var sort=$("#sort_banner_id").val();
	var id=$("#banner_name_id").val();
	if(title=="" || sort==""||url=="" || param==""){
		return false;
	}
	if(id=="" || id==null){
		var flie=$("input[name='file']").val();
		if(flie !=""){
			$("#imgUploadForm").ajaxSubmit({
				success : function (data){
					if(data.succeed == true){
						$("#imgUrl").val(data.path);
						var title=$("#title_id").val();
						var sort=$("#sort_banner_id").val();
						var url=$("#url_id").val();
						var param=$("#param_id").val();
						var paramNmae=$("#param_id").find("option:selected").text();
						var imgUrl=$("#imgUrl").val();
						var is_delete=$("#banner_is_delete_id").val();
						var is_enabled=$("#banner_is_enabled").val();
						var status;
						if(is_enabled==1){
							status="Enabled";
						}else{
							status="Disabled";
						}
						if(title=="" || sort==""||url=="" || param==""){
							return false;
						}
						rowBannerCount++;  

						var newRow='<tr class="banner_class odd" role="row" id="option_banner'+rowBannerCount+'">';  
						newRow+='<input  class="banner_id_class" type="hidden" name="banner_id'+rowBannerCount+'"  value="'+id+'">';
						newRow+='<input type="hidden" name="banner_id'+rowBannerCount+'" >';
						newRow+='<td  class="banner_title_class" id="option_title'+rowBannerCount+'">'+title+'</td>';
						newRow+='<td ><img width="100" height="80" class="banner_img_class"  id="img_id'+rowBannerCount+'" height="50" src="'+imgUrl+'"></td>'
						newRow+='<td class="banner_url_class" id="optionbannerUrl'+rowBannerCount+'">'+url+'</td>';
						newRow+='<td><input   class="banner_param_class"  type="hidden" id="optionbannerparam'+rowBannerCount+'" value="'+param+'">'+paramNmae+'</td>';
						newRow+='<td   class="banner_sort_class" id="optionbannersort'+rowBannerCount+'">'+sort+'</td>';
					    newRow+='<input class="banner_is_delete_class" type="hidden" id="banner_delete'+rowBannerCount+'" value="'+is_delete+'">';  
					    newRow+='<input class="banner_is_enabled_class" type="hidden" id="banner_enabled'+rowBannerCount+'" value="'+is_enabled+'">';  
					    newRow+='<td >'+status+'</td>';
						newRow+='<td><i class="fa fa-trash btn_sha" onclick=delBannerRow('+rowBannerCount+')></i></td></tr>';
						$('#banner_data_tbody').append(newRow);  
						 $("#close_banner_id").click();
					}else{
						$("#imageUploadFailedButton").click();
					}
				}
			});
			return false;
		}
		var title=$("#title_id").val();
		var sort=$("#sort_banner_id").val();
		var url=$("#url_id").val();
		var param=$("#param_id").val();
		var paramNmae=$("#param_id").find("option:selected").text();
		var imgUrl=$("#imgUrl").val();
		var is_delete=$("#banner_is_delete_id").val();
		var is_enabled=$("#banner_is_enabled").val();
		var status;
		if(is_enabled==1){
			status="Enabled";
		}else{
			status="Disabled";
		}
		if(title=="" || sort==""||url=="" || param==""){
			return false;
		}
		rowBannerCount++;  
		var newRow='<tr class="banner_class odd" role="row" id="option_banner'+rowBannerCount+'">';  
		newRow+='<input  class="banner_id_class" type="hidden" name="banner_id'+rowBannerCount+'"  value="'+id+'">';
		newRow+='<input type="hidden" name="banner_id'+rowBannerCount+'" >';
		newRow+='<td  class="banner_title_class" id="option_title'+rowBannerCount+'">'+title+'</td>';
		newRow+='<td ><img width="100" height="80" class="banner_img_class" id="img_id'+rowBannerCount+'" height="50" src="'+imgUrl+'"></td>'
		newRow+='<td class="banner_url_class" id="optionbannerUrl'+rowBannerCount+'">'+url+'</td>';
		newRow+='<td><input   class="banner_param_class"  type="hidden" id="optionbannerparam'+rowBannerCount+'" value="'+param+'">'+paramNmae+'</td>';
		newRow+='<td   class="banner_sort_class" id="optionbannersort'+rowBannerCount+'">'+sort+'</td>';
	    newRow+='<input class="banner_is_delete_class" type="hidden" id="banner_delete'+rowBannerCount+'" value="'+is_delete+'">';  
	    newRow+='<input class="banner_is_enabled_class" type="hidden" id="banner_enabled'+rowBannerCount+'" value="'+is_enabled+'">';  
	    newRow+='<td >'+status+'</td>';
		newRow+='<td><i class="fa fa-trash btn_sha" onclick=delBannerRow('+rowBannerCount+')></i></td></tr>';
		$('#banner_data_tbody').append(newRow);  
		 $("#close_banner_id").click();
	}else{
		var flie=$("input[name='file']").val();
		if(flie !=""){
			$("#imgUploadForm").ajaxSubmit({
				success : function (data){
					if(data.succeed == true){
						$("#imgUrl").val(data.path);
						var id=$("#banner_name_id").val();
						var title=$("#title_id").val();
						var sort=$("#sort_banner_id").val();
						var url=$("#url_id").val();
						var param=$("#param_id").val();
						var paramNmae=$("#param_id").find("option:selected").text();
						var img_url=$("#imgUrl").val();
						var is_delete=$("#banner_is_delete_id").val();
						var is_enabled=$("#banner_is_enabled").val();
						var status;
						if(is_enabled==1){
							status="Enabled";
						}else{
							status="Disabled";
						}
						if(title=="" || sort==""||url=="" || param==""){
							return false;
						}
						var i=$("#bannernum").val();
						$("#option_banner"+i).remove();  
						var newRow='<tr class="banner_class odd" role="row" id="option_banner'+i+'">';  
						newRow+='<input  class="banner_id_class" type="hidden" name="banner_id'+i+'"  value="'+id+'">';
						newRow+='<td  class="banner_title_class" id="option_title'+i+'">'+title+'</td>';
						newRow+='<td ><img width="100" height="80"  class="banner_img_class" id="img_id'+i+'" height="50" src="'+img_url+'"></td>';
						newRow+='<td class="banner_url_class" id="optionbannerUrl'+i+'">'+url+'</td>';
						newRow+='<td><input class="banner_param_class" type="hidden" id="optionbannerparam'+i+'"  value="'+param+'">'+paramNmae+'</td>';
						newRow+='<td class="banner_sort_class" id="optionbannersort'+i+'">'+sort+'</td>';
					    newRow+='<input class="banner_is_delete_class" type="hidden" id="banner_delete'+i+'" value="'+is_delete+'">';  
					    newRow+='<input class="banner_is_enabled_class" type="hidden" id="banner_enabled'+i+'" value="'+is_enabled+'">';  
					    newRow+='<td >'+status+'</td>';
						newRow+='<td><i class="fa fa-edit btn_edit" onclick=editBannerRow('+i+')></i>';
						newRow+='<i class="fa fa-trash btn_sha" onclick=delBannerRow('+i+')></i></td></tr>';
						$('#banner_data_tbody').append(newRow);  
						 $("#close_banner_id").click();
							$("#banner_name_id").val("");
							$("#title_id").val("");
							$("#sort_banner_id").val("");
							$("#url_id").val("");
							$("#param_id").val("");
							$("#imgUrl").val("");
							$("#bannernum").val("");
							$("#banner_img_id").attr("src","");
					}else{
						$("#imageUploadFailedButton").click();
					}
				}
			});
			return false;
		}
		var id=$("#banner_name_id").val();
		var title=$("#title_id").val();
		var sort=$("#sort_banner_id").val();
		var url=$("#url_id").val();
		var param=$("#param_id").val();
		var paramNmae=$("#param_id").find("option:selected").text();
		var img_url=$("#imgUrl").val();
		var is_delete=$("#banner_is_delete_id").val();
		var is_enabled=$("#banner_is_enabled").val();
		var status;
		if(is_enabled==1){
			status="Enabled";
		}else{
			status="Disabled";
		}
		if(title=="" || sort==""||url=="" || param==""){
			return false;
		}
		var i=$("#bannernum").val();
		$("#option_banner"+i).remove();  
		var newRow='<tr class="banner_class odd" role="row" id="option_banner'+i+'">';  
		newRow+='<input  class="banner_id_class" type="hidden" name="banner_id'+i+'"  value="'+id+'">';
		newRow+='<td  class="banner_title_class" id="option_title'+i+'">'+title+'</td>';
		newRow+='<td ><img width="100" height="80" class="banner_img_class" id="img_id'+i+'" height="50" src="'+img_url+'"></td>';
		newRow+='<td class="banner_url_class" id="optionbannerUrl'+i+'">'+url+'</td>';
		newRow+='<td><input class="banner_param_class" type="hidden" id="optionbannerparam'+i+'"  value="'+param+'">'+paramNmae+'</td>';
		newRow+='<td class="banner_sort_class" id="optionbannersort'+i+'">'+sort+'</td>';
	    newRow+='<input class="banner_is_delete_class" type="hidden" id="banner_delete'+i+'" value="'+is_delete+'">';  
	    newRow+='<input class="banner_is_enabled_class" type="hidden" id="banner_enabled'+i+'" value="'+is_enabled+'">';  
	    newRow+='<td >'+status+'</td>';
		newRow+='<td><i class="fa fa-edit btn_edit" onclick=editBannerRow('+i+')></i>';
		newRow+='<i class="fa fa-trash btn_sha" onclick=delBannerRow('+i+')></i></td></tr>';
		$('#banner_data_tbody').append(newRow);  
		 $("#close_banner_id").click();
			$("#banner_name_id").val("");
			$("#title_id").val("");
			$("#sort_banner_id").val("");
			$("#url_id").val("");
			$("#param_id").val("");
			$("#imgUrl").val("");
			$("#bannernum").val("");
			$("#banner_img_id").attr("src","");
	}
}  

//删除行  
function delBannerRow(rowIndex){  
  $("#option_banner"+rowIndex).remove();  
  rowBannerCount--;  
}  

function editRow(rowIndex){
	$("#button_sku").click();
	$("#sku_add_title").html("编辑Sku");
	var ku=$("#optionsku"+rowIndex).html().trim();
	var sor=$("#optionskusort"+rowIndex).html();
	var id=$("input[name='sku_id"+rowIndex+"']").val();
	var is_delete=$("input[name='sku_is_delete_id"+rowIndex+"']").val();
	var is_enabled=$("#sku_is_enabled_id"+rowIndex).val();
	var listid=$("#list_sku_id"+rowIndex).val();
	$("#sku_is_delete_id").val(is_delete);
	$("#sku_is_enabled").val(is_enabled);
	$("#sku_id").val(ku);
	$("#sku_sort_id").val(sor);
	$("#list_id").val(listid);
	$("#sku_name_id").val(id);
	$("#num").val(rowIndex);
	document.getElementById("sku_id").readOnly = true;
}

function editKeyRow(rowIndex){
	$("#button_key").click();
	$("#key_add_title").html("编辑热门关键字");
	var keyword=$("#option_keyword"+rowIndex).html();
	var is_delete=$("#key_is_delete"+rowIndex).val();
	var is_eabled=$("#key_enabled_id"+rowIndex).val();
	var sort=$("#optionkeysort"+rowIndex).html();
	var id=$("input[name='key_id"+rowIndex+"']").val();
	$("#keyword_id").val(keyword);
	$("#key_is_delete_id").val(is_delete);
	$("#key_is_enabled").val(is_eabled);
	$("#key_sort_id").val(sort);
	$("#key_name_id").val(id);
	$("#keynum").val(rowIndex);
	document.getElementById("keyword_id").readOnly = true;
}

function editBannerRow(rowIndex){
	$("#button_banner").click(); 
	 $("#banner_add_title").html("编辑Banner");
	var title=$("#option_title"+rowIndex).html();
	var img_id=$("#img_id"+rowIndex).attr("src");
	var url=$("#optionbannerUrl"+rowIndex).html();
	var param=$("#optionbannerparam"+rowIndex).val();
	var sort=$("#optionbannersort"+rowIndex).html();
	var id=$("input[name='banner_id"+rowIndex+"']").val();
	var enabled=$("#banner_enabled"+rowIndex).val();
	var deleted=$("#banner_delete"+rowIndex).val();
	$("#banner_img_id").show();
	$("#banner_img_id").attr("src",img_id);
	$("#banner_is_delete_id").val(deleted);
	$("#banner_is_enabled").val(enabled);
	$("#title_id").val(title);
	$("#imgUrl").val(img_id);
	$("#url_id").val(url);
	$("#param_id").val(param);
	$("#sort_banner_id").val(sort);
	$("#banner_name_id").val(id);
	$("#bannernum").val(rowIndex);
	
}

var mcmRenderFunction = function(list){
	var content = "";
	$("#table_form_id tbody").empty();
	if(list.length > 0){
		for(var i=0;i<list.length;i++){
			content += '<tr class="odd gradeX">'+
                    '<td>'+
                        '<input name="checkbox"  type="checkbox" class="checkboxes" value="'+i+'"  /> </td>'+
                    '<td id="featureId'+i+'" class="hide">'+list[i].id+'</td>'+
                    '<td><span class="up" data-id="'+i+'" onclick="editcategory('+i+')">'+'<i class="fa fa-edit btn_edit" data-toggle="modal" data-target="#static"></i>'+'</span></td>'+
                    '<td>'+list[i].client.name+'</td>'+
                    '<td>'+list[i].language.name+'</td>'+
                    '<td>'+stringShow(list[i].category.cpath)+'</td>'+
                    '<td>'+stringShow(list[i].sort)+'</td>'+
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

var getCategoryValue = function(type){
	var categoryValue = null;
	categoryValue = $("#goods_category_id").val();
	var categoryString = "";
	if(categoryValue != null && categoryValue != "" && categoryValue.length > 0){
		for(var i=0;i<categoryValue.length;i++){
			categoryString += ","+categoryValue[i];
		}
		return categoryString.substring(1);
	}
	return categoryString;
}

$(function(){
	$.get("/homepage/featurecategory/getId/"+$("#edit_ids").val(),function(data){
		if(data!=""){
			$("select[name='language_id']").val(data.language_id);
			$("select[name='category_id']").val(data.category_id);
			$("input[name='img_url']").val(data.img_url);
			$("input[name='id']").val(data.id);
			$("input[name='number']").val(data.number);
			$("input[name='sort']").val(data.sort);
			if(data.is_enabled==null){
				$("select[name='is_enabled']").val(1);
			}else{
				$("select[name='is_enabled']").val(data.is_enabled);
			}
			if(data.clients){
				for(var i=0;i<data.clients.length;i++){
					$("select[name='client_id']").val(data.clients[i]);
				}
			}
			if(data.skus!=null && data.skus.length>0){
				$("#sku_data_id").val(data.skus.length);
				for(var i=1;i<=data.skus.length;i++){
					var status;
					if(data.skus[i-1].is_enabled==1){
						status="Enabled";
					}else{
						status="Disabled";
					}
				   var newRow='<tr class="sku_class odd" role="row" id="option'+i+'">';
				    newRow+='<input  class="sku_listid_class" type="hidden" id="list_sku_id'+i+'" value="'+data.skus[i-1].listing_id+'">';
				    newRow+='<input class="sku_id_class" type="hidden" name="sku_id'+i+'" value="'+data.skus[i-1].id+'">';
				    newRow+='<td  class="sku_sku_class" id="optionsku'+i+'">'+data.skus[i-1].sku+'</td>';
				    newRow+='<input class="sku_is_delete_class" type="hidden" name="sku_is_delete_id'+i+'" value="'+data.skus[i-1].is_deleted+'">';
				    newRow+='<td  class="sku_sort_class" id="optionskusort'+i+'">'+data.skus[i-1].sort+'</td>';
				    newRow+='<input class="sku_is_enabled_class" type="hidden" id="sku_is_enabled_id'+i+'" value="'+data.skus[i-1].is_enabled+'">'; 
				    newRow+='<td>'+status+'</td>';
				    newRow+='<td><i class="fa fa-edit btn_edit" onclick=editRow('+i+')></i>';
				    newRow+='<i class="fa fa-trash btn_sha" onclick=delRow('+i+')></i></td></tr>';
				   $('#sku_data_tbody').append(newRow);  
					 rowCount++;

				}
			}
			if(data.keys){
				$("#key_data_id").val(data.keys.length);
				for(var i=1;i<=data.keys.length;i++){
					var status;
					if(data.keys[i-1].is_enabled==1){
						status="Enabled";
					}else{
						status="Disabled";
					}
					var newRow='<tr class="key_class odd" role="row" id="option_key'+i+'">';  
					newRow+='<input class="key_id_class" type="hidden" name="key_id'+i+'" value="'+data.keys[i-1].id+'">';
					newRow+='<td class="key_keyword_class" id="option_keyword'+i+'">'+data.keys[i-1].key+'</td>';
					newRow+='<td class="key_sort_class" id="optionkeysort'+i+'">'+data.keys[i-1].sort+'</td>';
					newRow+='<input class="key_is_delete_class" type="hidden" id="key_is_delete'+i+'" value="'+data.keys[i-1].is_deleted+'">';  
				   	newRow+='<input class="key_is_enabled_class"  type="hidden"  id="key_enabled_id'+i+'" value="'+data.keys[i-1].is_enabled+'">';  
				   	newRow+='<td >'+status+'</td>';
					newRow+='<td><i class="fa fa-edit btn_edit" onclick=editKeyRow('+i+')></i>';
					newRow+='<i class="fa fa-trash btn_sha" onclick=delKeyRow('+i+')></i></td></tr>';
					$('#key_data_tbody').append(newRow);  
					 rowKeyCount+=1;
				}
			}
			
			if(data.banners){
				$("#banner_data_id").val(data.banners.length);
				for(var i=1;i<=data.banners.length;i++){
					var status;
					if(data.banners[i-1].is_enabled==1){
						status="Enabled";
					}else{
						status="Disabled";
					}
					var newRow='<tr class="banner_class odd" role="row" id="option_banner'+i+'">';  
					newRow+='<input  class="banner_id_class" type="hidden" name="banner_id'+i+'"  value="'+data.banners[i-1].id+'">';
					newRow+='<input type="hidden" name="banner_id'+i+'" >';
					newRow+='<td  class="banner_title_class" id="option_title'+i+'">'+data.banners[i-1].title+'</td>';
					newRow+='<td ><img width="100" height="80" class="banner_img_class" id="img_id'+i+'" height="50" src="'+data.banners[i-1].img_url+'"></td>'
					newRow+='<td class="banner_url_class" id="optionbannerUrl'+i+'">'+data.banners[i-1].url+'</td>';
					newRow+='<td><input   class="banner_param_class"  type="hidden" id="optionbannerparam'+i+'" value="'+data.banners[i-1].position_id+'">'+data.banners[i-1].positionName+'</td>';
					newRow+='<td   class="banner_sort_class" id="optionbannersort'+i+'">'+data.banners[i-1].sort+'</td>';
				    newRow+='<input class="banner_is_delete_class" type="hidden" id="banner_delete'+i+'" value="'+data.banners[i-1].is_deleted+'">';  
				    newRow+='<input class="banner_is_enabled_class" type="hidden" id="banner_enabled'+i+'" value="'+data.banners[i-1].is_enabled+'">';  
				    newRow+='<td >'+status+'</td>';
					newRow+='<td><i class="fa fa-edit btn_edit" onclick=editBannerRow('+i+')></i><i class="fa fa-trash btn_sha" onclick=delBannerRow('+i+')></i></td></tr>';
					$('#banner_data_tbody').append(newRow);  
					rowBannerCount+=1;
				}
			}
		}
	});
	paging("/homepage/featurecategory/getList", {"pageNo":1,"_csrf":$("input[name='_csrf']").val()}, mcmRenderFunction);

	$("#mcSearch").click(function(){
		var url = "/homepage/featurecategory/getList";
		var param = {};
		param.pageNo = 1;
		param.languages = getLanguageValue();
		param.clients = getClientValue();
		param.categorys= getCategoryValue();
		param._csrf=$("input[name='_csrf']").val();
		paging(url,param,mcmRenderFunction);
	});

	$("#save_all_data").click(function(){
		$("#submit_All_data").click();
	});
	
	$("#close_return").click(function(){
		var url="/homepage/featurecategory";
		window.location=url;
	});
	
	$("#mcMainCheckbox").click(function(){
		$("input:checkbox").prop("checked",this.checked);
		var checkboxs="";
	});
	
	$("#delete_id").click(function(){
		var delIds="";
		$('input:checkbox[name=checkbox]:checked').each(function(i){
				var id =$(this).val();
				delIds = $("#featureId"+id).html()+ ","+delIds;
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
				delIds = $("#featureId"+id).html()+ ","+delIds;
		});
		$("#deleteCancelButton").click();
		var url = "/homepage/featurecategory/delete/"+delIds;
		$.get(url,function(msg){
			if(msg=="SUCCESS"){
				$("#deleteSuccessButton").click();
				$("#mcSearch").click();
			}else{
				$("#deleteFailedButton").click();
			}
		});
	});
	 
		//验证网址的格式
	 jQuery.validator.addMethod("Wurl", function(value, element) {  
	     var chrnum =/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;  
	     return this.optional(element) || (chrnum.test(value));  
	 }, "URL格式不正确！"); 


	 var validateData=$("#featured_category_form_Id").validate({
		 rules : {
			 category_id : {
				 required : true
			  },
			  client_id : {
				  required : true
			  },
			  language_id : {
				  required : true
			  },
			  is_enabled:{
				  required : true
			  },
			  sort:{
			    	isNumber:true
			  },
			  number:{
			    	isNumber:true
			  }
			},		
			messages:{
				category_id : {
					 required : "商品类目不能为空！"
				  },
				  client_id : {
					  required : "客户端不能为空"
				  },
				  language_id : {
					  required : "语言不能为空"
				  },
				  is_enabled:{
					  required : "状态不能为空"
				  }
			},
			submitHandler:function(form){
				$("#checkSkuRepeatAdd").click();
				var features={};
				features.language_id=$("select[name='language_id']").val();
				features.category_id=$("select[name='category_id']").val();
				features.number=$("input[name='number']").val();
				features.sort=$("input[name='sort']").val();
				features.img_url=$("input[name='img_url']").val();
				features.is_enabled=$("select[name='is_enabled']").val();
				features.is_deleted=$("input[name='is_deleted']").val();
				
				var client_ids=new Array(); 
				var edit=$("#edit_data_id").val();
				if(edit=="add"){
					$("select[name='client_id']").each(function(i){
						var id =$(this).val();
						client_ids=id;
					});
				}else{
					for(var i=0;i<$("select[name='client_id']").length;i++){
						client_ids[i]=$($("select[name='client_id']")[i]).val();
					}
				}
				
				features.clients=client_ids;
				if(features.clients==null || features.clients.length<=0){
					$("#client_id_error").html("请选择客户端");
					return;
				}
				$("#client_id_error").html("");
				var listsku=[];
				var listkey=[];
				var listbanner=[];
				
				var skusList=[];
				var skuList=$(".sku_class");
				if(skuList.length>0){
					for (var i=0;i<skuList.length;i++)
					{
						var id=$($(".sku_id_class")[i]).val();
						var sku=$($(".sku_sku_class")[i]).html().trim();
						var sort=$($(".sku_sort_class")[i]).html();
						var is_delete=$($(".sku_is_delete_class")[i]).val();
						var is_enabled=$($(".sku_is_enabled_class")[i]).val();
						var listid=$($(".sku_listid_class")[i]).val();
						skusList[i]=sku;
						listsku[i]= {"sku":sku,"sort":sort,"id":id,"listing_id":listid,"is_deleted":is_delete,"is_enabled":is_enabled};
					}
					features.skus=listsku;
				}
				
				if(skusList.length>0){
					for (var i=0;i<skusList.length;i++){
						for (var j=i+1;j<skusList.length;j++){
							if(skusList[i]==skusList[j]){
								//$("#checkSkuRepeatAdd").click();
								alert("Sku不能有重复！");
								return;
							}
						}
					}
				}
				 
				var keysList=$(".key_class");
				if(keysList.length>0){
					for (var i=0;i<keysList.length;i++){
						var id=$($(".key_id_class")[i]).val();
						var key=$($(".key_keyword_class")[i]).html();
						var sort=$($(".key_sort_class")[i]).html();
						var is_delete=$($(".key_is_delete_class")[i]).val();
						var is_enabled=$($(".key_is_enabled_class")[i]).val();
						listkey[i]= {"key":key,"sort":sort,"id":id,"is_deleted":is_delete,"is_enabled":is_enabled};
					}
					features.keys=listkey;
				}
				var bannerList=$(".banner_class");
				if(rowBannerCount>0){
					for (var i=0;i<rowBannerCount;i++){
						var id=$($(".banner_id_class")[i]).val();
						var title=$($(".banner_title_class")[i]).html();
						var img=$($(".banner_img_class")[i]).attr("src");
						var url=$($(".banner_url_class")[i]).html();
						var param=$($(".banner_param_class")[i]).val();
						var is_delete=$($(".banner_is_delete_class")[i]).val();
						var is_enabled=$($(".banner_is_enabled_class")[i]).val();
						var sort=$($(".banner_sort_class")[i]).html();
						listbanner[i]= {"position_id":param,"title":title,"sort":sort,"url":url,"id":id,"img_url":img,"is_deleted":is_delete,"is_enabled":is_enabled};
					}
					features.banners=listbanner;
				}
				var edit_info=$("#edit_data_id").val();
				features._csrf=$("input[name='_csrf']").val();
				if($("input[name='id']").val() != "" && $("input[name='id']").val()!=0){
					debugger;
					features.id=$("input[name='id']").val();
					var url = "/homepage/featurecategory/update?_csrf="+$("input[name='_csrf']").val();
					$.ajax(
							{url : url,
								data: $.toJSON(features),  
							    type: "POST",
							    contentType :"application/json",
							    success : function(msg){
								if(msg=="SUCCESS"){
									$("#updateSuccessButton").click();
								}else{
									$("#updateFailedButton").click();
								}
					}});
				}else{
					var categorys={};
					categorys.language_id=$("select[name='language_id']").val();
					categorys.category_id=$("select[name='category_id']").val();
					var clientStr;
					for(var i=0;i<client_ids.length;i++){
						clientStr=client_ids[i]+",";
					}
					categorys.clients=clientStr;
					categorys._csrf=$("input[name='_csrf']").val();
					var url_category="/homepage/featurecategory/validateFeaturedCategory";
					$.post(url_category,categorys,function(ms){
						if(ms=="SUCCESS"){
							var sku=$("#sku_Id").val().trim();
							if(sku=="" || sku==null){
								var url = "/homepage/featurecategory/add?_csrf="+$("input[name='_csrf']").val();
								$.ajax(
										{url : url,
											data: $.toJSON(features),  
										    type: "POST",
										    contentType :"application/json",
										    success : function(msg){
									if(msg=="SUCCESS"){
										$("#addSuccessButton").click();
									}else{
										$("#addFailedButton").click();
									}
								}});
							}else{
								var validatee_url= "/homepage/featurecategory/validateskudataid/"+sku;
								$.get(url,function(msg){
									if(msg=="SUCCESS"){
										var url = "/homepage/featurecategory/add?_csrf="+$("input[name='_csrf']").val();
										$.ajax(
												{url : url,
													data: $.toJSON(features),  
												    type: "POST",
												    contentType :"application/json",
												    success : function(msg){
											if(msg=="SUCCESS"){
												$("#addSuccessButton").click();
											}else{
												$("#addFailedButton").click();
											}
										}});
									}else{
										$("#checkMessageButton").click();
										$("#sku_submit_id").attr("disabled","disabled");
									}
								});
							}
						}else{
							$("#checkClientAndLanguageAndLanguageMessageButton").click();
						}
					});
				}
			}
	    });

	 $("#button_sku").click(function(){
		 button_sku.resetForm();
		 $("#sku_id").attr("readonly",false);
		 $("#sku_add_title").html("新增Sku");
	 });
	
	 $("#button_key").click(function(){
		 button_key.resetForm();
		 $("#key_add_title").html("新增热门关键字");
	 });
	 $("#button_banner").click(function(){
		 button_banner.resetForm();
		 $("#banner_img_id").attr("src","");
		 $("#banner_img_id").hide();
		 $("#imgUrl").val("");
		 $("#upload_file").val("");
		 $("#banner_add_title").html("新增Banner");
	 });
	 
	var button_sku= $("#sku_form_Id").validate({
		 rules : {
			 sku : {
				 required : true
			  },
			  sku_sort : {
				  required : true,
				  isNumber:true
			  }
			},
			messages:{
				 sku: {
					 required : "sku不能为空！"
				  },
				  sku_sort:{
					  required:"排序号不能为空！"
				  }
			},
			submitHandler:function(){
				var skuId=$("input[name='category_id']").val();
				if(skuId==""){
					alert("特色类目不能为空！");
					return false;
				};

			}
	    });

	var button_key= $("#key_form_Id").validate({
		 rules : {
			 keyword_id : {
				 required : true
			  },
			  sort_key_id : {
				  required : true,
				  isNumber:true
			  }
			},
			messages:{
				keyword_id: {
					 required : "关键字不能为空！"
				  },
				  sort_key_id:{
					  required:"排序号必填！"
				  }
			},
			submitHandler:function(){
				var skuId=$("input[name='category_id']").val();
				if(skuId==""){
					alert("特色类目不能为空！");
					return false;
				};

			}
	    });

	var button_banner= $("#banner_form_Id").validate({
		 rules : {
			 title_id : {
				 required : true
			  },
			  sort_banner_id : {
				  required : true,
				  isNumber:true
			  },
			  url_id : {
				 required : true,
				 Wurl:true
			  },
			  param_id : {
				  required : true				
			  }
			},
			messages:{
				title_id: {
					 required : "标题不能为空！"
				  },
				  sort_banner_id:{
					  required:"排序号必填！"
				  },
				  url_id: {
					 required : "url必填！"
				  },
				  param_id:{
					  required:"参数必填！"
				  }
			},
			submitHandler:function(){
				var skuId=$("input[name='category_id']").val();
				if(skuId==""){
					alert("特色类目不能为空！");
					return false;
				};
			}
	    });

});

function closeCategoryBtn(){
	setTimeout(function(){$("#closeCategoryid").click();},1500);  
}
function closeSkuBtn(){
	setTimeout(function(){$("#closeSkuId").click();},1500);  
}
function closeSkuExistBtn(){
	setTimeout(function(){$("#closeExistSkuId").click();},1500);  
}