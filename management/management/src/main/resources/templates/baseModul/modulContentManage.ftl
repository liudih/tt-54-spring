<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="#"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="#"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="#"><@my.message "page.baseDesign"/></a>
        </li>
        <li class="active"><@my.message "base.modulContent.navigation"/></li>
    </ol>
</div>
<div class="row">
	    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <i class="fa fa-gift"></i><@my.message "base.modulContent.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.client"/></label>
		                            <div class="col-md-9">
		                                <select id="client_id" class="form-control">
		                                	<option></option>
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
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.language"/></label>
		                            <div class="col-md-9">
		                                <select id="language_id" class="form-control">
		                                	<option></option>
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
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.layoutName"/></label>
		                            <div class="col-md-9">
		                                <select id="layout_id" class="form-control">
		                                	<option></option>
		                                    <#list layoutList as layout>
		                                    	<option value=${layout.id}>${layout.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.category"/></label>
		                            <div class="col-md-9">
		                                <select id="firstCategory" class="form-control" style="width:31%;display:inline">
		                                	<option></option>
		                                    <#list rootCategoryList as rootCategory>
		                                    	<option value=${rootCategory.iid}>${rootCategory.cpath}</option>
		                                    </#list>
		                                </select>
		                                <select id="secondCategory" class="form-control" style="width:31%;display:inline">
		                                </select>
		                                <select id="thirdCategory" class="form-control" style="width:31%;display:inline">
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                </div>
		                <!--/row-->
		                <div class="row">
		                     <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.moduleName"/></label>
		                            <div class="col-md-9">
		                                <select id="layout_module_id" class="form-control">
		                                	<option></option>
		                                    <#list loyoutModulList as loyoutModul>
		                                    	<option value=${loyoutModul.id}>${loyoutModul.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                     <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.sku"/></label>
		                            <div class="col-md-9">
		                            	<input type="text" id="sku" class="form-control" maxlength="30">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="row">
		                            <div class="col-md-offset-3 col-md-9">
		                                <button type="button" class="btn green" id="mcSearch"><@my.message "page.search"/></button>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		                <!--/row-->
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
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject bold uppercase"><@my.message "base.modulContent.listTitle"/></span>
                </div>
                <div class="actions">
	            	<div class="row">
	                    <div class="col-md-12">
	                        <div class="btn-group">
	                            <button id="mcAddMCButton" class="btn sbold green"><i class="fa fa-plus"></i>
	                            </button>
	                        </div>
	                        <div class="btn-group">
	                        	<button id="mcDeleteMCButton" class="btn sbold red"><i class="fa fa-trash"></i>
	                            </button>
	                        </div>
	                    </div>
	                </div>
            	</div>
            </div>
            <div class="portlet-body">
            	<div class="table-scrollable">
	                <table class="table table-striped table-bordered table-hover" id="mcTable">
	                    <thead>
	                        <tr>
	                            <th><input type="checkbox" class="group-checkable" id="mcMainCheckbox" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th><@my.message "base.modulContent.client"/></th>
	                            <th><@my.message "base.modulContent.language"/></th>
	                            <th><@my.message "base.modulContent.layoutName"/></th>
	                            <th><@my.message "base.modulContent.moduleName"/></th>
	                            <th><@my.message "base.modulContent.isShow"/></th>
	                            <th><@my.message "base.modulContent.category"/></th>
	                            <th><@my.message "base.modulContent.sku"/></th>
	                            <th><@my.message "base.modulContent.sort"/></th>
	                            <th><@my.message "base.modulContent.isEnabled"/></th>
	                            <th><@my.message "base.modulContent.createuser"/></th>
	                            <th><@my.message "base.modulContent.createdate"/></th>
	                            <th><@my.message "base.modulContent.updateuser"/></th>
	                            <th><@my.message "base.modulContent.updatedate"/></th>
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
     
<div id="modulContentDiv" class="modal fade in">
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title"><@my.message "base.modulContent.formTitle"/></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="modulContentForm" class="form-horizontal" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="client_id" class="form-control">
	                                	<option></option>
	                                    <#list clientList as client>
	                                    	<option value=${client.id}>${client.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.language"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="language_id" class="form-control">
	                                	<option></option>
	                                    <#list languageList as language>
	                                    	<option value=${language.id}>${language.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.layoutName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="layout_id" class="form-control">
	                                	<option></option>
	                                    <#list layoutList as layout>
	                                    	<option value=${layout.id}>${layout.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.moduleName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="layout_module_id" class="form-control">
	                                	<option></option>
	                                    <#list loyoutModulList as loyoutModul>
	                                    	<option value=${loyoutModul.id}>${loyoutModul.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.isShow"/></label>
                                <div class="col-md-9">
                                    <select name="is_show" class="form-control">
                                    	<option></option>
	                                	<option value="1"><@my.message "page.yes"/></option>
	                                    <option value="0"><@my.message "page.no"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.category"/></label>
                                <div class="col-md-9">
                                    <select name="mcFirstCategory" class="form-control" style="width:32%;display:inline">
	                                	<option></option>
	                                    <#list rootCategoryList as rootCategory>
	                                    	<option value=${rootCategory.iid}>${rootCategory.cpath}</option>
	                                    </#list>
	                                </select>
	                                <select id="mcSecondCategory" class="form-control" style="width:32%;display:inline">
	                                </select>
	                                <select id="mcThirdCategory" class="form-control" style="width:32%;display:inline">
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.sku"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="sku" class="form-control input-lg" maxlength="30"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.sort"/></label>
                                <div class="col-md-9">
                                    <input type="number" name="sort" class="form-control input-lg">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.isEnabled"/></label>
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
                            <button type="button" id="mcAddCancel" class="btn default" data-dismiss="modal"><@my.message "page.cancel"/></button>
                            <button type="submit" id="mcAdd" class="btn green"><@my.message "page.save"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
	<a href="#modulContentDiv" id="mcModulContentButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="clientCheck" class="hide"><@my.message "base.modulContent.clientCheck"/></span>
	<span id="languageCheck" class="hide"><@my.message "base.modulContent.languageCheck"/></span>
	<span id="layoutNameCheck" class="hide"><@my.message "base.modulContent.layoutNameCheck"/></span>
	<span id="moduleNameCheck" class="hide"><@my.message "base.modulContent.moduleNameCheck"/></span>
	<span id="isShowCheck" class="hide"><@my.message "base.modulContent.isShowCheck"/></span>
	<span id="categoryCheck" class="hide"><@my.message "base.modulContent.categoryCheck"/></span>
	<span id="skuCheck" class="hide"><@my.message "base.modulContent.skuCheck"/></span>
	<span id="sortCheck" class="hide"><@my.message "base.modulContent.sortCheck"/></span>
	<span id="isEnabledCheck" class="hide"><@my.message "base.modulContent.isEnabledCheck"/></span>
</div>
<script src="/js/baseModul/modulContentManage.js" type="text/javascript"></script>