String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length){
		return false;
	}
	if (this.substring(this.length - s.length) == s){
		return true;
	}else{
		return false;
	}
	return true;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}

var defaultSwitch = function(){
	var drops = $(".dropdown-fw");
	for (var i = 0; i < drops.length; i++) {
		if($(drops[i]).hasClass("open")){
			return;
		}
	}
	$(drops[0]).addClass("open");
}

var menuSwitch = function(index) {
	var drops = $(".dropdown-fw");
	for (var i = 0; i < drops.length; i++) {
		if (i == index) {
			if (!$(drops[i]).hasClass("open")) {
				$(drops[i]).addClass("open");
			}
		} else {
			if ($(drops[i]).hasClass("open")) {
				$(drops[i]).removeClass("open");
			}
		}
	}
}

$(function() {
	defaultSwitch();
	if($("#sectionId")){
		var sectionId = $("#sectionId").val();
		if(typeof(sectionId) != "undefined"){
			if (sectionId.startWith("baseModul")) {
				menuSwitch(0);
			}
			if (sectionId.startWith("homemodul")) {
				menuSwitch(1);
			}
			if (sectionId.startWith("authority")) {
				menuSwitch(2);
			}
			if (sectionId.startWith("shipping")) {
				menuSwitch(3);
			}
			if(sectionId.startWith("market")) {
				menuSwitch(4);
			}
		}
	}
	var currentUrl = window.location.href;
	currentUrl = currentUrl.substring(currentUrl.indexOf("/",currentUrl.indexOf("/")+2));
	if(currentUrl.startWith("/home") || currentUrl.startWith("/base")){
		menuSwitch(0);
	}
	if(currentUrl.startWith("/indexConfig") || currentUrl.startWith("/homepage")){
		menuSwitch(1);
	}
	if(currentUrl.startWith("/authority")){
		menuSwitch(2);
	}
	if(currentUrl.startWith("/shipping") || currentUrl.startWith("/systemConfig")){
		menuSwitch(3);
	}
	if(currentUrl.startWith("/market")){
		menuSwitch(4);
	}
});