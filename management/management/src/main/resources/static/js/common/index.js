function init(){
	getLoginUser();
}
function getLoginUser(){
	
	var url = "/login/user?csrf="+$("input[name='_csrf']").val();
	$.get(url,function(data){
		if($.trim(data)==""){
			$("#baseModeMenu").remove();
			$("#indexSettingMenu").remove();
			$("#oldWebUrlDiv").remove();
			$("#authorityConfigMenu").remove();
			$("#systemConfigMenu").remove();
			$("#marketMenu").remove();
			return;
		}
		if($.trim(data.baseMode)==""){
			$("#baseModeMenu").remove();
		}else{
			$("#baseModeMenu").show();
			$("#loginUserInfo").text(data.loginUser);
			var baseParams=data && data.baseMode!=null?data.baseMode.childMenuMap:null;
			$("#baseMode").text(data && data.baseMode && data.baseMode.currentMenu!=null?data.baseMode.currentMenu.menuName:"");
			menuLevel("baseDesign","010200", baseParams);
			menuLevel("localId","010100", baseParams);
			menuLevel("cms","010300", baseParams);
		}
		if($.trim(data.indexSet)==""){
			$("#indexSettingMenu").remove();
		}else{
			$("#indexSettingMenu").show();
			var indexParams=data && data.indexSet!=null?data.indexSet.childMenuMap:null;
			$("#indexSetting").text(data && data.indexSet && data.indexSet.currentMenu!=null?data.indexSet.currentMenu.menuName:"");
			menuLevel("searchFunctionId","020100", indexParams);
			menuLevel("productPromotions","020200", indexParams);
			menuLevel("brandManage","020300",indexParams);
			menuLevel("featuredCategory","020400",indexParams);
			menuLevel("recentlySold","020500",indexParams);
			menuLevel("customerVoice","020600",indexParams);	
		}
		
		if($.trim(data.oldWebUrl)!=""){
			$("#oldWebUrlDiv").show();
			$("#oldWebSite").parent().attr("href",data.oldWebUrl);
		}
		if($.trim(data.authoritySet)==""){
			$("#authorityConfigMenu").remove();
		}else{
			$("#authorityConfigMenu").show();
			var indexParams=data && data.authoritySet!=null?data.authoritySet.childMenuMap:null;
			$("#authorityConfig").text(data && data.authoritySet && data.authoritySet.currentMenu!=null?data.authoritySet.currentMenu.menuName:"");
			menuLevel("userConfig","030100", indexParams);
			menuLevel("roleManager","030200", indexParams);
			menuLevel("roleMenuManager","030300", indexParams);
		}
		if($.trim(data.systemSet)==""){
			$("#systemConfigMenu").remove();
		}else{
			var indexParams=data && data.systemSet!=null?data.systemSet.childMenuMap:null;
			$("#systemManage").text(data && data.systemSet && data.systemSet.currentMenu!=null?data.systemSet.currentMenu.menuName:"");
			menuLevel("freightManage","040100", indexParams);
			$("#systemConfigMenu").show();
		}
		
		if($.trim(data.market)==""){
			$("#marketMenu").remove();
		}else{
			$("#marketMenu").show();
			var indexParams=data && data.market!=null?data.market.childMenuMap:null;
			$("#dataManage").text(data && data.market && data.market.currentMenu!=null?data.market.currentMenu.menuName:"");
			menuLevel("datafeed","050100", indexParams);
		}
	});
}
function menuLevel(targetId,id,data){
	if($.trim(targetId)=="" || $.trim(id)=="" ){
		return ;
	}
	if($.trim(data)=="" || data[id]==null){
		$("#"+targetId).remove();
		return;
	}
	$("#"+targetId+" a").attr("href",data[id].currentMenu.menuUrl);
	$("#"+targetId+" span").text(data[id].currentMenu.menuName);
	if($.trim(data[id].childMenuMap)!=""){
		var append="";
		$.each(data[id].childMenuMap,function(index,value){
			append=append+"<a href="+value.currentMenu.menuUrl+">"+value.currentMenu.menuName+"</a>";
		})
		$("#"+targetId+" ul li").append(append);
	}
}
function listem(){
	$("#logoutId").click(function() {
		$("#logoutBt").click();
	});
	$("#oldButton").click(function() {
		if($.trim($("#oldWebSite").attr("href"))=="" && $.trim($("#oldWebSite").attr("href"))=="#" ){
			alert("get oldWebSite address fail");
			return;
		}
		$("#oldWebSite").click();
	});
	
}
function stringShow(value){
	if(value == null){
		return "";
	}else{
		return value;
	}
}
function changeSite(siteId){
	var currentUrl = window.location.href;
	currentUrl = currentUrl.substring(currentUrl.indexOf("/",currentUrl.indexOf("/")+2));
	var url = "/index/changeSite?url="+currentUrl+"&siteId="+siteId;
	window.location=url;
}
$(function() {
	init();
	listem();
	//get site
	var url = "/login/getSiteByUser?csrf="+$("input[name='_csrf']").val();
	$.get(url,function(data){
		var tli = "";
		var li = "";
		for(var i=0;i<data.length;i++){
			if(data[i].name == "www.tomtop.com"){
				tli += "<li><a href='javascript:;' onclick='changeSite("+data[i].id+")'>"+data[i].name+"</a></li>";
			}else{
				li += "<li><a href='javascript:;' onclick='changeSite("+data[i].id+")'>"+data[i].name+"</a></li>";
			}
		}
		$("#indexSiteUl").append(tli);
		$("#indexBrandSiteUl").append(li);
	});
	
	$.get("/base/site/getCurrentSite?csrf="+$("input[name='_csrf']").val(),function(data){
		$("#currentSite").html(data.name);
	});
});