<div>
	<div id="addSuccessAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-success fade in">
		    <button type="button" class="close" data-dismiss="modal"></button>
		    <h4 class="alert-heading"><@my.message "page.tip"/></h4>
		    <p><@my.message "page.save.succ"/></p>
		</div>
	</div>
	<div id="addFailedAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-danger fade in">
	        <button type="button" class="close" data-dismiss="modal"></button>
	        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
	        <p><@my.message "page.save.failed"/></p>
	    </div>
    </div>
    <div id="updateSuccessAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-success fade in">
		    <button type="button" class="close" data-dismiss="modal"></button>
		    <h4 class="alert-heading"><@my.message "page.tip"/></h4>
		    <p><@my.message "page.update.succ"/></p>
		</div>
	</div>
	<div id="updateFailedAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-danger fade in">
	        <button type="button" class="close" data-dismiss="modal"></button>
	        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
	        <p><@my.message "page.update.failed"/></p>
	    </div>
    </div>
    <div id="deleteSuccessAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-success fade in">
		    <button type="button" class="close" data-dismiss="modal"></button>
		    <h4 class="alert-heading"><@my.message "page.tip"/></h4>
		    <p><@my.message "page.delete.succ"/></p>
		</div>
	</div>
	<div id="deleteFailedAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-danger fade in">
	        <button type="button" class="close" data-dismiss="modal"></button>
	        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
	        <p><@my.message "page.delete.failed"/></p>
	    </div>
    </div>
    <div id="deleteOperateAlert" class="modal fade" style="margin:auto;width:600px;height:200px" data-backdrop="static">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "page.confirmDelete"/></p>
            <p>
                <a class="btn purple" id="deleteConfirmButton" href="javascript:;"><@my.message "page.confirm"/></a>
                <a class="btn dark" id="deleteCancelButton" href="javascript:;" data-dismiss="modal"><@my.message "page.cancel"/></a>
            </p>
        </div>
    </div>
    <div id="noDeleteAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "page.noDeleteItem"/></p>
        </div>
    </div>
</div>
<div>
	<span id="commonEditButton" class="hide"><@my.message "page.update"/></span>
	<span id="commonAll" class="hide"><@my.message "page.all"/></span>
</div>
<div id="delCheckMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "base.site.delCheckMess"/></h4>
        <p><@my.message "page.delete.failed"/></p>
    </div>
</div>
    <div id="importKeyMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "homepage.complete.synchronized.succ"/></h4>
        </div>
    </div>
    <div id="mcOperateTipAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "page.confirmDelete"/></p>
            <p>
                <a class="btn purple" id="mcMCDeleteButton" href="javascript:;"><@my.message "page.confirm"/></a>
                <a class="btn dark" id="mcMCDeleteCancelButton" href="javascript:;" data-dismiss="modal"><@my.message "page.cancel"/></a>
            </p>
        </div>
    </div>
    <div id="checkMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "homepage.complete.check.mess"/></p>
        </div>
    </div>
    <div id="checkRecommendMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.recommend.checkmessage"/></p>
        </div>
    </div>
    <div id="checkClientMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.client.checkmess"/></p>
        </div>
    </div>
    <div id="checkHotkeyMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "homepage.hotsearch.checkmess"/></p>
        </div>
    </div>
    <div id="checkRecentMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "homepage.recent.checksku"/></p>
        </div>
    </div>
    <div id="checkRecentCountryMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "homepage.recent.checkcountry"/></p>
        </div>
    </div>
    <div id="checkTypeMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "shipping.checkName"/></p>
        </div>
    </div>
    <div id="uploadMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "homepage.complete.import.succ"/></h4>
        </div>
    </div>
    <div id="imageUploadFailedAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
		<div class="alert alert-block alert-danger fade in">
	        <button type="button" class="close" data-dismiss="modal"></button>
	        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
	        <p><@my.message "page.image.upload.failed"/></p>
	    </div>
    </div>
    <div id="infoMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p id="infoMessageContent"></p>
        </div>
    </div>
<div>
	<a href="#addSuccessAlert" id="addSuccessButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#addFailedAlert" id="addFailedButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#updateSuccessAlert" id="updateSuccessButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#updateFailedAlert" id="updateFailedButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#deleteSuccessAlert" id="deleteSuccessButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#deleteFailedAlert" id="deleteFailedButton" class="btn hide" data-toggle="modal"></a>
	<a href="#deleteOperateAlert" id="deleteOperateButton" class="btn hide" data-toggle="modal"></a>
	<a href="#noDeleteAlert" id="noDeleteButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#mcOperateMessageAlert" onclick="closeBtn(this)" id="mcOperateMassageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkMessage" onclick="closeBtn(this)" id="checkMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#delCheckMessage" onclick="closeBtn(this)" id="delCheckMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#importKeyMessage" id="importKeyMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#mcOperateTipAlert"  id="mcOperateTipButton" class="btn hide" data-toggle="modal"></a>
    <a href="#uploadMessage" id="uploadMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#delCheckMessage" onclick="closeBtn(this)" id="delCheckMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#imageUploadFailedAlert" id="imageUploadFailedButton" onclick="closeBtn(this)" style="z-index:100;" class="btn hide" data-toggle="modal"></a>
    <a href="#checkRecommendMessage" onclick="closeBtn(this)" id="checkRecommendMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkClientMessage" id="checkClientMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkHotkeyMessage" id="checkHotkeyMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkRecentMessage" id="checkRecentMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkRecentCountryMessage" id="checkRecentCountryMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#checkTypeMessage" id="checkTypeMessageButton" class="btn hide" data-toggle="modal"></a>
    <a href="#infoMessage" id="infoMessageButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>

<script type="text/javascript">
	function closeBtn(e){
		var divId = $(e).attr("href");
		setTimeout(function(){
			$(divId+" .close").click();
		},1500);  
	}
	function showInfoMessage(content){
		$("#infoMessageContent").html(content);
		$("#infoMessageButton").click();
	}
</script>
