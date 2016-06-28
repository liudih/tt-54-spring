<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l" style="float: left;margin-top:7px;">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/base"><@my.message "base.client.bread.module"/></a>
                            </li>
                            <li>
                                <a href="/base/baseDesign"><@my.message "base.client.bread.design"/></a>
                            </li>
                            <li>
                                <a href="/base/layoutModule"><@my.message "base.recommend.title"/></a>
                            </li>
                            <#if type == "add">
					        	<li class="active"><@my.message "page.add"/></li>
					        <#else>
					        	<li class="active"><@my.message "page.update"/></li>
					        </#if>
                        </ol>
                            <div class="row" style="float: right;margin-right:-20px;">
                                <div class="col-md-12">
                                    <div class="btn-group" id="addT">
                                        <button class="btn sbold green"><i class="fa fa-save"></i>
                                        </button>
                                    </div>
                                    <div class="btn-group">
                                    	<a href="/base/layoutModule">
                                        <button class="btn sbold red"><i class="icon-action-undo"></i>
                                        </button>
                                        </a>
                                    </div>
                                </div>
                            </div>
</div>
<div class="row">
                        <div class="col-md-12">
							<input type="hidden" name="pageType" value="${type}"/>
                            <div class="portlet green box">
                                <div class="portlet-title">
                                    <#if type == "add">
					                	<div class="caption"><@my.message "page.add"/></div>
					                <#else>
					                	<div class="caption"><@my.message "page.update"/></div>
					                </#if>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                                    </div>
                                </div>
                                <div class="portlet-body ">
                                    <!-- BEGIN FORM-->
                                    <form id="id-form" method="post" class="form-horizontal">
                                        <div class="form-body">
                                        	<input type="hidden" name="id" value="${id}"/>
                                        	
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.client"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-9">
                                                        <#if type == "add">
                                                            <select class="bs-select form-control" id="clients2" name="client_id" multiple>
                                                                <#list clientList as client>
							                                    	<option value=${client.id}>${client.name}</option>
							                                    </#list>
                                                            </select>
                                                            <#else>
                                                            <select class="form-control" id="clients2" name="client_id">
                                                                <#list clientList as client>
							                                    	<option value=${client.id}>${client.name}</option>
							                                    </#list>
                                                            </select>
                                                        </#if>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.language"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-9">
                                                        <#if type == "add">
                                                            <select class="bs-select form-control" id="languages2" name="language_id" multiple>
                                                            	<#list languageList as language>
							                                    	<option value=${language.id}>${language.name}</option>
							                                    </#list>
                                                            </select>
                                                            <#else>
                                                            <select class="form-control" id="languages2" name="language_id">
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
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.layoutName"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-9">
                                                            <select class="form-control" id="layout_id2" name="layout_id">
							                                    <#list layoutList as layout>
							                                    	<option value=${layout.id}>${layout.name}</option>
							                                    </#list>
							                                </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.recommend.name"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-9">
                                                            <input type="text" id="name" name="name" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.moduleCode"/><span class="required" aria-required="true"> * </span></label>
                                                        <div class="col-md-9">
                                                            <input type="text" id="code" name="code" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.position"/></label>
                                                        <div class="col-md-9">
                                                            <select id="position_id" name="position_id" class="form-control">
                                                                <#list paramters as p>
																	<option value=${p.value}>${p.name}</option>
																</#list>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.url"/></label>
                                                        <div class="col-md-9">
                                                            <input id="url" name="url" type="text" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.number"/></label>
                                                        <div class="col-md-9">
                                                            <input  type="number" name="number" id="number" placeholder="<@my.message "home.featuredCategory.input.number"/>" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.sort"/></label>
                                                        <div class="col-md-9">
                                                            <input type="number" name="sort" id="sort" placeholder="<@my.message "base.currency.input.sort"/>" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.enabled"/></label>
                                                        <div class="col-md-9">
                                                            <select name="is_enabled" id="is_enabled" class="form-control error" aria-required="true">
						                                    	<option value="1">Enabled</option>
						                                    	<option value="0">Disabled</option>
						                                	</select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                            </div>

                                        </div>
                                    </form>

                                    <!-- END FORM-->
                                </div>

                            </div>
                            <div class="row margin-top-30">
                                <div class="col-md-12 bg-white">
                                    <div class="tabbable-custom ">
                                        <ul class="nav nav-tabs ">
                                            <li class="active">
                                                <a aria-expanded="false" href="#tab_5_1" data-toggle="tab"><@my.message "base.layoutmodule.manage"/></a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="tab_5_1">
                                                <div class="portlet-body">
                                                    <div class="table-toolbar">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="btn-group" id="addB">
                                                                    <button class="btn green" data-toggle="modal" data-target="#static01"><i class="fa fa-plus"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="dataTables_wrapper no-footer">
                                                        <div class="table-scrollable">
                                                            <table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer"  id="skuBody">
                                                                <thead>
                                                                <tr role="row">
                                                                    <th><@my.message "homepage.newimg.sku"/></th>
                                                                    <th><@my.message "base.layoutmodule.sort"/></th>
                                                                    <th><@my.message "base.layoutmodule.enabled"/></th>
                                                                    <th><@my.message "page.operation"/></th>
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
                            </div>
                        </div>
</div>
<div id="static01" data-backdrop="static" class="modal fade in">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "base.recommend.skuma"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">

                                        <form id="sku-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <input type="hidden" id="skuId"/>
                                            <div class="form-body">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "homepage.newimg.sku"/></label>
                                                    <div class="col-md-9">
                                                        <input type="text" name="sku" placeholder="<@my.message "home.detail.deal.input.sku"/>" class="form-control">
                                                        <input type="hidden" name="listingid"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.sort"/></label>
                                                    <div class="col-md-9">
                                                        <input type="number" name="sort" id="sort2" placeholder="<@my.message "base.currency.input.sort"/>" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.enabled"/></label>
                                                    <div class="col-md-9">
                                                        <select name="is_enabled" id="is_enabled2" class="form-control error" aria-required="true">
						                                    	<option value="1">Enabled</option>
						                                    	<option value="0">Disabled</option>
						                                </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button id="cancle" type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button id="sure" type="submit" class="btn green"><@my.message "page.confirm"/></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
</div>
<span id="numberCheck" class="hide"><@my.message "base.number.check"/></span>
<script src="/js/baseModul/recommend.js" type="text/javascript"></script>