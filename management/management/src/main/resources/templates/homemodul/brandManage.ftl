<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/indexConfig"><@my.message "page.indexConfig"/></a>
        </li>
        <li class="active"><@my.message "base.brand.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.brand.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "base.brand.client"/></label>
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
		                            <label class="control-label col-md-3"><@my.message "base.brand.language"/></label>
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
		                            <label class="control-label col-md-4"><@my.message "base.brand.name"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="name" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.brand.code"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="code" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                </div>
		                <div class="row">
		                    <div class="col-md-12 text-right">
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
                    <span class="caption-subject bold uppercase"><@my.message "base.brand.listTitle"/></span>
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
	                            <th><@my.message "base.brand.client"/></th>
	                            <th><@my.message "base.brand.language"/></th>
	                            <th><@my.message "base.brand.name"/></th>
	                            <th><@my.message "base.brand.code"/></th>
	                            <th><@my.message "base.brand.logo"/></th>
	                            <th><@my.message "base.brand.url"/></th>
	                            <th><@my.message "base.brand.description"/></th>
	                            <th><@my.message "base.brand.isEnabled"/></th>
	                            <th><@my.message "base.brand.createuser"/></th>
	                            <th><@my.message "base.brand.createdate"/></th>
	                            <th><@my.message "base.brand.updateuser"/></th>
	                            <th><@my.message "base.brand.updatedate"/></th>
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
 
<div id="brandDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "base.brand.formTitle"/></h4>
            </div>
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="imgUploadForm" action="/image/upload" method="post" enctype="multipart/form-data" class="form-horizontal cmxform">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-body">
	                        <input type="hidden" name="token" value="${imageUploadToken}">
	                        <div class="form-group">
	                            <label class="col-md-3 control-label"><@my.message "base.brand.logo"/></label>
	                            <div class="col-md-9">
	                            	<img id="bImgSrc" width="50%" src=""/>
	                            	<input type="file" name="file">
	                        		<label id="logoCheck" class="error" style="display:none;"><@my.message "base.brand.logoCheck"/></label>
	                            </div>
	                        </div>
                        </div>
                    </form>
                    <form id="brandForm" class="form-horizontal cmxform" role="form" method="post" enctype="multipart/form-data">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                    	<input type="hidden" name="logo_url" id="imgUrl">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.client"/><span class="required" aria-required="true"> * </span></label>
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
	                                <span id="brandClientCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.brand.clientCheck"/></span> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.language"/><span class="required" aria-required="true"> * </span></label>
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
	                                <span id="brandLanguageCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.brand.languageCheck"/></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="name" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.code"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="code" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.url"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="url" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.isEnabled"/></label>
                                <div class="col-md-9">
                                    <select name="is_enabled" class="form-control">
                                    	<option></option>
	                                	<option value="1"><@my.message "page.enabled"/></option>
	                                    <option value="0"><@my.message "page.disabled"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.brand.description"/></label>
                                <div class="col-md-9">
                                    <textarea id="description" name="description" cols="55" rows="5"></textarea>
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
	<div id="repeatBrandAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.brand.brandCheck"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#brandDiv" id="brandShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#repeatBrandAlert" id="repeatBrandShowButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="clientCheck" class="hide"><@my.message "base.brand.clientCheck"/></span>
	<span id="languageCheck" class="hide"><@my.message "base.brand.languageCheck"/></span>
	<span id="nameCheck" class="hide"><@my.message "base.brand.nameCheck"/></span>
	<span id="nameRepeatCheck" class="hide"><@my.message "base.brand.nameRepeatCheck"/></span>
	<span id="codeCheck" class="hide"><@my.message "base.brand.codeCheck"/></span>
	<span id="logoCheck" class="hide"><@my.message "base.brand.logoCheck"/></span>
</div>
<script src="/js/homemodul/brandManage.js" type="text/javascript"></script>