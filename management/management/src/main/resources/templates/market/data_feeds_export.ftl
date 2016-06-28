<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/market"><@my.message "market.datafeed.navigation"/></a>
                            </li>
                            <li>
                                <a href="/market/datafeeds"><@my.message "market.datafeed.name"/></a>
                            </li>
                            <li class="active"><@my.message "market.datafeed.title"/></li>
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
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2"><@my.message "market.datafeed.category"/></label>
										<select id="firstCategory" name="categoryId" class="form-control" style="width:32%;display:inline">
											<option value=""></option>
		                                    <#list rootCategoryList as rootCategory>
		                                    	<option value=${rootCategory.iid}>${rootCategory.cpath}</option>
		                                    </#list>
		                                </select>&nbsp;&nbsp;
		                                <button id="searchS" type="button" class="btn green"><i class="fa fa-search"></i></button>
										<button id="import" type="button" data-toggle="modal" href="#static" class="btn green"><@my.message "market.datafeed.import"/></button>
									</div>
									
									
								</div>
								<!--/span-->
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
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet light bordered">
						<div class="portlet-title">
							<div class="caption font-dark">
								<span class="caption-subject bold uppercase"><@my.message "base.layoutmodule.listtitle"/></span>
							</div>
						</div>
						<div class="portlet-body">
						   <div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover table-checkable order-column" id="bodyT">
														<thead>
															<tr>
																<th> <@my.message "base.modulContent.sku"/></th>
																<th> <@my.message "market.datafeed.titleN"/></th>
																<th> <@my.message "market.datafeed.price"/></th>
																<th> <@my.message "market.datafeed.saleprice"/></th>
																<th> <@my.message "authority.role.status"/></th>
																<th> <@my.message "market.datafeed.url"/></th>
																<th> <@my.message "market.datafeed.imgUrl"/></th>
																<th> <@my.message "market.datafeed.categoryName"/></th>
																<th> <@my.message "market.datafeed.brand"/></th>
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
  	<div id="static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "market.datafeed.t"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="id-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id"/>
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
                                                    <label class="col-md-3 control-label"><@my.message "base.country.currency"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="currency_code" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list currencies as currency>
																<option value=${currency.code}>${currency.code}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "market.datafeed.platforms"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select id="platForm" name="plat_form" class="form-control" aria-required="true" aria-invalid="true">
																<option value="admitad" selected="selected">Admitad</option>
																<option value="clixgalore">clixgalore</option>
																<option value="linkshare">linkshare</option>
																<option value="shareasale">shareasale</option>
																<option value="webgains">webgains</option>
																<option value="google">Google Merchant Center-US</option>
																<option value="facebook">Facebook</option>
																<option value="tradetracker">Tradetracker</option>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "market.datafeed.fileType"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select id="fileType" disabled="false" name="file_type" class="form-control" aria-required="true" aria-invalid="true">
																<option value="excel">EXCEL</option>
																<option value="csv">CSV</option>
																<option value="xml" selected="selected">XML</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "market.datafeed.isExportFreight"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <label for="isExportFreight"><input type="radio" id="isExportFreight" name="isExportFreight" value="1" class="form-control">是</label>
                                    					<label for="isNotExportFreight"><input type="radio" id="isNotExportFreight" name="isExportFreight" value="0" class="form-control" checked="true">否</label>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="storageForm" style="display:none;">
                                                    <label class="col-md-3 control-label"><@my.message "market.datafeed.storage"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select id="storageId" name="storageId" class="form-control" aria-required="true" aria-invalid="true">
															<#list storages as storage>
																<option value=${storage.iid}>${storage.cstoragename}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="countryForm" style="display:none;">
                                                    <label class="col-md-3 control-label"><@my.message "market.datafeed.country"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select id="country" name="country" class="form-control" aria-required="true" aria-invalid="true">
															<#list countries as country>
																<option value=${country.iso_code_two}>${country.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
                                            <div class="modal-footer">
                                                <button type="button" id="closeB" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <a id="addBtn" class="btn green"><@my.message "market.datafeed.import"/></a>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
 </div>
 
 <#include "/message.ftl">

<script src="/js/market/datafeed.js" type="text/javascript"></script>