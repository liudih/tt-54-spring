<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="#"><@my.message "page.home"/></a>
        </li>
        <li>
           <a href="#"><@my.message "page.systemManage"/></a>
        </li>
        <li>
            <a href="#"><@my.message "shipping.freight.manage"/></a>
        </li>
        <li class="active"><@my.message "shipping.template.title"/></li>
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
									<label class="control-label col-md-4"><@my.message "shipping.template.name"/></label>
									<div class="col-md-8">
										<input id="template_name_Id" placeholder="<@my.message "shipping.template.name"/>" class="form-control">
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
                    <span class="caption-subject bold uppercase"><@my.message "shipping.template.content"/></span>
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
	                            <th><@my.message "shipping.template.name"/> </th>
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
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>

<#include "/page.ftl">  
<#include "/message.ftl">
<div id="myModal"  class="modal fade in" data-backdrop="static" >	
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title" id="shippingtemplate_title_id"></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
	      	  <form  id="shippingtemplate_form_Id" class="form-horizontal" role="form" method="POST">

                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.template.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
		                        	<input type="hidden" name="id">
									<input  type="text" name="template_name" placeholder="<@my.message "shipping.template.input.name"/>" class="form-control">
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
							<button id="add_shippingtemplate_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<input class="btn green" type="submit" value="<@my.message "page.save"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<a href="#checkTemplateNameExistMessage" onclick="closeTemplateNameExistBtn()" id="checkExistTemplateName" class="btn hide" data-toggle="modal"></a>
<div id="checkTemplateNameExistMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="closeExistTemplateNameId" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "shipping.template.name.exist"/></p>
    </div>
</div>
<button style="display:none" id="shippingtemplate_data_id"  class="btn sbold green" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/shipping/shippingtemplate.js" type="text/javascript"></script>
