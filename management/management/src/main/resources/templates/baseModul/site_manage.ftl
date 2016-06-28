<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/base"><@my.message "base.client.bread.module"/></a>
                            </li>
                            <li>
                                <a href="/base/baseLocalize"><@my.message "base.client.bread.local"/></a>
                            </li>
                            <li class="active"><@my.message "base.site.title"/></li>
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
										<label class="control-label col-md-4"><@my.message "base.client.site"/></label>
										<div class="col-md-8">
											<input type="text" id="name" class="form-control" placeholder=""> 
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
							<div class="actions">
								<div class="row">
								<div class="col-md-12">
									<div class="btn-group" id="addT">
										 <button id="ta" class="btn sbold green" data-toggle="modal" href="#static"><i class="fa fa-plus"></i></button>
									</div>
									<div class="btn-group" id="delT">
										 <button id="sample_editable_1_new" class="btn sbold red"> <i class="fa fa-trash"></i>
										 </button>
									 </div>
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
																<th> <@my.message "base.client.site"/></th>
																<th> <@my.message "base.site.description"/></th>
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
					 <#include "/page.ftl">
					 <!-- END EXAMPLE TABLE PORTLET-->
  	</div>
 </div>
 
           
            <div id="static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "base.site.content"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="id-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <div class="form-body">
                                            	<input type="hidden" name="id"/>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.client.site"/><span class="required">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="name" class="form-control" required placeholder="">	
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.site.description"/></label>
                                                    <div class="col-md-9">
                                                        <textarea name="description" id="de" class="form-control" placeholder="" ></textarea>
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
                                                <button type="button" id="closeB" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button type="submit" id="addBtn" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
            </div>
            <div>
            	<span id="nameRepeatCheck" class="hide">站点名称已存在！</span>
            </div>
            
<#include "/message.ftl">

<script src="/js/baseModul/site.js" type="text/javascript"></script>