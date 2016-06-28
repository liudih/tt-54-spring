$(function() {

	var temp = null;
	function createCode() {
		$("#img_v_id").attr("src", '');
		$("#img_v_id").attr("src", '/public/captchaManage');
	}

	$("#login_button_id").click(function(){
		var name = $("input[name='jobNumber']").val();
		var pwd = $("input[name='password']").val();
		var code = $("input[name='v']").val();
		$("#new_addCode_row").remove();
		$("#new_addNamePwd_row").remove();
		if(name=="" || pwd==""){
			var newRow='<div id="new_addNamePwd_row" class="alert alert-danger display-hide" style="display: block;">';
			newRow+='<button class="close" data-close="alert"></button>';
			newRow+='<span> Enter any username and password .. </span>';
			newRow+=' </div>';
			$("#hhh_id").after(newRow);
		}else if(code==""){
			var newRow='<div  id="new_addCode_row" class="alert alert-danger display-hide" style="display: block;">';
			newRow+='<button class="close" data-close="alert"></button>';
			newRow+='<span> Enter any verification code .. </span>';
			newRow+=' </div>';
			$("#hhh_id").after(newRow);
		}else{
			$("#login_submit_id").click();
		}
	});
});
