<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/systemConfig"><@my.message "page.systemManage"/></a>
        </li>
        <li>
            <a href="/shipping/freightChargeManage"><@my.message "page.freightManage"/></a>
        </li>
        <li class="active"><@my.message "shipping.templateConfig.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "shipping.templateConfig.searchTitle"/>
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
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.templateName"/></label>
		                            <div class="col-md-8">
		                                <select id="templateName" class="form-control">
		                                    <option></option>
		                                    <#list shippingTemplateList as shippingTemplate>
		                                    	<option value=${shippingTemplate.id}>${shippingTemplate.template_name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.shippingType"/></label>
		                            <div class="col-md-8">
		                                <select id="shippingType" class="form-control">
		                                    <option></option>
		                                    <#list shippingTypeList as shippingType>
		                                    	<option value=${shippingType.id}>${shippingType.type_name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.shippingCountry"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="shippingCountry" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.filterConditions"/></label>
		                            <div class="col-md-8">
		                                <select id="filterConditions" class="form-control">
		                                    <option></option>
		                                    <#list filterList as filter>
		                                    	<option value=${filter.id}>${filter.name}</option>
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
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.isFreeshipping"/></label>
		                            <div class="col-md-8">
		                                <select id="isFreeshipping" class="form-control">
		                                    <option></option>
	                                		<option value="1"><@my.message "page.yes"/></option>
	                                    	<option value="0"><@my.message "page.no"/></option>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "shipping.templateConfig.isEspecial"/></label>
		                            <div class="col-md-8">
		                                <select id="isEspecial" class="form-control">
		                                    <option></option>
	                                		<option value="1"><@my.message "page.yes"/></option>
	                                    	<option value="0"><@my.message "page.no"/></option>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
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
                    <span class="caption-subject bold uppercase"><@my.message "shipping.templateConfig.listTitle"/></span>
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
	                            <th><@my.message "shipping.templateConfig.templateName"/></th>
	                            <th><@my.message "shipping.templateConfig.shippingType"/></th>
	                            <th><@my.message "shipping.templateConfig.storage"/></th>
	                            <th><@my.message "shipping.templateConfig.shippingCountry"/></th>
	                            <th><@my.message "shipping.templateConfig.filterConditions"/></th>
	                            <th><@my.message "shipping.templateConfig.shippingCode"/></th>
	                            <th><@my.message "shipping.templateConfig.isEspecial"/></th>
	                            <th><@my.message "shipping.templateConfig.isFreeshipping"/></th>
	                            <th><@my.message "shipping.templateConfig.isCountWeight"/></th>
	                            <th><@my.message "shipping.templateConfig.amountLimit"/></th>
	                            <th><@my.message "shipping.templateConfig.weightLimit"/></th>
	                            <th><@my.message "shipping.templateConfig.extraCharge"/></th>
	                            <th><@my.message "shipping.templateConfig.extraChargeNote"/></th>
	                            <th><@my.message "page.isEnabled"/></th>
	                            <th><@my.message "page.createuser"/></th>
	                            <th><@my.message "page.createdate"/></th>
	                            <th><@my.message "page.updateuser"/></th>
	                            <th><@my.message "page.updatedate"/></th>
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
<div id="stcDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "shipping.templateConfig.formTitle"/></h4>
            </div>
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="stcForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                        	<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.storage"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="warehouse_id" class="form-control">
                                    	<option></option>
                                    	<#list storageList as storage>
	                                    	<option value=${storage.iid}>${storage.cstoragename}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.templateName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="shipping_template_id" class="form-control">
                                    	<option></option>
	                                    <#list shippingTemplateList as shippingTemplate>
	                                    	<option value=${shippingTemplate.id}>${shippingTemplate.template_name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.shippingType"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="shipping_type_id" class="form-control">
                                    	<option></option>
	                                    <#list shippingTypeList as shippingType>
	                                    	<option value=${shippingType.id}>${shippingType.type_name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.shippingCountry"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <label for="includeCountry"><input type="radio" id="includeCountry" name="country_add_type" value="0" class="form-control">包含</label>
                                    <label for="debarCountry"><input type="radio" id="debarCountry" name="country_add_type" value="1" class="form-control">排除</label>
                                    <label for="allCountry"><input type="radio" id="allCountry" name="country_add_type" value="2" class="form-control">所有</label><br>
                                    <textarea id="country" name="country" cols="55" rows="5"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.isEspecial"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="is_especial" class="form-control">
	                                	<option></option>
                                		<option value="1"><@my.message "page.yes"/></option>
                                    	<option value="0"><@my.message "page.no"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.isFreeshipping"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="is_freeshipping" class="form-control">
	                                	<option></option>
                                		<option value="1"><@my.message "page.yes"/></option>
                                    	<option value="0"><@my.message "page.no"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.isCountWeight"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="is_calculate_weight" class="form-control">
	                                	<option></option>
                                		<option value="1"><@my.message "page.yes"/></option>
                                    	<option value="0"><@my.message "page.no"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.shippingCode"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="priority_shipping_code" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.filterConditions"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="filter_id" class="form-control">
	                                	<option></option>
	                                	<#list filterList as filter>
	                                    	<option value=${filter.id}>${filter.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.amountLimit"/></label>
                                <div class="col-md-4">
                                    <input type="number" name="start_amount" class="form-control"> 
                                </div>
                                <div class="col-md-1">
                                    <span>--</span>
                                </div>
                                <div class="col-md-4">
                                    <input type="number" name="amount_limit" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.weightLimit"/></label>
                                <div class="col-md-4">
                                    <input type="number" name="start_weight" class="form-control">
                                </div>
                                <div class="col-md-1">
                                    <span>--</span>
                                </div>
                                <div class="col-md-4">
                                    <input type="number" name="weight_limit" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.extraCharge"/></label>
                                <div class="col-md-9">
                                    <input type="number" name="extra_charge" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "shipping.templateConfig.extraChargeNote"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="extra_charge_note" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "page.isEnabled"/><span class="required" aria-required="true"> * </span></label>
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
	<div id="repeatSTCAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "shipping.templateConfig.stcCheck"/></p>
        </div>
    </div>
    <div id="invalidCOrSCAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "shipping.templateConfig.cscCheck"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#stcDiv" id="stcShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#repeatSTCAlert" id="repeatSTCShowButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
	<a href="#invalidCOrSCAlert" id="invalidCOrSCShowButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="storageCheck" class="hide"><@my.message "shipping.templateConfig.storageCheck"/></span>
	<span id="shippingTemplateCheck" class="hide"><@my.message "shipping.templateConfig.shippingTemplateCheck"/></span>
	<span id="shippingTypeCheck" class="hide"><@my.message "shipping.templateConfig.shippingTypeCheck"/></span>
	<span id="shippingCountryCheck" class="hide"><@my.message "shipping.templateConfig.shippingCountryCheck"/></span>
	<span id="isEspecialCheck" class="hide"><@my.message "shipping.templateConfig.isEspecialCheck"/></span>
	<span id="isFreeshippingCheck" class="hide"><@my.message "shipping.templateConfig.isFreeshippingCheck"/></span>
	<span id="isCalculateCheck" class="hide"><@my.message "shipping.templateConfig.isCalculateCheck"/></span>
	<span id="shippingCodeCheck" class="hide"><@my.message "shipping.templateConfig.shippingCodeCheck"/></span>
	<span id="filterConditionsCheck" class="hide"><@my.message "shipping.templateConfig.filterConditionsCheck"/></span>
	<span id="isEnabledCheck" class="hide"><@my.message "shipping.templateConfig.isEnabledCheck"/></span>
	<span id="upperCaseCheck" class="hide"><@my.message "shipping.templateConfig.upperCaseCheck"/></span>
</div>
<script src="/js/shipping/shippingTemplateConfig.js" type="text/javascript"></script>
