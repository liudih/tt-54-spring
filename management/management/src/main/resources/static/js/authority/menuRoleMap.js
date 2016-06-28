function roleMenuListem() {
	addCheckboxListem();
	searchData();
	$("#searchData").bind("click",function(){
		updateMenuRoleMap();
	});
}
function addCheckboxListem(){
	//给所有复选框添加监听事件
	$("input[class='checkboxes']").bind("click",function(){
		if($(this).parent().attr("class")=="checked"){
			$(this).parent().removeClass();
		}else{
			$(this).parent().attr("class","checked");
			selectParentCheckBox($(this).attr("id"));
		}
	});
}

function selectParentCheckBox(id){
	var leave=$("#"+id).attr("leave");
	var parentId=$("#"+id).attr("patentId");
	if($("#"+id).parent().attr("class")!="checked"){
		$("#"+id).parent().attr("class","checked");
	} 
	if(leave==1){
		return;
	}
	selectParentCheckBox(parentId);
}

function validateParentCheckbox(id){
	var leave=$("#"+id).attr("leave");
	var parentId=$("#"+id).attr("patentId");
	if(leave==1){
		return "";
	}
	if($("#"+parentId).parent().attr("class")=="checked"){
		return validateParentCheckbox(parentId);
	}else{
		return parentId;
	}
}
function updateMenuRoleMap(){
	var param=$("#roleId").val();
	if($.trim(param)=="-1"){
		$("#roleEmpty").click();
		return;
	}
	
	var checkList=$("input:checked");
	var menuList="";
	var validateCheckParent=true;
	var needCheckBox="";
	if($.trim(checkList)!=""){
		$.each(checkList,function(index,item){
			if($.trim($(item).attr("id"))!=""){
				menuList=menuList+","+$(item).attr("id");
				
				var temp=validateParentCheckbox($(item).attr("id"));
				if($.trim(temp)!=""){
					validateCheckParent=false;
					needCheckBox=temp;
				}
			}
		});
	}
	if(!validateCheckParent){
		var parentText=$("#"+needCheckBox).parent().parent().siblings().html();
		var tips=$("#parentCheckTips").html();
		$("#parentCheckMessageTips").html(tips+'"'+parentText+'"');
		$("#parentCheckboxValidate").click();
		return;
	}
	$.ajax({
		type : "POST",
		url : "/role/menu/map",
		data : {
			roleId : param,
			menus  :menuList,
			_csrf  : $("input[name='_csrf']").val()
		},
		datatype : "json",
		success : function(data) {
			if($.trim(data)=="Y"){
				$("#menuUpdateSuccessAlertBtn").click();
			}else{
				$("#parentCheckboxValidate").click();
			}
		},
		error : function() {
			$("#loadDataError").click();
		}
	});
}
function searchData(){
	$("#roleId").change(function(){
		var param=$("#roleId").val();
		$.ajax({
			type : "POST",
			url : "/role/menu/get",
			data : {
				roleId : param,
				_csrf  : $("input[name='_csrf']").val()
			},
			datatype : "json",
			success : function(data) {
				diplayMenuItem(data,param);
			},
			error : function() {
				$("#loadDataError").click();
			}
		});
	});
}
function diplayMenuItem(data,param){
	if(data){
		if($.trim(data.roleList)!=""){
			var roleList="";
			var tempItem=$("#roleId option").clone();
			var roleList=$(tempItem).get(0).outerHTML;
			$.each(data.roleList,function(inde,item){
					roleList=roleList+$(tempItem).attr("value",item.id).text(item.roleName).get(0).outerHTML;
			});
			$("#roleId").empty().append(roleList);
			$("#roleId").val(param);
		}
		
		//清除列表
		var hideItem=$("#menuList tr:hidden");
		$("#menuList").empty().html(hideItem[0].outerHTML);
		if($.trim(data.allMenu)!=""){
			
			var menuList="";
			var leaveMenuList=$("#menuList tr:hidden")[0].outerHTML;
			var isfirst=true;
			$.each(data.allMenu,function(inde,item){
				var tempItem=$("#menuList tr:hidden").clone();
				var value=item.adminMenu.id;
				var menuName=item.adminMenu.menuName;
				var menuLeave=item.adminMenu.menuLevel;
				var isChecked=item.roleChecked;
				var patentId=item.adminMenu.parentId;
				//先处理第一级
				if(menuLeave==1){
					var rootMenuItem=$(tempItem);
					rootMenuItem.find("#rootLabel").text(menuName).removeAttr("id");
					rootMenuItem.find(".juese_title").val(value);
					rootMenuItem.find("#menuItemTempLabel").removeAttr("id");
					rootMenuItem.find("#menuItemTemp").removeAttr("id");
					if(isChecked){
						rootMenuItem.find(".juese_title input").attr("checked",true).attr("id",value).attr("leave",menuLeave)
						.attr("patentId",patentId).parent().attr("class","checked");
					}else{
						rootMenuItem.find(".juese_title input").attr("id",value).attr("leave",menuLeave).attr("patentId",patentId);
					}
					//leaveMenuList=leaveMenuList+rootMenuItem.find("tr").show()[0].outerHTML;
					leaveMenuList=leaveMenuList+rootMenuItem.show()[0].outerHTML;
				}
				if(menuLeave==2){
					if(isfirst){
						//清除以前数据
						$("#menuList").empty().html(leaveMenuList);
						isfirst=false;
					}
					
					var menuItem=$(tempItem).find("#menuItemTemp");
					menuItem.find("input").attr("id",value);
					menuItem.find("div").siblings().text(menuName);
					//在处理一级后面的层级
					if(item.roleChecked){
						menuItem.find("input").attr("id",value).attr("checked",true).attr("leave",menuLeave)
						.attr("patentId",patentId).parent().attr("class","checked");
					}else{
						menuItem.find("input").attr("id",value).attr("leave",menuLeave).attr("patentId",patentId);
					}
					menuItem.find("#menuItemTempLabel").removeAttr("id");
					menuItem.find("label").show();
					menuItem.removeAttr("id");
					if($("#"+patentId)!=null){
						var target= $("#"+patentId).parents(".juese_title").siblings();
						menuItem.find("label").appendTo(target);
					}
				}
				if(menuLeave>2){
					var menuItem=$(tempItem).find("#menuItemTemp");
					menuItem.find("input").attr("id",value);
					menuItem.find("div").siblings().text(menuName);
					//在处理一级后面的层级
					if(item.roleChecked){
						menuItem.find("input").attr("id",value).attr("checked",true).attr("leave",menuLeave)
						.attr("patentId",patentId).parent().attr("class","checked");
					}else{
						menuItem.find("input").attr("id",value).attr("leave",menuLeave).attr("patentId",patentId);
					}
					menuItem.find("#menuItemTempLabel").removeAttr("id");
					menuItem.find("label").show();
					menuItem.removeAttr("id");
					if($("#"+patentId)!=null){
						var target= $("#"+patentId).parents("td");
						menuItem.find("label").appendTo(target);
					}
				}
			});
			//$("#menuList").empty().append(menuList);
		}
		addCheckboxListem();
	}
}


function roleMenuLoadData() {
	$.ajax({
		type : "POST",
		url : "/role/menu/get",
		data : {	
			roleId : $("#roleId").val(),
			_csrf  : $("input[name='_csrf']").val()
		},
		datatype : "json",
		success : function(data) {
			diplayMenuItem(data,$("#roleId").val());
		},
		error : function() {
			$("#loadDataError").click();
		}
	});
}
function roleMenuInit() {
	roleMenuLoadData();
}
$(function() {
	roleMenuInit();
	roleMenuListem();

});