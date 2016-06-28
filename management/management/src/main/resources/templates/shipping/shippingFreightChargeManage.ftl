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
        <li class="active"><@my.message "shipping.freight.count"/></li>
    </ol>
</div>
<div class="row">
	    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "shipping.freight.computing"/>
		        </div>
		        <div class="tools">
                    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                </div>
		    </div>
			<div class="portlet-body form">
		        <!-- BEGIN FORM-->
				 <form id="sfcForm" action="#" class="form-horizontal">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            <div class="form-body">
		                <div class="row">
	               			<div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-5"><@my.message "shipping.freight.receiving.country"/><span class="required" aria-required="true"> * </span></label>
		                            <div class="col-md-7">
		                                <select id="country" name="country" class="bs-select form-control" data-live-search="true">
		                                    <#list countries as country>
		                                    	<option value=${country.id}>${country.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
						</div>
	                   	<div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
									<label class="control-label col-md-5"><@my.message "shipping.freight.weight"/><span class="required" aria-required="true"> * </span></label>
									<div class="col-md-7">
										<input id="weight" name="weight" placeholder="<@my.message "shipping.freight.weight"/>" class="form-control">
									</div>
		                        </div>
		                    </div>
						</div>
						<div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-5"><@my.message "shipping.repertory"/><span class="required" aria-required="true"> * </span></label>
		                            <div class="col-md-7">
		                                <select id="storage" name="storage" class="form-control">
		                                    <#list storages as storage>
		                                    	<option value=${storage.iid}>${storage.cstoragename}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
						</div>
						<div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-5"><@my.message "shipping.type"/><span class="required" aria-required="true"> * </span></label>
		                            <div class="col-md-7">
		                                <select id="shippingType" name="shippingType" class="form-control">
		                                    <#list shippingTypes as type>
		                                    	<option value=${type.id}>${type.type_name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
							<div class="col-md-3">
		                        <div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button type="submit" class="btn green" id="freightCalculate"><@my.message "shipping.freight.computing"/></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "shipping.freight.count.content"/></span>
                </div>
            </div>
            <div class="portlet-body">
            	<div class="table-scrollable">
					<table class="table table-striped table-bordered table-hover table-checkable order-column dataTable no-footer" id="dataTable">
						<thead>
							<tr>
	                            <th><@my.message "shipping.freight.receiving.country"/> </th>
	                            <th> <@my.message "shipping.repertory"/></th>
	                            <th> <@my.message "shipping.type"/></th>
	                            <th> <@my.message "shipping.freight.shippingCode"/></th>
	                            <th id="tfreight" class="sorting_asc"> <@my.message "shipping.freight"/></th>
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
<div>
	<span id="storageCheck" class="hide"><@my.message "shipping.freight.storageCheck"/></span>
	<span id="weightCheck" class="hide"><@my.message "shipping.freight.weightCheck"/></span>
	<span id="shippingTypeCheck" class="hide"><@my.message "shipping.freight.shippingTypeCheck"/></span>
	<span id="shippingCountryCheck" class="hide"><@my.message "shipping.freight.shippingCountryCheck"/></span>
</div>
<script src="/js/shipping/shippingFreightCharge.js" type="text/javascript"></script>