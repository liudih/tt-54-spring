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
                            <li class="active"><@my.message "shipping.type.template.title"/></li>
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
				<input type="hidden" name="pageType" value="${type}"/>
				
				<div class="portlet-body">
					<form action="" class="form-horizontal">
						<div class="form-body">
							<div class="row">
								<!--/span-->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4"><@my.message "shipping.typename"/></label>
										<div class="col-md-8">
											<input type="text" id="type_name" class="form-control" placeholder=""> 
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
									<a href="/shipping/type/operation/0">
									<div class="btn-group" id="addT">
										 <button id="ta" class="btn sbold green" data-toggle="modal" href="#static"><i class="fa fa-plus"></i></button>
									</div>
									</a>
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
																<th> <@my.message "shipping.typename"/></th>
																<th> <@my.message "shipping.displayname"/></th>
																<th> <@my.message "shipping.description"/></th>
																<th> <@my.message "shipping.type.sort"/></th>
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
 
<script src="/js/shipping/shipping_type.js" type="text/javascript"></script>