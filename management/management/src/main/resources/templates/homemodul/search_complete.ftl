<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/indexConfig"><@my.message "homepage.hotsearch.bread.module"/></a>
                            </li>
                            <li>
                                <a href="/indexConfig/search"><@my.message "homepage.hotsearch.bread.search"/></a>
                            </li>
                            <li class="active"><@my.message "homepage.complete.title"/></li>
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
                    <!--/span-->
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
                    <!--/span-->
                    <!--/span-->
                     <div class="col-md-4">
                        <div class="form-group">
                            <label class="control-label col-md-4"><@my.message "homepage.hotsearch.hotkey"/></label>
                            <div class="col-md-8">
                            	<input type="text" id="keyword" class="form-control">
                            </div>
                        </div>
                    <!--/span-->
					</div>
					</div>
                <div class="row" style="margin-left:15px;">
                	<div class="col-md-5">
		                	<button id="searchS" type="button" class="btn green"><i class="fa fa-search"></i></button>
		                	<a href="#imp" data-toggle="modal"><button id="im" class="btn green"><i class="fa fa-sign-in"></i></button></a>
		                	<button id="tong" type="button" class="btn green"><i class="fa fa-refresh"></i></button>
		                	<span id="t" style="display:none;"><@my.message "homepage.complete.synchronized.mess"/></span>
		                	<span id="d" style="display:none;"><@my.message "homepage.complete.improt.mess"/></span>
		                	<a href="/samples/keyword_sample.xls"><@my.message "homepage.complete.click.mess"/></a>
                	</div>
                </div>
			</div>
           </form>
           <form id="fileForm" class="form-horizontal cmxform" role="form" method="post" enctype="multipart/form-data">
	           <div id="imp" class="modal fade" role="dialog" style="margin:auto;width:350px;height:200px;background-color: white;">
	  				<div class="modal-body" style="auto;">
	  					<div class="control-group">
		  				<input type="file" name="excel" accept=".xls,.xlsx" />
		  				</div><br/>
		  				<span class="btn-group" style="margin-left:22px;">
		  				<button type="button" id="upload" class="btn blue"><@my.message "homepage.complete.import.key"/></button>
		  				</span>
		  				<span class="btn-group" style="margin-left:22px;">
			                <button id="closeB" class="btn blue" data-dismiss="modal"> <@my.message "page.close"/>
			                </button>
			            </span>
	  				</div>
	  			</div>
  			</form>
  			</div>
  </div>
  <div class="row">
    <div class="col-md-12">
        <!-- BEGIN EXAMPLE TABLE PORTLET-->
        <div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption font-dark">
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject bold uppercase"><@my.message "base.layoutmodule.list"/></span>
                </div>
                <div class="actions">
                	<div class="row">
                		<div class="btn-group" id="addT">
			                     <button id="ta" class="btn sbold green" data-toggle="modal" href="#static"> <i class="fa fa-plus"></i>
			                     </button>
		                </div>
		                <div class="btn-group" id="delT">
		                     <button id="sample_editable_1_new" class="btn sbold red"> <i class="fa fa-trash"></i>
		                     </button>
		                 </div>
	                 </div>
                </div>
            </div>
           <div class="portlet-body">
           <div class="table-scrollable">
           <table class="table table-striped table-bordered table-hover table-checkable order-column" id="bodyT">
                                        <thead>
                                            <tr>
                                                <th>
                                                	<span>
                                                		<input type="checkbox" id="mcMainCheckbox" class="group-checkable" data-set="#bodyT .checkboxes" />
                                                	</span>
                                                </th>
                                                <th> <@my.message "page.operation"/></th>
                                                <th> <@my.message "base.client.name"/></th>
					                            <th> <@my.message "base.layoutmodule.language"/></th>
					                            <th> <@my.message "homepage.hotsearch.hotkey"/></th>
					                            <th> <@my.message "base.layoutmodule.enabled"/></th>
					                        	<th> <@my.message "base.layoutmodule.createuser"/></th>
					                            <th> <@my.message "base.layoutmodule.createdate"/></th>
					                            <th> <@my.message "base.layoutmodule.updateuser"/> </th>
					                            <th> <@my.message "base.layoutmodule.updatedate"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
             </table>
             </div>
             </div>
         </div>
         <!-- END EXAMPLE TABLE PORTLET-->
         <#include "/page.ftl">
  </div>
  </div>
  </div>
  </div>
  	
  	<div id="static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "homepage.complete.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="complete-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id"/>
												<div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.client.name"/><span class="required" aria-required="true"> * </span></label>
                                                    <div class="col-md-9">
                                                        <select name="client_id" class="form-control" aria-required="true" aria-invalid="true">
                                                            <#list clientList as client>
																<option value=${client.id}>${client.name}</option>
															</#list>
                                                        </select>
                                                    </div>
                                                </div>
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
                                                    <label class="col-md-3 control-label"><@my.message "homepage.hotsearch.keyword"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="keyword" class="form-control" required placeholder="">	
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
                                                <button type="button" id="closeSC" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button type="submit" id="addBtn" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
			
<#include "/message.ftl">

<script src="/js/homemodul/search_complete.js" type="text/javascript"></script>