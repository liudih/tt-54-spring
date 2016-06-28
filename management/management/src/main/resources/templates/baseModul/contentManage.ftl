<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="#"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="#"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="#">CMS</a>
        </li>
        <li class="active"><@my.message "base.content.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.content.searchTitle"/>
		        </div>
		        <div class="tools">
                    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                </div>
		    </div>
			<div class="portlet-body form">
		        <!-- BEGIN FORM-->
		        <form action="#" class="form-horizontal">
		            <div class="form-body">
		                <div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.client"/></label>
		                            <div class="col-md-9">
		                                <select id="client_id" class="form-control">
		                                    <#list clientList as client>
		                                    	<option value=${client.id}>${client.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="row">
		                            <div class="col-md-offset-3 col-md-9">
		                                <button type="button" class="btn green" id="dataSearch"><@my.message "page.search"/></button>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </form>
		        <!-- END FORM-->
		    </div>
		</div> 
	</div>
</div>
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN EXAMPLE TABLE PORTLET-->
        <div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption font-dark">
                    <span class="caption-subject bold uppercase"><@my.message "base.content.navigation"/></span>
                </div>
            </div>
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-3"><ul id="treeDemo" class="ztree"></ul></div>
                    <div id="contentDetailsDiv" class="col-md-9" style="display:none;">
                        <div class="cms_box_01">
                            <div class="tabbable-custom ">
                                <ul class="nav nav-tabs ">
                                	<#list languageList as language>
                                    	<#if language.id == 1>
                                    		<li class="active">
		                                        <a aria-expanded="true" href="#cdc" data-toggle="tab" onclick="showDetails(${language.id})">${language.name}</a>
		                                    </li>
                                    	<#else>
	                                    	<li class="">
		                                        <a aria-expanded="false" href="#cdc" data-toggle="tab" onclick="showDetails(${language.id})">${language.name}</a>
	                                        </li>
                                        </#if>
                                    </#list>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="cdc">
                                        <div class="portlet-body">
                                            <form id="contentDetailsForm" action="#" class="form-horizontal">
                                            	<input type="hidden" name="id">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2"><@my.message "base.content.title"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-10">
                                                            <input id="title" type="text" name="title" class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2"><@my.message "base.content.url"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-10">
                                                            <input id="url" type="text" name="url" readonly=true class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2"><@my.message "base.content.content"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-10">
                                                            <div name="summernote" id="summernote_1"> </div>
                                                            <span id="contentCheck" class="error" style="display:none;color:red;"><@my.message "base.content.contentCheck"/></span> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2">Meta Title</label>
                                                        <div class="col-md-10">
                                                            <input type="text" name="meta_title" class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2">Meta Keyword</label>
                                                        <div class="col-md-10">
                                                            <input type="text" name="meta_keyword" class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2">Meta Description</label>
                                                        <div class="col-md-10">
                                                            <textarea class="form-control" id="meta_description" name="meta_description" rows="3" ></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-2"><@my.message "page.isEnabled"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-10">
                                                            <select name="is_enabled" class="form-control error" aria-required="true">
                                                                <option></option>
                                                                <option value="1"><@my.message "page.enabled"/></option>
                                                                <option value="0"><@my.message "page.disabled"/></option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="cms_con">
                                                       <@my.message "page.createuser"/>：<span id="created_by"></span>
                                                       <@my.message "page.createdate"/>：<span id="created_on"></span>
                                                       <@my.message "page.updateuser"/>：<span id="last_updated_by"></span>
                                                       <@my.message "page.updatedate"/>：<span id="last_updated_on"></span>
                                                       <button class="btn sbold green" type="submit"><i class="fa fa-save"></i></button></p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>
<div id="catalogueUpdateDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title"><@my.message "base.content.catalogueForm"/></h4>
	        </div>
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="catalogueUpdateForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" id="catalogueId">
                    	<input type="hidden" id="parentId">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.content.catalogueName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" id="catalogueName" name="catalogueName" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.content.catalogueSort"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="number" id="catalogueSort" name="catalogueSort" class="form-control"> 
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="saveCancel" class="btn default" data-dismiss="modal"><@my.message "page.cancel"/></button>
                            <button type="submit" id="save" class="btn green"><@my.message "page.save"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
	<a href="#catalogueUpdateDiv" id="catalogueUpdateShowButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="titleCheck" class="hide"><@my.message "base.content.titleCheck"/></span>
	<span id="urlCheck" class="hide"><@my.message "base.content.urlCheck"/></span>
	<span id="urlFormatCheck" class="hide"><@my.message "base.content.urlFormatCheck"/></span>
	<span id="isEnabledCheck" class="hide"><@my.message "base.content.isEnabledCheck"/></span>
	<span id="catalogueCheck" class="hide"><@my.message "base.content.catalogueCheck"/></span>
	<span id="catalogueNameCheck" class="hide"><@my.message "base.content.catalogueNameCheck"/></span>
	<span id="catalogueSortCheck" class="hide"><@my.message "base.content.catalogueSortCheck"/></span>
</div>

<link href="${styleurl}/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
<link href="${styleurl}/global/css/zTreeStyle.css" rel="stylesheet" type="text/css">
<script src="${styleurl}/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="${styleurl}/global/plugins/zTree/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="${styleurl}/global/plugins/zTree/jquery.ztree.exedit.min.js" type="text/javascript"></script>
<script src="${styleurl}/pages/scripts/components-editors.min.js" type="text/javascript"></script>
<script src="/js/baseModul/contentManage.js" type="text/javascript"></script>