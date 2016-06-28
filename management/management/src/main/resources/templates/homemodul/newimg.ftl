<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/indexConfig"><@my.message "homepage.hotsearch.bread.module"/></a>
                            </li>
                            <li>
                                <a href="/indexConfig/customerVoice"><@my.message "homepage.newimg.customer"/></a>
                            </li>
                            <li class="active"><@my.message "homepage.newimg.title"/></li>
                        </ol>
                    </div>

<div class="row">
<div class="col-md-12">
<div class="portlet box green">
	<div class="portlet-title">
        <div class="caption">
            <@my.message "page.search.content"/>
        </div>
        <div class="tools">
			         <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
	    </div>
    </div>
    
    
    <div class="portlet-body">
    	<form action="" class="form-horizontal">
            <div class="form-body">
                <div class="row">
                    <!--/span-->
                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.modulContent.client"/></label>
		                            <div class="col-md-8">
		                                <select id="client_id" class="bs-select form-control" multiple>
		                                    <#list clientList as client>
		                                    	<option value=${client.id}>${client.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.modulContent.language"/></label>
		                            <div class="col-md-8">
		                                <select id="language_id"class="bs-select form-control" multiple>
		                                    <#list languageList as language>
		                                    	<option value=${language.id}>${language.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
                    <!--/span-->
                	<!--/span-->
                     <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label col-md-4"><@my.message "homepage.newimg.person"/></label>
                            <div class="col-md-8">
                            	<input type="text" id="img_by" class="form-control">
                            </div>
                        </div>
                    </div>
                    <!--/span-->
                    <!--/span-->
                     <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label col-md-4"><@my.message "homepage.newimg.country"/></label>
                            <div class="col-md-8">
                                <select id="country" class="form-control">
                                	<option></option>
                                    <#list countries as country>
                                    	<option value="${country.name}">${country.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <!--/span-->
				</div>
                <div class="row">
                	<!--/span-->
                     <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label col-md-4"><@my.message "homepage.newimg.sku"/></label>
                            <div class="col-md-8">
                            	<input type="text" id="sku" class="form-control">
                            </div>
                        </div>
                    </div>
                    <button id="searchS" type="button" class="btn green"><i class="fa fa-search"></i></button>
                 </div>
			</div>
           </form>
           </div>
        </div>
  </div>
  	<div class="row">
  		<div class="col-md-12">
        <!-- BEGIN EXAMPLE TABLE PORTLET-->
        <div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption font-dark">
                    <span class="caption-subject bold uppercase"><@my.message "base.layoutmodule.listtitle"/></span>
                </div>
                <div class="actions">
                	<div class="row">
                		<div class="btn-group" id="addT">
			                     <button id="tt" class="btn sbold green" data-toggle="modal" href="#static"> <i class="fa fa-plus"></i>
			                     </button>
		                </div>
		                <div class="btn-group" id="delT">
		                     <button id="sample_editable_1_new" type="button" class="btn sbold red"> <i class="fa fa-trash"></i>
		                     </button>
		                 </div>
	                 </div>
                </div>
            </div>
           <div class="portlet-body">
           <div class="table-scrollable">
           <table class="table table-striped table-bordered table-hover table-checkable order-column" id="bodyT">
                                        <thead>
                                            <tr>
                                                <th>
                                                    <input type="checkbox" id="mcMainCheckbox" class="group-checkable" data-set="#bodyT .checkboxes" /> 
                                                </th>
                                                <th> <@my.message "page.operation"/></th>
                                                <th> <@my.message "base.client.name"/></th>
					                            <th> <@my.message "base.layoutmodule.language"/></th>
					                            <th> <@my.message "homepage.newimg.sku"/></th>
					                            <th> <@my.message "homepage.newimg.url"/></th>
					                            <th> <@my.message "homepage.newimg.person"/></th>
					                            <th> <@my.message "homepage.newimg.country"/></th>
					                            <th> <@my.message "base.layoutmodule.enabled"/></th>
					                        	<th> <@my.message "base.layoutmodule.createuser"/></th>
					                            <th> <@my.message "base.layoutmodule.createdate"/></th>
					                            <th> <@my.message "base.layoutmodule.updateuser"/> </th>
					                            <th> <@my.message "base.layoutmodule.updatedate"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
             </table>
             </div>
             </div>
         </div>
         <#include "/page.ftl">
         <!-- END EXAMPLE TABLE PORTLET-->
  	</div>
    </div> 
    
    
    <div id="static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "homepage.newimg.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="img-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id"/>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.client.name"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="client_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list clientList as client>
																<option value=${client.id}>${client.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.language"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="language_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list languageList as language>
																<option value=${language.id}>${language.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.sku"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="sku" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.url"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="img_url" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.person"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="img_by" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.country"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="country" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list countries as country>
																<option value=${country.name}>${country.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.enabled"/></label>
                                                    <div class="col-md-9">
                                                        <select name="is_enabled" class="form-control error" aria-required="true">
					                                    	<option value="1">Enabled</option>
					                                    	<option value="0">Disabled</option>
					                                	</select>
					                                	<label id="is_enabled-error" class="error" for="is_enabled" style="display: none;"></label>
                                                    </div>
                                                </div>
                                            </div>
											
                                            <div class="modal-footer">
                                                <button type="button" id="closeB" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button type="submit" id="addBtn" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
    
    
  </div>
  
  


<#include "/message.ftl">

<script src="/js/homemodul/newimg.js" type="text/javascript"></script>