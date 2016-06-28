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
        <li class="active"><@my.message "home.newsReview.title"/></li>
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
					<div class="form-body">
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "base.client.name"/></label>
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
									<label class="control-label col-md-3"><@my.message "base.language.name"/></label>
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
									<label class="control-label col-md-4"><@my.message "home.newsReview.country"/></label>
									<div class="col-md-8">
										<select id="country_id"  class="bs-select form-control" multiple>
											<#list countryList as country>
												<option value=${country.name}>${country.name}</option>
											</#list>
										</select>
									</div>
								</div>
							</div>
							 <div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "home.newsReview.commentator"/></label>
									<div class="col-md-9">
										<input id="newsReview_name_id" placeholder="<@my.message "home.newsReview.commentator"/>" class="form-control">
									</div>
								</div>
							</div>
							 <div class="col-md-3">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "home.detail.deal.sku"/></label>
									<div class="col-md-9">
										<input id="newsReview_sku_id" placeholder="<@my.message "home.detail.deal.sku"/>" class="form-control">
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
                    <span class="caption-subject bold uppercase"><@my.message "home.newsReview.content.list"/></span>
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
								<th><@my.message "page.operation"/></th>
								<th> <@my.message "base.layoutmodule.client"/> </th>
								<th> <@my.message "base.layoutmodule.language"/> </th>
								<th><@my.message "home.detail.deal.sku"/> </th>
								<th><@my.message "page.title"/></th>
								<th><@my.message "home.newsReview.contents"/> </th>
								<th> <@my.message "home.newsReview.commentator"/></th>
								<th><@my.message "home.newsReview.country"/> </th>
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
<div id="myModal"  class="modal fade in">	
  	<div class="modal-dialog">
	  	<div class="modal-content">
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title" id="newestReview_title_id"></h4>
	        </div>	
	  		<div class="modal-body">
                <div class="portlet light form no-space">
					<form class="form-horizontal" id="newestReview_form_Id" role="form" method="POST">

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
                                <label class="col-md-3 control-label"><@my.message "page.title"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
									<input type="text" name="title" placeholder="<@my.message "home.newsReview.input.title"/>" class="form-control">
                             	   <span style="color: red;font-weight: bolder;"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
	                                <@my.message "home.detail.deal.sku"/>
	                                <span class="required" aria-required="true"> * </span>
                                </label>
                                <div class="col-md-9">
                                	<input id="list_id" type="hidden">
									<input id="sku_validate_id" type="text" name="sku" placeholder="<@my.message "home.detail.deal.input.sku"/>" class="form-control">
                             	   <span style="color: red;font-weight: bolder;"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                	<@my.message "home.newsReview.contents"/>
                                	<span class="required" aria-required="true"> * </span>
                                </label>
                                <div class="col-md-9">
									<textarea class="form-control" name="review_content" maxlength="2000" placeholder="<@my.message "home.newsReview.input.contents"/>"></textarea>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label">
                                	<@my.message "home.newsReview.commentator"/>
                               		 <span class="required" aria-required="true"> * </span>
                                </label>
								<div class="col-md-9">
		                			<input type="text" name="review_by" placeholder="<@my.message "home.newsReview.input.commentator"/>" class="form-control">
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-md-3 control-label">
                                	<@my.message "home.newsReview.country"/>
                                	<span class="required" aria-required="true"> * </span>
                                </label>
                                <div class="col-md-9">
									<select name="country" class="form-control">
									   <option value=""></option>
										<#list countryList as country>
											<option value=${country.name}>${country.name}</option>
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
							<button id="add_newestReview_close" class="btn default" data-dismiss="modal" aria-hidden="true"><@my.message "page.close"/></button>
							<input   class="btn green" type="submit" value="<@my.message "page.save"/>">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<a href="#checkSkuExit" id="checkIsSkuExit" class="btn hide" data-toggle="modal"></a>
<div id="checkSkuExit" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.newsReview.sku.noexit"/></p>
    </div>
</div>

<button style="display:none" id="newestReview_edit_data" data-toggle="modal" data-target="#myModal"></button>
<script src="/js/homemodul/newestReviewManage.js" type="text/javascript"></script>
