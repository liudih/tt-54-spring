var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : onRemove,
		onRename : onRename,
		onClick : onClick
	}
};
var zNodes = [];
var log, className = "dark";
var ctreeId = null;
var ctreeNode = null;
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	/*var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);*/
	catalogueUpdateValidator.resetForm();
	$("#catalogueId").val(treeNode.id);
	$("#catalogueName").val(treeNode.name);
	$("#catalogueSort").val(treeNode.sort);
	$("#parentId").val(treeNode.pId);
	$("#catalogueUpdateShowButton").click();
	return false;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	//return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	ctreeId = treeId;
	ctreeNode = treeNode;
	$("#deleteOperateButton").click();
	return false;
}
function onRemove(e, treeId, treeNode) {
	$.ajax({
		url : "/base/cms/content/delete/"+treeNode.id+"?_csrf="+$("input[name='_csrf']").val(),
		type : "delete",
		success : function(data){
			if(data == "succ"){
				showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
						+ treeNode.name);
			}else{
				$("#deleteFailedButton").click();
			}
			refreshTree();
		}
	});
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	/*className = (className === "dark" ? "" : "dark");
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
			+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
	if (newName.length == 0) {
		showInfoMessage("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	}
	return true;*/
	
}
function onRename(e, treeId, treeNode, isCancel) {
	/*var url = "/base/cms/content/update";
	var param = {};
	param.id = treeNode.id;
	param.name = treeNode.name;
	param._csrf=$("input[name='_csrf']").val();
	$.post(url,param,function(data){
		if(data == "succ"){
			showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
					+ " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
					+ (isCancel ? "</span>" : ""));
		}else{
			$("#updateFailedButton").click();
		}
	});*/
	
}
function showRemoveBtn(treeId, treeNode) {
	return treeNode.level != 0;
}
function showRenameBtn(treeId, treeNode) {
	return true;
}
function showLog(str) {
	if (!log)
		log = $("#log");
	log.append("<li class='" + className + "'>" + str + "</li>");
	if (log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			/*var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var count = newCount++;
			var param = {};
			param.name = "new node"+ count;
			param.parent_id = treeNode.id;
			param.client_id = getCLientId();
			param._csrf=$("input[name='_csrf']").val();
			$.ajax({
				url : "/base/cms/content/add",
				type : "put",
				data : param,
				success : function(data){
					zTree.addNodes(treeNode, {
						id : data,
						pId : treeNode.id,
						name : "new node" + count
					});
					return false;
				}
			});
			*/
			catalogueUpdateValidator.resetForm();
			$("#catalogueId").val(null);
			$("#parentId").val(treeNode.id);
			$("#catalogueUpdateShowButton").click();
		});
};

var addOneLevelNode = function(list){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var param = {};
	if(list.length > 0 && getCLientId() != null){
		for(var i=0;i<list.length;i++){
			param.name = list[i];
			param.parent_id = 0;
			param.sort = 0;
			param.client_id = getCLientId();
			param._csrf=$("input[name='_csrf']").val();
			$.ajax({
				url : "/base/cms/content/add",
				async : false,
				type : "put",
				data : param,
				success : function(data){
					zTree.addNodes(null, {
						id : data,
						pId : 0,
						name : list[i],
						open : true
					});
					return false;
				}
			});
		}
	}
}
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

var cataloguaId = null;
var languageId = 1;
function onClick(event, treeId, treeNode){
	cataloguaId = treeNode.id;
	refreshDetailsForm();
	$("#contentDetailsDiv").show();
}

var getCLientId = function(){
	var clientId = $("#client_id").val();
	if(clientId == null || clientId == ""){
		clientId = null;
	}
	return clientId;
}

var refreshTree = function(){
	$("#contentDetailsDiv").hide();
	zNodes = [];
	var clientId = getCLientId();
	if(clientId != null){
		$.get("/base/cms/content/get/"+clientId+"?csrf="+$("input[name='_csrf']").val(),function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(data[i].level == 1){
						zNodes[i] = {id : data[i].id, pId : data[i].parent_id, name : data[i].name, sort : data[i].sort, open : true};
					}else{
						zNodes[i] = {id : data[i].id, pId : data[i].parent_id, name : data[i].name, sort : data[i].sort};
					}
				}
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			}else{
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				addOneLevelNode(["Footer","System","Help"]);
			}
		});
	}else{
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}
	cataloguaId = null;
	languageId = 1;
	validator.resetForm();
	$("#created_by").html("");
	$("#created_on").html("");
	$("#last_updated_by").html("");
	$("#last_updated_on").html("");
	$("[name='id']").val(null);
	$("[name='title']").val(null);
	$("[name='url']").val(null);
	$("#summernote_1").code(null);
	$("[name='meta_title']").val(null);
	$("[name='meta_keyword']").val(null);
	$("#meta_description").val(null);
	$("[name='is_enabled']").val(null);
}
var refreshDetailsForm = function(){
	var url = "/base/cms/content/ContentDetails/get/"+cataloguaId;
	$.get(url,function(data){
		if(data != null && data != ""){
			$("#created_by").html(data.whoCreated);
			$("#created_on").html(data.crateTime);
			$("#last_updated_by").html(data.whoModified);
			$("#last_updated_on").html(data.updateTime);
			url = "/base/cms/content/ContentDetailsContext/get";
			var param = {};
			param.details_id = data.id;
			param.language_id = languageId;
			param._csrf=$("input[name='_csrf']").val();
			$.post(url,param,function(data){
				if(data != null && data != ""){
					$("[name='id']").val(data.id);
					$("[name='title']").val(data.title);
					$("#summernote_1").code(data.content);
					$("[name='meta_title']").val(data.meta_title);
					$("[name='meta_keyword']").val(data.meta_keyword);
					$("#meta_description").val(data.meta_description);
					$("[name='is_enabled']").val(data.is_enabled);
				}else{
					$("[name='id']").val(null);
					$("[name='title']").val(null);
					$("[name='url']").val(null);
					$("#summernote_1").code(null);
					$("[name='meta_title']").val(null);
					$("[name='meta_keyword']").val(null);
					$("#meta_description").val(null);
					$("[name='is_enabled']").val(null);
				}
			});
			if(data.id != null && data.id != ""){
				url = "/base/cms/content/ContentDetailsContext/getEnglishUrl/"+data.id;
				$.get(url,function(data){
					$("#url").val(data);
				});
			}
		}else{
			$("#created_by").html("");
			$("#created_on").html("");
			$("#last_updated_by").html("");
			$("#last_updated_on").html("");
			$("[name='id']").val(null);
			$("[name='title']").val(null);
			$("[name='url']").val(null);
			$("#summernote_1").code(null);
			$("[name='meta_title']").val(null);
			$("[name='meta_keyword']").val(null);
			$("#meta_description").val(null);
			$("[name='is_enabled']").val(null);
		}
	});
}
var showDetails = function(lId){
	languageId = lId;
	validator.resetForm();
	refreshDetailsForm();
}
var catalogueUpdateValidator = $("#catalogueUpdateForm").validate({  
    rules : {  
    	catalogueName : "required",
    	catalogueSort : {required : true}
    },
    messages : {  
    	catalogueName : $("#catalogueNameCheck").html(),
    	catalogueSort : {required : $("#catalogueSortCheck").html()}
    },
    submitHandler : function(){
    	var url = null;
    	var param = {};
    	param.name = $("#catalogueName").val();
    	param.sort = $("#catalogueSort").val();
    	param._csrf=$("input[name='_csrf']").val();
    	if($("#catalogueId").val() == null || $("#catalogueId").val() == ""){
    		url = "/base/cms/content/add";
    		param.client_id = getCLientId();
    		param.parent_id = $("#parentId").val();
    		$.post(url,param,function(data){
    			if(data != null && data != ""){
    				$("#saveCancel").click();
    				$("#addSuccessButton").click();
    				refreshTree();
    			}else{
    				$("#addFailedButton").click();
    			}
    		});
    	}else{
    		url = "/base/cms/content/update";
    		param.id = $("#catalogueId").val();
	    	$.post(url,param,function(data){
	    		if(data == "succ"){
	    			$("#saveCancel").click();
	    			$("#updateSuccessButton").click();
	    			refreshTree();
	    		}else{
	    			$("#updateFailedButton").click();
	    		}
			});
    	}
    }
});
var validator = $("#contentDetailsForm").validate({  
    rules : {  
    	title : "required",
    	url : {required : true},
    	is_enabled : "required"
    },
    messages : {  
    	title : $("#titleCheck").html(),
    	url : {required : $("#urlCheck").html()},
    	is_enabled : $("#isEnabledCheck").html()
    },
    /*errorPlacement: function(error, element) {  
    	$(element.parent().parent()).addClass("has-error");
        error.appendTo(element.parent());  
    },
    success : function(element){
    	$(element.parent().parent()).removeClass("has-error");
    },
    errorClass:"help-block",*/
    submitHandler : function(){
    	if(cataloguaId == null){
    		showInfoMessage($("#catalogueCheck").html());
    		return;
    	}
    	var content = $("#summernote_1").code();
    	if(content == null || content == ""){
    		$("#contentCheck").show();
    		return;
    	}else{
    		$("#contentCheck").hide();
    	}
    	var url = "/base/cms/content/save";
    	var param = {};
    	param.catalogue_id = cataloguaId;
    	param.language_id = languageId;
    	param.title = $("[name='title']").val();
    	param.url = $("[name='url']").val();
    	param.content = $("#summernote_1").code();
    	param.meta_title = $("[name='meta_title']").val();
    	param.meta_keyword = $("[name='meta_keyword']").val();
    	param.meta_description = $("#meta_description").val();
    	param.is_enabled = $("[name='is_enabled']").val();
    	param._csrf=$("input[name='_csrf']").val();
    	$.post(url,param,function(data){
    		if(data.status == 1){
    			showInfoMessage("内容连接在同一站点里重复");
    		}
    		if(data.status == 0){
    			$("#addSuccessButton").click();
    		}else{
    			$("#addFailedButton").click();
    		}
    	});
    }
});
$(document).ready(function() {
	refreshTree();
	$("#dataSearch").click(function(){
		refreshTree();
	});
	$("#selectAll").bind("click", selectAll);
	$("#deleteConfirmButton").click(function(){
		$("#deleteCancelButton").click();
		onRemove(this.event,ctreeId,ctreeNode);
	});
	$("#client_id").change(function(){
		refreshTree();
	});
	$("#title").blur(function(){
		var allReg = new RegExp("((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&* ])|(?=.*\d)(?=.*[#@!~%^&* ]))[a-z\d#@!~%^&* ]+", "g");
		if($("#title").val() != null && $("#title").val() != "" && allReg.test($("#title").val())){
			var reg = new RegExp("[^a-zA-Z0-9]+", "g");
			var url = "";
			if($("#title").val().indexOf(" ") != -1){
				var vals = $("#title").val().split(" ");
				for(var i=0;i<vals.length;i++){
					url += "-"+vals[i].replace(reg,"-");
				}
			}else{
				url += "-"+$("#title").val().replace(reg,"-");
			}
			$("#url").val(url.substring(1));
		}
	});
});