<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/base"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="/base/baseLocalize"><@my.message "page.localize"/></a>
        </li>
        <li class="active"><@my.message "base.resource.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.resource.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "base.resource.client"/></label>
		                            <div class="col-md-9">
		                                <select id="client_id" class="bs-select form-control" multiple>
		                                    <#list clientList as client>
		                                    	<option value=${client.id}>${client.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.resource.language"/></label>
		                            <div class="col-md-9">
		                                <select id="language_id" class="bs-select form-control" multiple>
		                                    <#list languageList as language>
		                                    	<option value=${language.id}>${language.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.resource.key"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="key" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.resource.value"/></label>
		                            <div class="col-md-9">
		                                <input type="text" id="value" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                </div>
		                <div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.resource.isEnabled"/></label>
		                            <div class="col-md-9">
		                                <select id="is_enabled" class="form-control">
		                                	<option></option>
		                                    <option value="1"><@my.message "page.enabled"/></option>
		                                    <option value="0"><@my.message "page.disabled"/></option>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-1 text-right">
                                <button type="button" id="dataSearch" class="btn green "><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "base.resource.listTitle"/></span>
                </div>
                <div class="actions">
                	<div class="row">
                        <div class="col-md-12">
                            <div class="btn-group">
                                <button id="downloadButton" class="btn sbold blue"><i class="fa fa-download"></i>
                                </button>
                            </div>
                            <div class="btn-group">
                                <button id="addButton" class="btn sbold green"><i class="fa fa-plus"></i>
                                </button>
                            </div>
                            <div class="btn-group">
                            	<button id="deleteButton" class="btn sbold red"><i class="fa fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
            	<div class="table-scrollable">
	                <table class="table table-striped table-bordered table-hover table-checkable order-column" id="dataTable">
	                    <thead>
	                        <tr>
	                            <th><input type="checkbox" class="group-checkable" id="mainCheckbox" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th><@my.message "base.resource.client"/></th>
	                            <th><@my.message "base.resource.language"/></th>
	                            <th><@my.message "base.resource.key"/></th>
	                            <th><@my.message "base.resource.value"/></th>
	                            <th><@my.message "base.resource.isEnabled"/></th>
	                            <th><@my.message "base.resource.createuser"/></th>
	                            <th><@my.message "base.resource.createdate"/></th>
	                            <th><@my.message "base.resource.updateuser"/></th>
	                            <th><@my.message "base.resource.updatedate"/></th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                </table>
	            </div>
	            <#include "/page.ftl">
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>
 
<div id="resourceDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title"><@my.message "base.resource.formTitle"/></h4>
	        </div>
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="resourceForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.resource.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="client_id_add" class="bs-select form-control" multiple>
	                                    <#list clientList as client>
	                                    	<option value=${client.id}>${client.name}</option>
	                                    </#list>
	                                </select>
	                                <select name="client_id" class="form-control">
	                                	<option></option>
	                                    <#list clientList as client>
	                                    	<option value=${client.id}>${client.name}</option>
	                                    </#list>
	                                </select>
	                                <span id="resourceClientCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.resource.clientCheck"/></span> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.resource.language"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="language_id_add" class="bs-select form-control" multiple>
	                                    <#list languageList as language>
	                                    	<option value=${language.id}>${language.name}</option>
	                                    </#list>
	                                </select>
	                                <select name="language_id" class="form-control">
	                                	<option></option>
	                                    <#list languageList as language>
	                                    	<option value=${language.id}>${language.name}</option>
	                                    </#list>
	                                </select>
	                                <span id="resourceLanguageCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.resource.languageCheck"/></span> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.resource.key"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="key" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.resource.value"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="value" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.resource.isEnabled"/></label>
                                <div class="col-md-9">
                                    <select name="is_enabled" class="form-control">
                                    	<option></option>
	                                	<option value="1"><@my.message "page.enabled"/></option>
	                                    <option value="0"><@my.message "page.disabled"/></option>
	                                </select>
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
	<div id="downloadOperateAlert" class="modal fade" style="margin:auto;width:600px;height:200px" data-backdrop="static">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <div class="form-group">
                <label class="col-md-3 control-label"><@my.message "base.resource.fileType"/></label>
                <div class="col-md-9">
                    <select id="fileType" class="form-control">
                    	<option value="0">APP-Android(.xml)</option>
                        <option value="1">APP-IOS(.txt)</option>
                    </select> 
                </div>
            </div>
            <p>
                <a class="btn purple" id="downloadConfirmButton" href="javascript:;"><@my.message "page.confirm"/></a>
                <a class="btn dark" id="downloadCancelButton" href="javascript:;" data-dismiss="modal"><@my.message "page.cancel"/></a>
            </p>
        </div>
    </div>
    <div id="repeatKeyAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.resource.keyRepeat"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#resourceDiv" id="resourceShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#downloadOperateAlert" id="downloadShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#repeatKeyAlert" id="repeatKeyButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="clientCheck" class="hide"><@my.message "base.resource.clientCheck"/></span>
	<span id="languageCheck" class="hide"><@my.message "base.resource.languageCheck"/></span>
	<span id="keyCheck" class="hide"><@my.message "base.resource.keyCheck"/></span>
	<span id="keyRepeatCheck" class="hide"><@my.message "base.resource.keyRepeatCheck"/></span>
	<span id="valueCheck" class="hide"><@my.message "base.resource.valueCheck"/></span>
</div>
<script src="/js/baseModul/resourceManage.js" type="text/javascript"></script>