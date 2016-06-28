<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/base"><@my.message "page.baseModul"/></a>
        </li>
        <li>
            <a href="/base/baseLocalize"><@my.message "page.localize"/></a>
        </li>
        <li class="active"><@my.message "base.country.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "base.country.searchTitle"/>
		        </div>
		        <div class="tools">
                    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                </div>
		    </div>
			<div class="portlet-body form">
		        <!-- BEGIN FORM-->
		        <form action="#" class="form-horizontal">
		            <div class="form-body">
		                <!--/row-->
		                <div class="row">
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "base.country.name"/></label>
		                            <div class="col-md-8">
		                                <input type="text" id="countryName" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-1 text-right">
                                <button type="button" id="dataSearch" class="btn green "><i class="fa fa-search"></i></button>
                            </div>
		                </div>
		                <!--/row-->
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
                    <span class="caption-subject bold uppercase"><@my.message "base.country.listTitle"/></span>
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
	                <table class="table table-striped table-bordered table-hover table-checkable order-column" id="dataTable">
	                    <thead>
	                        <tr>
	                            <th><input type="checkbox" class="group-checkable" id="mainCheckbox" /> </th>
	                            <th><@my.message "page.operation"/></th>
	                            <th><@my.message "base.country.language"/></th>
	                            <th><@my.message "base.country.name"/></th>
	                            <th>ISOCode(2)</th>
	                            <th>ISOCode(3)</th>
	                            <th><@my.message "base.country.nationalFlag"/></th>
	                            <th><@my.message "base.country.addressFormat"/></th>
	                            <th><@my.message "base.country.isRequiredPostcode"/></th>
	                            <th><@my.message "base.country.currency"/></th>
	                            <th><@my.message "base.country.officialLanguage"/></th>
	                            <th><@my.message "base.country.lengthUnit"/></th>
	                            <th><@my.message "base.country.weigthUnit"/></th>
	                            <th><@my.message "base.country.sort"/></th>
	                            <th><@my.message "base.country.isEnabled"/></th>
	                            <th><@my.message "base.country.createuser"/></th>
	                            <th><@my.message "base.country.createdate"/></th>
	                            <th><@my.message "base.country.updateuser"/></th>
	                            <th><@my.message "base.country.updatedate"/></th>
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
    
<div id="countryDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	            <h4 class="modal-title"><@my.message "base.country.formTitle"/></h4>
	        </div>
	  		<div class="modal-body">
                <div class="portlet light form no-space">
                	<form id="imgUploadForm" action="/image/upload" method="post" enctype="multipart/form-data" class="form-horizontal cmxform">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-body">
	                        <input type="hidden" name="token" value="${imageUploadToken}">
	                        <div class="form-group">
	                            <label class="col-md-3 control-label"><@my.message "base.country.nationalFlag"/></label>
	                            <div class="col-md-9">
	                            	<img id="cImgSrc" width="50%" src=""/>
	                            	<input type="file" name="file">
	                            	<label id="nationalFlagCheck" class="error" style="display:none;"><@my.message "base.country.nationalFlagCheck"/></label>
	                            </div>
	                        </div>
                        </div>
                    </form>
                    <form id="countryForm" class="form-horizontal cmxform" role="form" method="post" enctype="multipart/form-data">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                    	<input type="hidden" name="national_flag_img_url" id="imgUrl">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.language"/><span class="required" aria-required="true"> * </span></label>
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
                                <label class="col-md-3 control-label"><@my.message "base.country.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="name" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">ISO Code (2)<span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="iso_code_two" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">ISO Code (3)</label>
                                <div class="col-md-9">
                                    <input type="text" name="iso_code_three" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.addressFormat"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="address_format" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.isRequiredPostcode"/></label>
                                <div class="col-md-9">
                                    <select name="is_required_postcode" class="form-control">
                                    	<option></option>
                                    	<option value="0"><@my.message "page.no"/></option>
	                                	<option value="1"><@my.message "page.yes"/></option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.currency"/></label>
                                <div class="col-md-9">
                                	<select name="currency" class="form-control">
	                                	<option></option>
	                                    <#list currencyList as currency>
	                                    	<option value=${currency.id}>${currency.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.officialLanguage"/></label>
                                <div class="col-md-9">
                                    <select name="official_language_id" class="form-control">
	                                	<option></option>
	                                    <#list languageList as language>
	                                    	<option value=${language.id}>${language.name}</option>
	                                    </#list>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.lengthUnit"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="length_unit" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.weigthUnit"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="weigth_unit" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.sort"/></label>
                                <div class="col-md-9">
                                    <input type="number" name="sort" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "base.country.isEnabled"/></label>
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
	<a href="#countryDiv" id="countryShowButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="languageCheck" class="hide"><@my.message "base.country.languageCheck"/></span>
	<span id="nameCheck" class="hide"><@my.message "base.country.nameCheck"/></span>
	<span id="nameRepeatCheck" class="hide"><@my.message "base.country.nameRepeatCheck"/></span>
	<span id="codeTwoCheck" class="hide"><@my.message "base.country.codeTwoCheck"/></span>
</div>
<script src="/js/baseModul/countryManage.js" type="text/javascript"></script>