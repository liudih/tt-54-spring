<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l" style="float: left;margin-top:7px;">
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
                                    	<a href="/shipping/type">
                                        <button class="btn sbold red"><i class="icon-action-undo"></i>
                                        </button>
                                        </a>
                                    </div>
                                </div>
                            </div>
</div>

<input type="hidden" name="pageType" value="${type}"/>
                                <div class="row">
									<div class="col-md-12">
										<div class="portlet-body">
                                        <form id="id-form" class="form-horizontal cmxform" role="form" method="post" novalidate="novalidate">
                                            <input type="hidden" name="id" value="${id}"/>
                                            <div class="form-body">
                                            	
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "shipping.typename"/><span class="required">*</span></label>
                                                    <div class="col-md-3">
                                                        <input type="text" name="type_name" class="form-control">	
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "shipping.code"/><@my.message "shipping.code.style"/><span class="required">*</span></label>
                                                    <div class="col-md-3">
                                                        <input type="text" name="shipping_code" class="form-control">	
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "shipping.displayname"/><@my.message "shipping.english"/><span class="required">*</span></label>
                                                    <div class="col-md-3">
                                                        <input type="hidden" name="sdList[0].language_id" class="form-control" value="1">	
	                                                    <input type="text" name="sdList[0].display_name" class="form-control" >	
                                                    </div>
                                                    <div class="col-md-3">
	                                                	<div class="btn-group" id="dis">
															 <button type="button" class="btn blue" data-toggle="modal" href="#dis_static"><@my.message "shipping.translate"/><span class="required">*</span></button>
														</div>
													</div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "shipping.description"/><@my.message "shipping.english"/><span class="required">*</span></label>
                                                    <div class="col-md-3">
                                                    	<input type="hidden" name="sdList[0].language_id" class="form-control" value="1">
                                                        <textarea name="sdList[0].description" class="form-control" placeholder="" ></textarea>
                                                    </div>
                                                    <div class="col-md-3">
	                                                	<div class="btn-group" id="des">
															 <button type="button" class="btn blue" data-toggle="modal" href="#des_static"><@my.message "shipping.translate"/><span class="required">*</span></button>
														</div>
													</div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "shipping.type.sort"/><span class="required">*</span></label>
                                                    <div class="col-md-3">
                                                        <input type="number" name="shipping_sequence" class="form-control">	
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"><@my.message "base.layoutmodule.enabled"/></label>
                                                    <div class="col-md-3">
                                                        <select name="is_enabled" class="form-control error" aria-required="true">
					                                    	<option value="1">Enabled</option>
					                                    	<option value="0">Disabled</option>
					                                	</select>
					                                	<label id="is_enabled-error" class="error" for="is_enabled" style="display: none;"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        

            
            <div id="dis_static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content" style="height: auto;">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "shipping.translate"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">
                                    	 <div class="form-horizontal cmxform" novalidate="novalidate">
                                    	<div class="form-body">
                                    		<#list languageList as language>
                                    			<#if language.id!=1>
		                                    	<div class="form-group">
	                                                    <label class="col-md-3 control-label">${language.name}</label>
	                                                    <div class="col-md-9">
	                                                        <input type="hidden" name="sdList[${language_index}].language_id" class="form-control" value="${language.id}"/>	
	                                                        <input type="text" name="sdList[${language_index}].display_name" class="form-control"/>	
	                                                    </div>
	                                            </div>
	                                            </#if>
		                                    </#list>
                                    		</div>
                                    	</div>
                                    	<div class="modal-footer">
                                                <button type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button type="button" data-dismiss="modal" class="btn green"><@my.message "page.save"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
            </div>
            
            <div id="des_static" data-backdrop="static" class="modal fade" style="display: none;">
                        <div class="modal-dialog">
                            <div class="modal-content" style="height: auto;">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title"><@my.message "shipping.translate"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="portlet light form no-space">
                                    	 <div class="form-horizontal cmxform" novalidate="novalidate">
                                    	<div class="form-body">
                                    		<#list languageList as language>
                                    			<#if language.id!=1>
		                                    	<div class="form-group">
	                                                    <label class="col-md-3 control-label">${language.name}</label>
	                                                    <div class="col-md-9">
	                                                        <input type="hidden" name="sdList[${language_index}].language_id" class="form-control" value="${language.id}">	
	                                                        <input type="text" name="sdList[${language_index}].description" class="form-control">	
	                                                    </div>
	                                            </div>
	                                            </#if>
		                                    </#list>
                                    		</div>
                                    	<div class="modal-footer">
                                                <button type="button" data-dismiss="modal" class="btn"><@my.message "page.close"/></button>
                                                <button type="button" data-dismiss="modal" class="btn green"><@my.message "page.save"/></button>
                                            </div>
                                    	</div>
                                    </div>
                                </div>
                            </div>
                        </div>
            </div>
            
            </form>
         </div>
       </div>
     </div>
 <div>
	<div id="invalidCOrSCAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
    	<div class="alert alert-block alert-info fade in">
            <button type="button" class="close" data-dismiss="modal"></button>
            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
            <p><@my.message "shipping.type.shippingCodeCheck"/></p>
        </div>
    </div>
</div>
<div>
	<a href="#invalidCOrSCAlert" id="invalidCOrSCShowButton" onclick="closeBtn(this)" class="btn hide" data-toggle="modal"></a>
</div>
     <span id="englishCheck" class="hide"><@my.message "shipping.english.check"/></span>
     <script src="/js/shipping/shipping_type.js" type="text/javascript"></script>