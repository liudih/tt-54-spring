<!-- BEGIN BREADCRUMBS -->
<div class="breadcrumbs">
    <ol class="breadcrumb tx_l" style="float: left;margin-top:7px;">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/base"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="/base/baseDesign"><@my.message "page.baseDesign"/></a>
        </li>
        <li>
            <a href="/base/banner"><@my.message "base.banner.title"/></a>
        </li>
        <#if type == "add">
        	<li class="active"><@my.message "page.add"/></li>
        <#else>
        	<li class="active"><@my.message "page.update"/></li>
        </#if>
    </ol>
        <div class="row" style="float: right;margin-right:-20px;">
            <div class="col-md-12">
                <div class="btn-group">
                    <button class="btn sbold green" id="bannerSave"><i class="fa fa-save"></i>
                    </button>
                </div>
                <div class="btn-group">
                    <button class="btn sbold red" onclick="window.location.href='/base/banner';"><i class="icon-action-undo"></i>
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
            	<input type="hidden" id="bcIdValue" value="${id}">
            	<input type="hidden" id="typeValue" value="${type}">
                <!-- BEGIN FORM-->
                <form id="bannerForm" class="form-horizontal" method="post">
                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                	<input type="hidden" name="id">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.bannerContent.client"/><span class="required" aria-required="true"> * </span></label>
                                    <div class="col-md-9">
                                        <#if type == "add">
	                                        <select name="client_id" class="bs-select form-control" multiple>
			                                    <#list clientList as client>
			                                    	<option value=${client.id}>${client.name}</option>
			                                    </#list>
	                                        </select>
                                    	<#else>
                                    		<select name="client_id" class="form-control">
			                                    <#list clientList as client>
			                                    	<option value=${client.id}>${client.name}</option>
			                                    </#list>
	                                        </select>
                                		</#if>
                                		<label id="bannerClientCheck" class="error" style="display:none;"><@my.message "base.banner.bannerClientCheck"/></label>
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.bannerContent.language"/><span class="required" aria-required="true"> * </span></label>
                                    <div class="col-md-9">
                                    	<#if type == "add">
	                                        <select name="language_id" class="bs-select form-control" multiple>
	                                            <#list languageList as language>
			                                    	<option value=${language.id}>${language.name}</option>
			                                    </#list>
	                                        </select>
                                        <#else>
                                        	<select name="language_id" class="form-control">
	                                            <#list languageList as language>
			                                    	<option value=${language.id}>${language.name}</option>
			                                    </#list>
	                                        </select>
                                        </#if>
                                        <label id="bannerLanguageCheck" class="error" style="display:none;"><@my.message "base.banner.bannerLanguageCheck"/></label>
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.bannerContent.layoutName"/><span class="required" aria-required="true"> * </span></label>
                                    <div class="col-md-9">
                                        <select name="layout_code" class="form-control">
                                            <option></option>
		                                    <#list layoutList as layout>
		                                    	<option value=${layout.code}>${layout.name}</option>
		                                    </#list>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.banner.name"/><span class="required" aria-required="true"> * </span></label>
                                    <div class="col-md-9">
                                        <input type="text" name="name" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.banner.code"/><span class="required" aria-required="true"> * </span></label>
                                    <div class="col-md-9">
                                        <input type="text" name="code" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3"><@my.message "base.layoutmodule.position"/></label>
                                    <div class="col-md-9">
                                        <select name="position_id" class="form-control">
                                            <option></option>
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
                                    <label class="control-label col-md-3"><@my.message "base.bannerContent.isEnabled"/></label>
                                    <div class="col-md-9">
                                        <select name="is_enabled" class="form-control">
                                        	<option></option>
                                            <option value="1"><@my.message "page.enabled"/></option>
	                                    	<option value="0"><@my.message "page.disabled"/></option>
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
                            <a aria-expanded="false" href="#tab_5_1" data-toggle="tab"><@my.message "base.bannerContent.navigation"/></a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab_5_1">
                            <div class="portlet-body">
                                <div class="table-toolbar">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="btn-group">
                                                <button class="btn green" id="addBannerContent"><i class="fa fa-plus"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div  class="dataTables_wrapper no-footer">
                                    <div class="table-scrollable">
                                        <table aria-describedby="sample_editable_1_info" role="grid" class="table table-striped table-hover table-bordered dataTable no-footer" id="dataTable">
                                            <thead>
                                            <tr role="row">
                                                <th class="bcCategory"><@my.message "base.bannerContent.category"/></th>
                                                <th><@my.message "base.bannerContent.bannerName"/></th>
                                                <th><@my.message "base.bannerContent.bannerTitle"/></th>
                                                <th><@my.message "base.bannerContent.bannerUrl"/></th>
                                                <th><@my.message "base.bannerContent.bannerImage"/></th>
                                                <th><@my.message "base.bannerContent.sort"/></th>
                                                <th><@my.message "base.bannerContent.isEnabled"/></th>
                                                <th><@my.message "page.operation"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                    <#if type == "update">
                                    	<#include "/page.ftl">
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END 广告内容查询 -->
<!-- BEGIN 弹出层 -->
<!-- 添加SKU弹出框 -->
<div id="bannerContentDiv" class="modal fade in" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "base.bannerContent.formTitle"/></h4>
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
	                            	<img id="bcImgSrc" width="50%" src=""/>
	                            	<input type="file" name="file">
	                            	<label id="bannerImgCheck" class="error" style="display:none;"><@my.message "base.bannerContent.bannerImageCheck"/></label>
	                            </div>
	                        </div>
                        </div>
                    </form>
                    <form id="bannerContentForm" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                        <div class="form-body">
                        	<input type="hidden" id="bcId">
                        	<input type="hidden" id="imgUrl" name="img_url">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.category"/></label>
                                <div class="col-md-9">
                                    <select name="mcFirstCategory" class="form-control error" style="width:32%;display:inline">
                                        <option></option>
                                        <#list rootCategoryList as rootCategory>
	                                    	<option value=${rootCategory.iid}>${rootCategory.cpath}</option>
	                                    </#list>
                                    </select>
                                    <select id="mcSecondCategory" class="form-control" style="width:32%;display:inline">
                                    </select>
                                    <select id="mcThirdCategory" class="form-control" style="width:32%;display:inline">
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" id="categoryValue" name="category_id">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerName"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerTitle"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="title" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerUrl"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="url" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.sort"/></label>
                                <div class="col-md-9">
                                    <input type="number" name="sort" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.isEnabled"/></label>
                                <div class="col-md-9">
                                    <select name="is_enabled" class="form-control">
                                        <option></option>
                                        <option value="1"><@my.message "page.enabled"/></option>
	                                    <option value="0"><@my.message "page.disabled"/></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" data-dismiss="modal" id="bcAddCancel" class="btn"><@my.message "page.cancel"/></button>
                            <button type="submit" id="bcSave" class="btn green"><@my.message "page.confirm"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END 弹出层 -->
<div>
	<div id="repeatCodeAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "base.banner.codeRepeat"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#bannerContentDiv" id="bannerContentShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#repeatCodeAlert" id="repeatCodeButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="bannerContentNameCheck" class="hide"><@my.message "base.bannerContent.bannerContentNameCheck"/></span>
	<span id="bannerTitleCheck" class="hide"><@my.message "base.bannerContent.bannerTitleCheck"/></span>
	<span id="bannerUrlCheck" class="hide"><@my.message "base.bannerContent.bannerUrlCheck"/></span>
	<span id="bannerNameCheck" class="hide"><@my.message "base.banner.bannerNameCheck"/></span>
	<span id="bannerCodeCheck" class="hide"><@my.message "base.banner.bannerCodeCheck"/></span>
	<span id="bannerLayoutCheck" class="hide"><@my.message "base.banner.bannerLayoutCheck"/></span>
</div>
<script src="/js/baseModul/addBanner.js" type="text/javascript"></script>