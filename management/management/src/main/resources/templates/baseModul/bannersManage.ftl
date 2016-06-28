<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/base"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="/base/baseDesign"><@my.message "page.baseDesign"/></a>
        </li>
        <li class="active"><@my.message "base.banner.title"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.banner.searchTitle"/>
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
		                     <div class="col-md-3">
		                        <div class="form-group">
									<label class="control-label col-md-4"><@my.message "base.layout.name"/></label>
									<div class="col-md-8">
										<select id="layout_code_id" class="form-control">
										   <option value=""></option>
											<#list layoutList as layout>
												<option value=${layout.code}>${layout.code}</option>
											</#list>
										</select>
									</div>
		                        </div>
		                    </div>
							
							 <div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4"><@my.message "base.banner.name"/></label>
									<div class="col-md-8">
										<input type="text" id="banner_name_id" class="form-control">
									</div>
								</div>
							</div>
		                    
		                </div>
		                <div class="row">
		                    <div class="col-md-12 text-right">
                                <button type="button" id="mcSearch" class="btn green "><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "base.banner.content"/></span>
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
					<table class="table table-striped table-bordered table-hover table-checkable order-column" id="table_form_id">
						<thead>
							<tr>
								<th>
									<input id="mcMainCheckbox" type="checkbox" class="group-checkable" /> </th>
								<th><@my.message "page.operation"/></th>
								<th> <@my.message "base.layoutmodule.client"/> </th>
								<th> <@my.message "base.layoutmodule.language"/> </th>
								<th> <@my.message "base.layout.name"/> </th>
								<th> <@my.message "base.banner.name"/> </th>
								<th> <@my.message "base.banner.code"/></th>
								<th> <@my.message "base.layoutmodule.position"/> </th>
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
	            <h4 class="modal-title"><@my.message "base.banner.formTitle"/></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
				 <form class="form-horizontal" id="banner_form_Id" role="form" method="POST">

                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="client_id" class="form-control">
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
										<#list languageList as language>
											<option value=${language.id}>${language.name}</option>
										</#list>
									</select> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layout.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="layout_code" class="form-control">
										<#list layoutList as layout>
											<option value=${layout.code}>${layout.code}</option>
										</#list>
									</select> 
                                </div>
                            </div>
 
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.banner.name"/></label>
                                <div class="col-md-9">
								   <input type="text" name="name" placeholder="<@my.message 'base.input.banner.name'/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.banner.code"/></label>
                                <div class="col-md-9">
									<input type="text" name="code" placeholder="<@my.message 'base.input.banner.code'/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layoutmodule.position"/></label>
                                <div class="col-md-9">
									<select class="form-control" name="position_id">
										<#list paramters as p>
											<option value=${p.value}>${p.name}</option>
										</#list>
									</select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layoutmodule.enabled"/></label>
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
							<button id="add_banner_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<button id="addParam_submit"  class="btn green" type="submit"><@my.message "page.save"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<button style="display:none" id="banner_edit_data" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/baseModul/bannerManage.js" type="text/javascript"></script>
