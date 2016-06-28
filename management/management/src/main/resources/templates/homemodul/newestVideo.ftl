<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/indexConfig"><@my.message "page.indexConfig"/></a>
        </li>
        <li>
            <a href="/indexConfig/customerVoice">Customer Voice</a>
        </li>
        <li class="active"><@my.message "base.newestVideo.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.newestVideo.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "base.newestVideo.client"/></label>
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
		                            <label class="control-label col-md-3"><@my.message "base.newestVideo.language"/></label>
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
		                            <label class="control-label col-md-4"><@my.message "base.newestVideo.createBy"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="video_by" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.newestVideo.country"/></label>
		                            <div class="col-md-8">
		                                <select id="country" class="form-control">
		                                	<option></option>
		                                    <#list countryList as country>
		                                    	<option value=${country.name}>${country.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                </div>
		                <div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3">SKU</label>
		                            <div class="col-md-9">
		                                <input type="text" id="sku" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="row">
			                    <div class="col-md-1 text-right">
	                                <button type="button" id="dataSearch" class="btn green "><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "base.newestVideo.listTitle"/></span>
                </div>
                <div class="actions">
                	<div class="row">
                        <div class="col-md-12">
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
	                            <th><@my.message "base.newestVideo.client"/></th>
	                            <th><@my.message "base.newestVideo.language"/></th>
	                            <th><@my.message "base.newestVideo.videoTitle"/></th>
	                            <th>SKU</th>
	                            <th><@my.message "base.newestVideo.url"/></th>
	                            <th><@my.message "base.newestVideo.createBy"/></th>
	                            <th><@my.message "base.newestVideo.country"/></th>
	                            <th><@my.message "base.newestVideo.isEnabled"/></th>
	                            <th><@my.message "base.newestVideo.createuser"/></th>
	                            <th><@my.message "base.newestVideo.createdate"/></th>
	                            <th><@my.message "base.newestVideo.updateuser"/></th>
	                            <th><@my.message "base.newestVideo.updatedate"/></th>
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
 
<div id="newestVideoDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "base.newestVideo.formTitle"/></h4>
            </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="newestVideoForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.client"/><span class="required" aria-required="true"> * </span></label>
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
	                                <span id="newestVideoClientCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.newestVideo.clientCheck"/></span> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.language"/><span class="required" aria-required="true"> * </span></label>
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
	                                <span id="newestVideoLanguageCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.newestVideo.languageCheck"/></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.videoTitle"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="title" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">SKU<span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="sku" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.url"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="video_url" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.createBy"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="video_by" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.country"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="country" class="form-control">
                                	<option></option>
                                    <#list countryList as country>
                                    	<option value=${country.name}>${country.name}</option>
                                    </#list>
                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.newestVideo.isEnabled"/></label>
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
	<div id="repeatNewestVideoAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.newestVideo.newestVideoCheck"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#newestVideoDiv" id="newestVideoShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#repeatNewestVideoAlert" id="repeatNewestVideoShowButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="clientCheck" class="hide"><@my.message "base.newestVideo.clientCheck"/></span>
	<span id="languageCheck" class="hide"><@my.message "base.newestVideo.languageCheck"/></span>
	<span id="videoTitleCheck" class="hide"><@my.message "base.newestVideo.videoTitleCheck"/></span>
	<span id="skuCheck" class="hide"><@my.message "base.newestVideo.skuCheck"/></span>
	<span id="urlCheck" class="hide"><@my.message "base.newestVideo.urlCheck"/></span>
	<span id="urlFormatCheck" class="hide"><@my.message "base.newestVideo.urlFormatCheck"/></span>
	<span id="createByCheck" class="hide"><@my.message "base.newestVideo.createByCheck"/></span>
	<span id="countryCheck" class="hide"><@my.message "base.newestVideo.countryCheck"/></span>
	<span id="skuIsNotAvailiableCkeck" class="hide"><@my.message "base.newestVideo.skuIsNotAvailiableCkeck"/></span>
</div>
<script src="/js/homemodul/newestVideo.js" type="text/javascript"></script>