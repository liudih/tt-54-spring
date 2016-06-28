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
        <li class="active"><@my.message "home.detail.deal.title"/></li>
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
		                                <select id="language_id"  class="bs-select form-control" multiple>
		                                    <#list languageList as language>
		                                    	<option value=${language.id}>${language.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
							
							 <div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "home.detail.deal.sku"/></label>
									<div class="col-md-9">
										<input id="daily_deal_sku_id" placeholder="<@my.message "home.detail.deal.sku"/>" class="form-control">
									</div>
								</div>
							</div>
							 <div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-4"><@my.message "home.detail.deal.date"/></label>
									<div class="col-md-8">
										<input id="daily_deal_start_date_id" readonly="readonly" placeholder="<@my.message "home.detail.deal.date"/>" data-date-format="yyyy/mm/dd" class="form-control form-control-inline date-picker" size="26">
									</div>
								</div>
							</div>
		                </div>
                        <div class="row">
							 <div class="col-md-12 text-right">
								<button type="button" class="btn green" id="mcSearch"><i class="fa fa-search"></i></button>
								<button type="reset" class="btn green"><i class="fa fa-rotate-left"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "home.detail.deal.content"/></span>
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
									<input id="mcMainCheckbox" type="checkbox" class="group-checkable"/> </th>
								<th> <@my.message "base.layoutmodule.client"/> </th>
								<th> <@my.message "base.layoutmodule.language"/> </th>
								<th><@my.message "home.detail.deal.sku"/> </th>
								  <th><@my.message "home.detail.deal.date"/> </th>
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
<div id="myModal"  class="modal fade in" data-backdrop="static">	
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title" id="dailyDeal_title_id"></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
	        		<form class="form-horizontal" id="daily_deal_form_Id" role="form" method="POST">

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
                                <label class="col-md-3 control-label"><@my.message "home.detail.deal.sku"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input  type="text" name="sku" placeholder="<@my.message "home.detail.deal.input.sku"/>">&nbsp;
									<@my.message "home.detail.deal.price"/>
									<span id="price_id" style="color: red;font-weight: bolder;"></span>&nbsp;
									<@my.message "home.detail.deal.costprice"/>
									<span id="cost_price" style="color: red;font-weight: bolder;"></span>
									<input type="hidden" id="listting_id" >
                                </div>
                            </div>
 
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "home.detail.deal.date"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<div class="input-group input-medium date date-picker" data-date-start-date="+0d">
									<input readonly="readonly" type="text" name="startTime_date" placeholder="<@my.message "home.detail.deal.input.date"/>" class="form-control">
										<span class="input-group-btn">
											<button class="btn default" type="button" id="button_id">
												<i class="fa fa-calendar"></i>
											</button>
										</span>
									</div>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "home.detail.deal.discount"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input name="is_enabled" value="1" type="hidden">
									<input name="is_deleted" value="0" type="hidden">
									<input type="number" name="discount" placeholder="<@my.message "home.detail.deal.number"/>"  class="form-control valid" aria-invalid="false">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "home.detail.deal.discountprice.label"/></label>
                                <div class="col-md-9">
		               			    <input  placeholder="<@my.message "home.detail.deal.discountprice"/>" name="fSalePrice" class="form-control" readonly="readonly" >
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

<a href="#checkClientAndSkuAndDate" onclick="clientAndSkuAndDateClick()"  id="validateClientAndSkuAndDate" class="btn hide" data-toggle="modal"></a>
<div id="checkClientAndSkuAndDate" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="clientAndSkuAndDate_id" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.detail.deal.check.client.sku.date"/></p>
    </div>
</div>

<a href="#checkdiscountprice" onclick="discountPriceClick()" id="validatediscountprice" class="btn hide" data-toggle="modal"></a>
<div id="checkdiscountprice" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="discountprice_id" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.detail.deal.check.discountprice"/></p>
    </div>
</div>

<a href="#checkdiscountpricearea"  onclick="discountPriceAreaClick()"  id="validatediscountpricearea" class="btn hide" data-toggle="modal"></a>
<div id="checkdiscountpricearea" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="discountpricearea_id" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.detail.deal.check.discountprice.area"/></p>
    </div>
</div>

<button style="display:none" id="daily_deal_edit_data" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/homemodul/dailyDealManage.js" type="text/javascript"></script>
