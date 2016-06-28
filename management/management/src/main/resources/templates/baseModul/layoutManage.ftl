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
        <li class="active"><@my.message "base.layout.title"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            </i><@my.message "base.layout.title"/>
		        </div>
		        <div class="tools">
                    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                </div>
		    </div>
			<div class="portlet-body form">
		        <!-- BEGIN FORM-->
				 <form action="#" class="form-horizontal">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            <div class="form-body">
		                <div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.client"/></label>
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
		                            <label class="control-label col-md-3"><@my.message "base.modulContent.language"/></label>
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
		                     <div class="col-md-4">
		                        <div class="form-group">
									<label class="control-label col-md-4"><@my.message "base.layout.name"/></label>
									<div class="col-md-8">
										<input id="banner_name_id" placeholder="<@my.message "base.layout.name"/>" class="form-control">
									</div>
		                        </div>
		                    </div>
		                    <div class="col-md-1 text-right">
                                	<button type="button" class="btn green" id="mcSearch"><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "base.modulContent.listTitle"/></span>
                </div>
                <div class="actions">
	            	<div class="row">
	                    <div class="col-md-12">
                            <div class="btn-group">
                                <button id="sample_editable_1_new" class="btn sbold green">
                                    <i class="fa fa-plus"></i>
                                </button>
                            </div>
                            <div class="btn-group">
                            	<button id="delete_id" class="btn sbold red">
                            		<i class="fa fa-trash"></i>
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
	                            <th>
	                                <input id="mcMainCheckbox" type="checkbox" class="group-checkable" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th> <@my.message "base.layoutmodule.client"/> </th>
	                            <th> <@my.message "base.layoutmodule.language"/> </th>
	                            <th> <@my.message "base.layout.name"/> </th>
	                            <th> <@my.message "base.layout.code"/> </th>                            
	                            <th> <@my.message "base.layout.url.route"/> </th>                            
	                            <th> <@my.message "base.layout.seo.title"/> </th>                            
	                            <th> <@my.message "base.layout.keyword"/> </th>
	                            <th> <@my.message "base.layout.seo.description"/> </th>
	                            <th> <@my.message "base.layoutmodule.enabled"/></th>
	                            <th> <@my.message "base.layoutmodule.createuser"/></th>
	                            <th> <@my.message "base.layoutmodule.createdate"/></th>
	                            <th> <@my.message "base.layoutmodule.updateuser"/></th>
	                            <th> <@my.message "base.layoutmodule.updatedate"/></th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr class="odd gradeX">
	                        </tr>
	                    </tbody>
	                </table>
                </div>
                <#include "/page.ftl">
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>

<div id="myModal"  class="modal fade in" data-backdrop="static">	
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title"><@my.message "base.layout.add.title"/></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
				  <form  id="layout_form_Id" class="form-horizontal" role="form" method="POST">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div id="clientAdd" class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="client_id_add" class="bs-select form-control" multiple>
										<#list clientList as client>
											<option value=${client.id}>${client.name}</option>
										</#list>
									</select>
									<span id="bannerClientCheckAdd" class="error" style="display:none;color:red;"><@my.message "base.banner.bannerClientCheck"/></span> 
                                </div>
                            </div>
                            <div id="clientUpdate" class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="client_id" class="form-control">
										<#list clientList as client>
											<option value=${client.id}>${client.name}</option>
										</#list>
									</select> 
									<span id="bannerClientCheckUpdate" class="error" style="display:none;color:red;"><@my.message "base.banner.bannerClientCheck"/></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.language"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="language_id" class="form-control">
										<#list languageList as language>
											<option value=${language.id}>${language.name}</option>
										</#list>
									</select> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.code"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input  type="text" name="code" placeholder="<@my.message "base.layout.input.code"/>" class="form-control">
                                </div>
                            </div>
 
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="name" placeholder="<@my.message "base.layout.input.name"/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.url.route"/> </label>
                                <div class="col-md-9">
									<input type="text" name="url" placeholder="<@my.message "base.layout.input.url.route"/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.seo.title"/></label>
                                <div class="col-md-9">
									<input type="text" name="title" placeholder="<@my.message "base.layout.input.seo.title"/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.keyword"/></label>
                                <div class="col-md-9">
									<input type="text" name="keyword" placeholder="<@my.message "base.layout.input.keyword"/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.seo.description"/></label>
                                <div class="col-md-9">
									<textarea class="form-control" name="description" maxlength="1500" placeholder="<@my.message "base.layout.input.seo.description"/>"></textarea>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.seo.description"/></label>
                                <div class="col-md-9">
									<input type="hidden" name="is_deleted" value="0">
									<select name="is_enabled" class="form-control">
										<option value="1">Enabled</option>						
										<option value="0">Disabled</option>
									</select>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
							<button id="addLayout_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<input id="addLayout_submit"  class="btn green" type="submit" value="<@my.message "page.save"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<a href="#checkClientAndLanguageAndLayoutNameMessage"  onclick="closeLayoutBtn()" id="checkLayoutNameButton" class="btn hide" data-toggle="modal"></a>
<div id="checkClientAndLanguageAndLayoutNameMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="close_layout" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "base.layout.validate.client.language.name"/></p>
    </div>
</div>

<a href="#checkCodeMessage"  id="checkCodeButton" onclick="closeLayoutCodeBtn()" class="btn hide" data-toggle="modal"></a>
<div id="checkCodeMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="close_layoutcode" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "base.layout.validate.code"/></p>
    </div>
</div>


<button style="display:none" id="layout_data_id"  class="btn sbold green" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/baseModul/layoutManage.js" type="text/javascript"></script>
