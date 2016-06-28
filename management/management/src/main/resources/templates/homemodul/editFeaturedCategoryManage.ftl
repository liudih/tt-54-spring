<div class="breadcrumbs">
	<ol class="breadcrumb tx_l" style="float: left;margin-top:7px;">
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
		<div class="row" style="float: right;margin-right:-20px;">
			<div class="col-md-12">
				<div class="btn-group">
					<button class="btn sbold green" data-toggle="modal" href="#static" id="save_all_data"><i class="fa fa-save"></i>
					</button>
				</div>
				<div class="btn-group">
					<button class="btn sbold red" id="close_return"><i class="icon-action-undo"></i>
					</button>
				</div>
			</div>
		</div>
</div>
<!-- END BREADCRUMBS -->
<!-- BEGIN 广告内容查询 -->
<div class="row">
	<div class="col-md-12">

		<div class="portlet green box">
			<div class="portlet-title">
				<div class="caption">
				<#if edit='add'>
					<@my.message "home.featuredCategory.add.title"/>
				<#elseif  edit='edit'>
					<@my.message "home.featuredCategory.edit.title"/>
				</#if>
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
				</div>
			</div>
			<div class="portlet-body ">
				<!-- BEGIN FORM-->
				<div class="form-body">
					<form action="#" class="form-horizontal" id="featured_category_form_Id">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "base.modulContent.client"/>
										<span class="required" aria-required="true"> * </span>
									</label>
									<div class="col-md-9">
										<#if edit='add'>
											<input type="hidden" id="edit_data_id" value="${edit}">
											  <select name="client_id" class="bs-select form-control" multiple>
												<#list clientList as client>
													<option value=${client.id}>${client.name}</option>
												</#list>
											</select>
										<#elseif  edit='edit'>
											<input type="hidden" id="edit_data_id" value="${edit}">
											<select name="client_id" class="form-control"disabled="disabled">
												<#list clientList as client>
													<option value=${client.id}>${client.name}</option>
												</#list>
											</select>
										</#if>
										<span id="client_id_error" style="color: red;font-weight: bolder;"></span>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-6">
								<div class="form-group">
								<label class="control-label col-md-3">
									<@my.message "base.modulContent.language"/>
									<span class="required" aria-required="true"> * </span>
								</label>
								<div class="col-md-9">
									<input id="edit_ids" name="id" value=${iid?c} type="hidden">
										<#if edit='add'>
											<select name="language_id" class="form-control">
												<option></option>
												<#list languageList as language>
													<option value=${language.id}>${language.name}</option>
												</#list>
											</select>
										<#elseif  edit='edit'>
											<select name="language_id" class="form-control" disabled="disabled">
												<option></option>
												<#list languageList as language>
													<option value=${language.id}>${language.name}</option>
												</#list>
											</select>
										</#if>

								</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3">
										<@my.message "home.featuredCategory.goodsCategory"/>
										<span class="required" aria-required="true"> * </span>		
									</label>
									<div class="col-md-9">
										<#if edit='add'>
										<select name="category_id" class="form-control">
											<option></option>
											<#list rootCategoryList as rootCategory>
													<option value=${rootCategory.iid?c}>${rootCategory.cpath}</option>
											</#list>
										</select> 
										<#elseif  edit='edit'>
											<select name="category_id" class="form-control" disabled="disabled">
												<option></option>
												<#list rootCategoryList as rootCategory>
														<option value=${rootCategory.iid?c}>${rootCategory.cpath}</option>
												</#list>
											</select> 
										</#if>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "home.featuredCategory.goodsCategory.URL"/></label>
									<div class="col-md-9">
										<input type="text"  name="img_url" placeholder="<@my.message "home.featuredCategory.input.goodsCategory.URL"/>" class="form-control">
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "home.featuredCategory.number"/></label>
									<div class="col-md-9">
										<input type="number"  name="number" placeholder="<@my.message "home.featuredCategory.input.number"/>"  class="form-control valid" aria-invalid="false">
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3"><@my.message "base.layoutmodule.sort"/></label>
									<div class="col-md-9">
											<input type="number" name="sort" placeholder="<@my.message "base.currency.input.sort"/>"  class="form-control valid" aria-invalid="false">
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-3">
										<@my.message "base.layoutmodule.enabled"/>
									</label>
									<div class="col-md-9">
										<input type="hidden" name="is_deleted" value="0">
										<select name="is_enabled" class="form-control">
											<option value="1">Enabled</option>						
											<option value="0">Disabled</option>
										</select>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<input type="submit" value="保存" style="display:none"  id="submit_All_data">
					</form>
					<div class="row margin-top-10">
						<div class="col-md-12 bg-white">
							<div class="tabbable-custom ">
								<ul class="nav nav-tabs ">
									<li class="active">
										<a aria-expanded="true" href="#tab_5_1" data-toggle="tab">
											<@my.message "home.featuredCategory.sku.manage"/>
										 </a>
									</li>
									<li class="">
										<a aria-expanded="false" href="#tab_5_2" data-toggle="tab">
											<@my.message "home.featuredCategory.key.manage"/>
										 </a>
									</li>
									<li class="">
										<a aria-expanded="false" href="#tab_5_3" data-toggle="tab">
											<@my.message "home.featuredCategory.banner.manage"/>
										 </a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="tab_5_1">
										<div class="portlet-body">
											<div class="table-toolbar">
												<div class="row">
													<div class="col-md-6">
														<div class="btn-group">
															<button id="button_sku"  class="btn green" data-toggle="modal" data-target="#static01"><i class="fa fa-plus"></i>
															</button>
														</div>
													</div>
												</div>
											</div>

											<div class="dataTables_wrapper no-footer">
												<div class="table-scrollable">
													<table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="sample_editable_1">
														<thead>
														<tr role="row">
															<th><@my.message "base.modulContent.sku"/></th>
															<th><@my.message "base.modulContent.sort"/></th>
															<th><@my.message "base.modulContent.isEnabled"/></th>
															<th><@my.message "page.operation"/></th>
														</tr>
														</thead>
														<tbody id="sku_data_tbody">
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
														<div class="btn-group">
															<button id="button_key" class="btn green" data-toggle="modal" data-target="#static02"><i class="fa fa-plus"></i>
															</button>
														</div>
													</div>
												</div>
											</div>

											<div id="" class="dataTables_wrapper no-footer">
												<div class="table-scrollable">
													<table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="sample_editable_1">
														<thead>
														<tr role="row">
															<th><@my.message "home.featuredCategory.key.keyword"/></th>
															<th><@my.message "base.modulContent.sort"/></th>
															<th><@my.message "base.modulContent.isEnabled"/></th>
															<th><@my.message "page.operation"/></th>
														</tr>
														</thead>
														<tbody id="key_data_tbody">
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane" id="tab_5_3">
										<div class="portlet-body">
											<div class="table-toolbar">
												<div class="row">
													<div class="col-md-6">
														<div class="btn-group">
															<button id="button_banner" class="btn green" data-toggle="modal" data-target="#static03"><i class="fa fa-plus"></i>
															</button>
														</div>
													</div>
												</div>
											</div>

											<div id="sample_editable_1_wrapper" class="dataTables_wrapper no-footer">
												<div class="table-scrollable">
													<table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="sample_editable_1">
														<thead>
														<tr role="row">
															<th><@my.message "page.title"/></th>
															<th><@my.message "home.featuredCategory.banner.img"/></th>
															<th><@my.message "base.bannerContent.bannerUrl"/></th>
															<th><@my.message "base.layoutmodule.position"/></th>
															<th><@my.message "base.modulContent.sort"/></th>
															<th><@my.message "base.modulContent.isEnabled"/></th>
															<th><@my.message "page.operation"/></th>
														</tr>
														</thead>
														<tbody id="banner_data_tbody">
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- END FORM-->
			</div>
		</div>
	</div>
</div>
<!-- END 广告内容查询 -->
<!-- BEGIN 弹出层 -->
<!-- 添加SKU弹出框 -->
<div id="static01" class="modal fade in" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title" id="sku_add_title"></h4>
			</div>
			<div class="modal-body">
				<div class="portlet light form no-space">

					<form id="sku_form_Id" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "base.modulContent.sku"/></label>
								<div class="col-md-9">
									<input type="hidden" id="list_id">
									<input type="hidden" id="sku_name_id">
									<input type="hidden" id="num">
									<input id="sku_is_delete_id" type="hidden" value="0">
									<input type="text" placeholder="<@my.message "home.detail.deal.input.sku"/>" class="form-control" id="sku_id" name="sku">
									<span style="color: red;font-weight: bolder;"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label" ><@my.message "base.modulContent.sort"/></label>
								<div class="col-md-9">
									<input type="number" id="sku_sort_id" name="sku_sort" placeholder="<@my.message "base.currency.input.sort"/>"  class="form-control valid" aria-invalid="false">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label" ><@my.message "base.modulContent.isEnabled"/></label>
								<div class="col-md-9">
									<select id="sku_is_enabled" class="form-control">
										<option value="1">Enabled</option>						
										<option value="0">Disabled</option>
									</select>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button id="close_sku_id" type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
							<button id="sku_submit_id“ data-dismiss="modal" class="btn green" onclick="addRow()" ><@my.message "page.confirm"/></button>
						</div>
					</form>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- 添加热门搜索关键字弹出框 -->
<div id="static02" class="modal fade in" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title" id="key_add_title"></h4>
			</div>
			<div class="modal-body">
				<div class="portlet light form no-space">

					<form id="key_form_Id" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "homepage.hotsearch.keyword"/></label>
								<input id="keynum" type="hidden">
								<input id="key_name_id" type="hidden">
								<input id="key_is_delete_id" type="hidden" value="0">
								<div class="col-md-9">
									<input name="keyword_id" id="keyword_id" type="text" placeholder="<@my.message "homepage.hotsearch.keyword"/>" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-md-3 control-label"><@my.message "base.modulContent.sort"/></label>
								<div class="col-md-9">
									<input name="sort_key_id" id="key_sort_id" type="number" placeholder="<@my.message "base.currency.input.sort"/>"  class="form-control valid" aria-invalid="false">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label" ><@my.message "base.modulContent.isEnabled"/></label>
								<div class="col-md-9">
									<select id="key_is_enabled" class="form-control">
										<option value="1">Enabled</option>						
										<option value="0">Disabled</option>
									</select>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button  id="close_key_id" type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
							<button id="button_key_id"   class="btn green" onclick="addKeyRow()" ><@my.message "page.confirm"/></button>
						</div>
					</form>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- 添加banner弹出框 -->
<div id="static03" class="modal fade in" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title" id="banner_add_title"></h4>
			</div>
			<div class="modal-body">
				<div class="portlet light form no-space">
                	<form id="imgUploadForm" action="/image/upload" method="post" enctype="multipart/form-data" class="form-horizontal cmxform">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-body">
	                        <input type="hidden" name="token" value="${imageUploadToken}">
	                        <div class="form-group">
	                            <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerImage"/></label>
	                            <div class="col-md-9">
	                            	<input id="upload_file" type="file" name="file" class="input-lg">
			                        <img id="banner_img_id" width="150" height="120" src="">
	                            	<label id="bannerImgCheck" class="error" style="display:none;"><@my.message "base.bannerContent.bannerImageCheck"/></label>
	                            </div>
	                        </div>
                        </div>
                    </form>
					<form id="banner_form_Id" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="img_url" id="imgUrl">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "page.title"/></label>
								<div class="col-md-9">
									<input type="hidden" id="banner_name_id">
									<input type="hidden" id="bannernum">
									<input id="banner_is_delete_id" type="hidden" value="0">
									<input type="text" name="title_id" id="title_id" placeholder="<@my.message "home.newsReview.input.title"/>" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerUrl"/></label>
								<div class="col-md-9">
									<input type="text" name="url_id" placeholder="<@my.message "home.featuredCategory.banner.input.url"/>" id="url_id" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "base.layoutmodule.position"/></label>
								<div class="col-md-9">
									<select class="form-control" name="param_id" id="param_id">
	                                    <#list parameters as param>
	                                    	<option value=${param.value?c}>${param.name}</option>
	                                    </#list>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><@my.message "base.modulContent.sort"/></label>
								<div class="col-md-9">
									<input type="number" name="sort_banner_id" id="sort_banner_id" placeholder="<@my.message "base.currency.input.sort"/>"  class="form-control valid" aria-invalid="false">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label" ><@my.message "base.modulContent.isEnabled"/></label>
								<div class="col-md-9">
									<select id="banner_is_enabled" class="form-control">
										<option value="1">Enabled</option>						
										<option value="0">Disabled</option>
									</select>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button id="close_banner_id" type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
							<button class="btn green" onclick="addBannerRow()"><@my.message "page.confirm"/></button>
						</div>
					</form>
				</div>
			</div>

		</div>
	</div>

</div>
<a href="#checkClientAndLanguageAndcategoryIdMessage" onclick="closeCategoryBtn()" id="checkClientAndLanguageAndLanguageMessageButton" class="btn hide" data-toggle="modal"></a>
<div id="checkClientAndLanguageAndcategoryIdMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="closeCategoryid" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.featuredCategory.check.client.language.category.mess"/></p>
    </div>
</div>

<a href="#checkSkuMessage"  id="checkSkuRepeatAdd" class="btn hide" data-toggle="modal"></a>
<div id="checkSkuMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="closeSkuId" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.featuredCategory.check.sku"/></p>
    </div>
</div>

<a href="#checkSkuExistMessage" onclick="closeSkuExistBtn()" id="checkExistSku" class="btn hide" data-toggle="modal"></a>
<div id="checkSkuExistMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
	<div class="alert alert-block alert-info fade in">
        <button id="closeExistSkuId" stype="display:none" type="button" class="close" data-dismiss="modal"></button>
        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
        <p><@my.message "home.featuredCategory.check.sku.exist"/></p>
    </div>
</div>
<span id="numberCheck" class="hide"><@my.message "base.number.check"/></span>
<script src="/js/homemodul/featuredCategoryManage.js" type="text/javascript"></script>
