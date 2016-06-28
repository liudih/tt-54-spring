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
        <li class="active"><@my.message "base.param.title"/></li>
    </ol>
</div>
<div class="row">
	    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "page.search.condition"/>
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
		                    <!--/span-->
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
							
							 <div class="col-md-3">
		                        <div class="form-group">
									<label class="control-label col-md-4"><@my.message "base.param.type"/></label>
									<div class="col-md-8">
										<input id="type_name_id" placeholder="<@my.message "base.param.type"/>" class="form-control">
									</div>
		                        </div>
		                    </div>
							<div class="col-md-3">
		                        <div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button type="button" class="btn green" id="mcSearch"><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "base.param.content"/></span>
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
                            	<button id="delete_id" class="btn sbold red" onclick="return confirm('Are you sure to delete them?'')">
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
									<input id="mcMainCheckbox" type="checkbox" class="group-checkable"  /> </th>
								<th><@my.message "page.operation"/></th>
								<th> <@my.message "base.layoutmodule.client"/> </th>
								<th> <@my.message "base.layoutmodule.language"/> </th>
								<th><@my.message "base.param.type"/> </th>
								<th> <@my.message "base.param.value"/>  </th>
								<th> <@my.message "base.param.value.description"/> </th>
								<th> <@my.message "base.layoutmodule.sort"/> </th>
								<th> <@my.message "base.param.remark"/>  </th>
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
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>

<#include "/page.ftl">     
<#include "/message.ftl">
<div id="myModal"  class="modal fade in" data-backdrop="static">	
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title" id="param_title_id"></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
				<form action="#" id="param_form_id" class="form-horizontal" role="form" method="POST">

                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                                <label class="col-md-3 control-label"><@my.message "base.param.type"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="type" placeholder="<@my.message "base.param.input.type"/>" class="form-control">
                                </div>
                            </div>
 
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.param.value"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="value" placeholder="<@my.message "base.param.input.value"/>" onkeyup="this.value=this.value.replace(/[^\d^\+]/,'')" class="form-control">

                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.param.value.description"/></label>
                                <div class="col-md-9">
									<input type="text" name="name" placeholder="<@my.message "base.param.input.remark"/>"   class="form-control">

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.layoutmodule.sort"/></label>
                                <div class="col-md-9">
									<input  type="text" name="sort" placeholder="<@my.message "base.currency.input.sort"/>" onkeyup="this.value=this.value.replace(/[^\d^\+]/,'')"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.param.remark"/></label>
                                <div class="col-md-9">
									<input type="hidden" name="is_deleted" value="0">
									<textarea class="form-control" name="remark" maxlength="1500" placeholder="<@my.message "base.param.input.remark"/>" ></textarea>
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
							<button id="addParam_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<input id="addParam_submit"  class="btn green" type="submit" value="<@my.message "page.save"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<button style="display:none" id="param_edit_data" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/baseModul/paramManage.js" type="text/javascript"></script>
