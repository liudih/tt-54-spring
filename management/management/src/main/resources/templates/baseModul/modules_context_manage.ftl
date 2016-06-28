<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="javascript:;"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="javascript:;"><@my.message "base.client.bread.module"/></a>
                            </li>
                            <li>
                                <a href="javascript:;"><@my.message "base.client.bread.design"/></a>
                            </li>
                            <li class="active"><@my.message "base.layoutmodule.title"/></li>
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
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3"><@my.message "base.layoutmodule.client"/></label>
                            <div class="col-md-9">
                                <select class="form-control" id="client_id">
                                		<option vlaue=""></option>
                                   <#list clientList as client>
                                    	<option value=${client.id}>${client.name}</option>
                                    </#list>
                                </select>
                                <!-- <span class="help-block"> Select your gender. </span>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3"><@my.message "base.layoutmodule.language"/></label>
                            <div class="col-md-9">
                                <select class="form-control" id="language_id">
                                	<option vlaue=""></option>
                                    <#list languageList as language>
                                    	<option value=${language.id}>${language.name}</option>
                                    </#list>
                                </select>
                                <!-- <span class="help-block"> Select your gender. </span>-->
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3"><@my.message "base.layoutmodule.layoutName"/></label>
                            <div class="col-md-9">
                                <select class="form-control" id="layout_id">
                                	<option vlaue=""></option>
                                    <#list layoutList as layout>
                                    	<option value=${layout.id}>${layout.name}</option>
                                    </#list>
                                </select>
                                <!-- <span class="help-block"> Select your gender. </span>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3"><@my.message "base.layoutmodule.position"/></label>
                            <div class="col-md-9">
                                <select class="form-control" id="position_id">
                                			<option vlaue=""></option>
                                			<#list paramters as p>
                                				<option value=${p.value}>${p.name}</option>
                                			</#list>
	                                </select>
                                <!-- <span class="help-block"> Select your gender. </span>-->
                            </div>
                        </div>
                    </div>
                </div>
                
                
                <div class="row">
                    <!--/span-->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-md-3"><@my.message "base.layoutmodule.moduleName"/></label>
                            <div class="col-md-9">
                                <input type="text" id="name" class="form-control" placeholder=""> </div>
                        </div>
                    </div>
                    <!--/span-->
                </div>
                <div class="row" style="margin-left:200px;">
                	<button id="searchS" type="button" class="btn green"><@my.message "page.search"/></button>
                </div>
                </div>
           </form>
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
			                     <button id="ta" class="btn sbold green" data-toggle="modal" href="#static"> <@my.message "page.add"/>
			                     </button>
		                </div>
		                <div class="btn-group" id="delT">
		                     <button id="sample_editable_1_new" class="btn sbold red"> <@my.message "page.delete"/>
		                     </button>
		                 </div>
	                 </div>
                </div>
            </div>
           <div class="portlet-body">
           <div class="table-scrollable">
           <table class="table table-striped table-bordered table-hover" id="bodyT">
                                        <thead>
                                            <tr>
                                                <th scope="col">
	                                                	<span>
	                                                		<input type="checkbox" class="group-checkable" id="mcMainCheckbox" data-set="#bodyT .checkboxes"/>
	                                                	</span>
                                                </th>
                                                <th> <@my.message "page.operation"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.client"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.language"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.layoutName"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.moduleName"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.moduleCode"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.position"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.url"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.number"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.sort"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.enabled"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.createuser"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.createdate"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.updateuser"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.updatedate"/></th>
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
  </div>
  </div>
          <div id="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "base.layoutmodule.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="id-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
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
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.layoutName"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="layout_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list layoutList as layout>
																<option value=${layout.id}>${layout.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.moduleName"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="name" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.moduleCode"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="code" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.position"/></label>
                                                    <div class="col-md-9">
                                                        <select name="position_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list paramters as p>
																<option value=${p.value}>${p.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.url"/></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="url" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@my.message "base.layoutmodule.number"/></label>
													<div class="col-md-9">
														<input type="number" name="number" class="form-control" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label"><@my.message "base.layoutmodule.sort"/></label>
													<div class="col-md-9">
														<input type="number" name="sort" class="form-control" />
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
			
    <#include "/message.ftl">
    
<script src="/js/baseModul/layoutModuleManage.js" type="text/javascript"></script>
