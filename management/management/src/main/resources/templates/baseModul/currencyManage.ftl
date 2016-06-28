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
        <li class="active"><@my.message "base.currency.title"/></li>
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
									<label class="control-label col-md-4"><@my.message "base.currency.name"/></label>
									<div class="col-md-8">
										<input id="currency_name" placeholder="<@my.message "base.currency.name"/>" class="form-control">
									</div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
									<label class="control-label col-md-4"><@my.message "base.currency.code"/></label>
									<div class="col-md-8">
										<input id="currency_code" placeholder="<@my.message "base.currency.code"/>" class="form-control">
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
                    <span class="caption-subject bold uppercase"><@my.message "base.currency.content"/></span>
                </div>
                <div class="actions">
	            	<div class="row">
	                    <div class="col-md-12">
							<div class="btn-group">
                                <button id="currency_synchro" class="btn sbold green">
								 	<i class="fa fa-refresh"></i>
                                </button>
                            </div>
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
									<input id="mcMainCheckbox" type="checkbox" class="group-checkable" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th><@my.message "base.currency.name"/> </th>
	                            <th><@my.message "base.currency.code"/> </th>
	                            <th><@my.message "base.currency.symbol.position"/> </th>
	                            <th> <@my.message "base.currency.symbol"/> </th>
	                            <th> <@my.message "base.currency.current.rate"/></th>
	                        	<th> <@my.message "base.currency.new.rate"/></th>
	                            <th> <@my.message "base.currency.decimal.places"/></th>
	                            <th> <@my.message "base.currency.synchro.date"/></th>
	                            <th> <@my.message "base.layoutmodule.enabled"/></th>
	                            <th> <@my.message "base.layoutmodule.sort"/> </th>
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
	            <h4 class="modal-title" id="currency_title_id"></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
	      	  <form  id="currency_form_Id" class="form-horizontal" role="form" method="POST">

                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
		                        	<input type="hidden" name="id">
									<input  type="text" name="name" placeholder="<@my.message "base.currency.input.name"/>" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.code"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
			                		<input type="text" name="code" placeholder="<@my.message "base.currency.input.code"/>" class="form-control">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.symbol.position"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<select name="symbol_positions" class="form-control">
	                                    <#list parameters as param>
		                                    	<option value=${param.value?c}>${param.name}</option>
	                                    </#list>
									</select>
                                </div>
                            </div>
 
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.symbol"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="symbol_code" placeholder="<@my.message "base.currency.input.symbol"/>" class="form-control">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.current.rate"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="current_rate" placeholder="<@my.message "base.currency.input.current.rate"/> " class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.new.rate"/></label>
                                <div class="col-md-9">
									<input type="text" name="new_rate" placeholder="<@my.message "base.currency.input.new.rate"/> " class="form-control">
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.currency.decimal.places"/></label>
                                <div class="col-md-9">
									<input type="text" name="decimal_places" placeholder="<@my.message "base.currency.input.decimal.places"/>" class="form-control">
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.modulContent.sort"/></label>
                                <div class="col-md-9">
									<input type="number" name="sort" placeholder="<@my.message "base.currency.input.sort"/>"   class="form-control valid" aria-invalid="false">
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
							<button id="add_currency_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<input id="addParam_submit"  class="btn green" type="submit" value="<@my.message "page.save"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<a href="#synchroButton"  id="synchroButtonid" class="btn hide" data-toggle="modal"></a>
<div id="synchroButton" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
			<button style="display:none" id="close_synchro_id" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
	        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
	       	<h2><@my.message "base.currency.being.synchronized"/></h2>
	</div>
</div>
<a href="#synchroButtonS"  id="synchroButtonidS" class="btn hide" data-toggle="modal"></a>
<div id="synchroButtonS" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
		<button  type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "base.currency.synchro.success"/></p>
    </div>
</div>

<a href="#synchroButtonF"  id="synchroButtonidF" class="btn hide" data-toggle="modal"></a>
<div id="synchroButtonF" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
		<button  type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "base.currency.synchro.fail"/></p>
    </div>
</div>
<button style="display:none" id="currency_data_id"  class="btn sbold green" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/baseModul/currencyManage.js" type="text/javascript"></script>
