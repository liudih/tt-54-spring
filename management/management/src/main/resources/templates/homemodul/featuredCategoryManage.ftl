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
        <li class="active"><@my.message "home.featuredCategory.title"/></li>
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
		                                <select id="language_id" class="bs-select form-control" multiple>
		                                    <#list languageList as language>
		                                    	<option value=${language.id}>${language.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
							<div class="col-md-3">
								<div class="form-group">
										<label class="control-label col-md-4"><@my.message "home.featuredCategory.goodsCategory"/></label>
										<div class="col-md-8">
											<select id="goods_category_id"  class="bs-select form-control" multiple >
												<#list rootCategoryList as rootCategory>
													<option value=${rootCategory.iid?c}>${rootCategory.cpath}</option>
												</#list>
											</select>
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
                    <span class="caption-subject bold uppercase"><@my.message "home.featuredCategory.contentList"/></span>
                </div>
                <div class="actions">
	            	<div class="row">
	                    <div class="col-md-12">
                             <div class="btn-group">
    	 	                        <a id="a_category"  href="/homepage/featurecategory/edit/0"  style="text-decoration: none;;color: white;">
		                                <button  class="btn sbold green" id="sample_editable_1_new">
		                                    <i class="fa fa-plus"></i>
		                                </button>
			                        </a>
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
                            <th><@my.message "home.featuredCategory.goodsCategory"/> </th>
                            <th><@my.message "base.layoutmodule.sort"/> </th>
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

<#include "/message.ftl">
<#include "/page.ftl">
<script src="/js/homemodul/featuredCategoryManage.js" type="text/javascript"></script>