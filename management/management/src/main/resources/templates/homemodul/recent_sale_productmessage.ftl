<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/indexConfig/sale"><@my.message "homepage.hotsearch.bread.module"/></a>
                            </li>
                            <li class="active"><@my.message "homepage.recent.title"/></li>
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
                    <!--/span-->
                    <button id="searchS" type="button" class="btn green"><i class="fa fa-search"></i></button>
               	</div>
                	
                </div>
           </form>
           </div>
           </div>
  <div class="row">
    <div class="col-md-12">
         <!-- BEGIN CONTAINER -->
        <div class="wrapper">
            <div class="container-fluid">
                <div class="col-md-12 bg-white">
                    <div class="tabbable-custom ">
                        <ul class="nav nav-tabs ">
                            <li class="active">
                                <a aria-expanded="true" href="#tab_5_1" data-toggle="tab">
                                    <@my.message "homepage.recent.sku"/> </a>
                            </li>
                            <li class="">
                                <a aria-expanded="false" href="#tab_5_2" data-toggle="tab">
                                    	<@my.message "homepage.recent.country"/> </a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab_5_1">
                                <div class="portlet-body">
						                <div class="table-toolbar">
													<div class="row">
														<div class="col-md-6">
															<div class="btn-group" id="addS">
																<button id="tt1" type="button" class="btn sbold green" data-toggle="modal" href="#staticS"> <i class="fa fa-plus"></i>
																</button>
															</div>
														</div>
													</div>
												</div>

                                            <div id="sample_editable_1_wrapper" class="dataTables_wrapper no-footer">
											<div class="table-scrollable">
											<table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="sample_editable_1">
                                        <thead>
                                        <tr>
					                            <th> <@my.message "homepage.newimg.sku"/></th>
					                            <th> <@my.message "base.layoutmodule.enabled"/></th>
					                        	<th> <@my.message "base.layoutmodule.createuser"/></th>
					                            <th> <@my.message "base.layoutmodule.createdate"/></th>
					                            <th> <@my.message "base.layoutmodule.updateuser"/> </th>
					                            <th> <@my.message "base.layoutmodule.updatedate"/></th>
					                            <th> <@my.message "page.operation"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>

										</tbody>
                                    </table>
                                    </div>
                                    </div>


                                    </div>
                                </div>
                                
                            <div class="tab-pane" id="tab_5_2">
                                <div class="portlet-body">
										<div class="table-toolbar">
													<div class="row">
														<div class="col-md-6">
															<div class="btn-group" id="addC">
																<button id="tt2" type="button" class="btn sbold green" data-toggle="modal" href="#staticC"> <i class="fa fa-plus"></i>
																</button>
															</div>
														</div>
													</div>
												</div>
                                            <div id="sample_editable_1_wrapper" class="dataTables_wrapper no-footer">
											<div class="table-scrollable">
											<table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="sample_editable_2">
                                        <thead>
                                        <tr>
					                            <th> <@my.message "homepage.newimg.country"/></th>
					                            <th> <@my.message "base.layoutmodule.enabled"/></th>
					                        	<th> <@my.message "base.layoutmodule.createuser"/></th>
					                            <th> <@my.message "base.layoutmodule.createdate"/></th>
					                            <th> <@my.message "base.layoutmodule.updateuser"/> </th>
					                            <th> <@my.message "base.layoutmodule.updatedate"/></th>
					                            <th> <@my.message "page.update"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>

										</tbody>
                                    </table>
                                    </div>
                                    </div>


                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END FOOTER -->
            </div>
        </div>
        <!-- END CONTAINER -->
      
      <div id="staticS" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "homepage.recent.sku.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="sku-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id" id="ids"/>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.client.name"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="client_idS" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list clientList as client>
																<option value=${client.id}>${client.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.language"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="language_idS" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list languageList as language>
																<option value=${language.id}>${language.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.sku"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="sku" class="form-control" required placeholder="">	
                                                        <input type="hidden" name="listing_id" />
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
                                                <button id="closeB" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button id="addBtnS" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
								
								
								
								<div id="staticC" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "homepage.recent.sku.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="country-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id" id="idC"/>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.client.name"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="client_idC" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list clientList as client>
																<option value=${client.id}>${client.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.language"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="language_idC" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list languageList as language>
																<option value=${language.id}>${language.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.country"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="country_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list countries as country>
					                                    	<option value=${country.id}>${country.name}</option>
					                                    </#list>
                                                        </select>
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
                                                <button id="closeC" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button id="addBtnC" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
      
         
<#include "/message.ftl">


<script src="/js/homemodul/rencently.js" type="text/javascript"></script>