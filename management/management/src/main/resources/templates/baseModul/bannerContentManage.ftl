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
        <li class="active"><@my.message "base.bannerContent.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <i class="fa fa-gift"></i><@my.message "base.bannerContent.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.client"/></label>
		                            <div class="col-md-3">
		                                <select id="client_id" class="form-control">
		                                	<option></option>
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
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.language"/></label>
		                            <div class="col-md-9">
		                                <select id="language_id" class="form-control">
		                                	<option></option>
		                                    <#list languageList as language>
		                                    	<option value=${language.id}>${language.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.layoutName"/></label>
		                            <div class="col-md-9">
		                                <select id="layout_id" class="form-control">
		                                	<option></option>
		                                    <#list layoutList as layout>
		                                    	<option value=${layout.code}>${layout.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.bannerName"/></label>
		                            <div class="col-md-9">
		                                <select id="bannerName" class="form-control">
		                                	<option></option>
		                                    <#list bannerList as banner>
		                                    	<option value=${banner.code}>${banner.name}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                </div>
		                <!--/row-->
		                <div class="row">
		                	<div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "base.bannerContent.category"/></label>
		                            <div class="col-md-9">
		                                <select id="firstCategory" class="form-control" style="width:31%;display:inline">
		                                	<option></option>
		                                    <#list rootCategoryList as rootCategory>
		                                    	<option value=${rootCategory.iid}>${rootCategory.cpath}</option>
		                                    </#list>
		                                </select>
		                                <select id="secondCategory" class="form-control" style="width:31%;display:inline">
		                                </select>
		                                <select id="thirdCategory" class="form-control" style="width:31%;display:inline">
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="row">
		                            <div class="col-md-offset-3 col-md-9">
		                                <button type="button" class="btn green" id="dataSearch"><@my.message "page.search"/></button>
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
                    <i class="icon-settings font-dark"></i>
                    <span class="caption-subject bold uppercase"><@my.message "base.bannerContent.listTitle"/></span>
                </div>
                <div class="actions">
                	<div class="row">
                        <div class="col-md-12">
                            <div class="btn-group">
                                <button id="addButton" class="btn sbold green"><i class="fa fa-plus"></i>
                                </button>
                            </div>
                            <div class="btn-group">
                            	<button id="deleteButton" class="btn sbold red"><i class="fa fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet-body">
	            <div class="table-scrollable">
	                <table class="table table-striped table-bordered table-hover" id="dataTable">
	                    <thead>
	                        <tr>
	                            <th>
	                                <input type="checkbox" class="group-checkable" id="mainCheckbox" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th><@my.message "base.bannerContent.client"/></th>
	                            <th><@my.message "base.bannerContent.language"/></th>
	                            <th><@my.message "base.bannerContent.category"/></th>
	                            <th><@my.message "base.bannerContent.layoutName"/></th>
	                            <th><@my.message "base.bannerContent.bannerName"/></th>
	                            <th><@my.message "base.bannerContent.bannerTitle"/></th>
	                            <th><@my.message "base.bannerContent.bannerUrl"/></th>
	                            <th><@my.message "base.bannerContent.bannerImage"/></th>
	                            <th><@my.message "base.bannerContent.sort"/></th>
	                            <th><@my.message "base.bannerContent.isEnabled"/></th>
	                            <th><@my.message "base.bannerContent.createuser"/></th>
	                            <th><@my.message "base.bannerContent.createdate"/></th>
	                            <th><@my.message "base.bannerContent.updateuser"/></th>
	                            <th><@my.message "base.bannerContent.updatedate"/></th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                </table>
	            </div>
	            <#include "/page.ftl">  
            </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
    </div>
</div>
   
<div id="bannerContentDiv" class="modal fade in">
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
	                            	<input type="file" name="file" class="input-lg">
	                            	<label id="bannerImgCheck" class="error" style="display:none;"><@my.message "base.bannerContent.bannerImageCheck"/></label>
	                            </div>
	                        </div>
                        </div>
                    </form>
                    <form id="bannerContentForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                    	<input type="hidden" name="img_url" id="imgUrl">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.client"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="client_id" class="form-control">
	                                	<option></option>
	                                    <#list clientList as client>
	                                    	<option value=${client.id}>${client.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.language"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="language_id" class="form-control">
	                                	<option></option>
	                                    <#list languageList as language>
	                                    	<option value=${language.id}>${language.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.category"/></label>
                                <div class="col-md-9">
                                    <select name="mcFirstCategory" class="form-control" style="width:32%;display:inline">
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
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.layoutName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="layout_code" class="form-control">
	                                	<option></option>
	                                    <#list layoutList as layout>
	                                    	<option value=${layout.code}>${layout.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <select name="banner_code" class="form-control">
                                	<option></option>
                                    <#list bannerList as banner>
                                    	<option value=${banner.code}>${banner.name}</option>
                                    </#list>
                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerContentName"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="name" class="form-control input-lg" maxlength="30"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerTitle"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="title" class="form-control input-lg" maxlength="30"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.bannerUrl"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="url" class="form-control input-lg" maxlength="30"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.bannerContent.sort"/></label>
                                <div class="col-md-9">
                                    <input type="number" name="sort" class="form-control input-lg">
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
                            <button type="button" id="saveCancel" class="btn default" data-dismiss="modal"><@my.message "page.cancel"/></button>
                            <button type="submit" id="save" class="btn green"><@my.message "page.save"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
	<a href="#bannerContentDiv" id="bannerContentShowButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="clientCheck" class="hide"><@my.message "base.bannerContent.clientCheck"/></span>
	<span id="languageCheck" class="hide"><@my.message "base.bannerContent.languageCheck"/></span>
	<span id="layoutNameCheck" class="hide"><@my.message "base.bannerContent.layoutNameCheck"/></span>
	<span id="bannerNameCheck" class="hide"><@my.message "base.bannerContent.bannerNameCheck"/></span>
	<span id="bannerContentNameCheck" class="hide"><@my.message "base.bannerContent.bannerContentNameCheck"/></span>
	<span id="bannerTitleCheck" class="hide"><@my.message "base.bannerContent.bannerTitleCheck"/></span>
</div>
<script src="/js/baseModul/bannerContentManage.js" type="text/javascript"></script>