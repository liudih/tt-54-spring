<div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="/home"><@my.message "base.client.bread.home"/></a>
                            </li>
                            <li>
                                <a href="/base"><@my.message "base.client.bread.module"/></a>
                            </li>
                            <li>
                                <a href="/base/baseDesign"><@my.message "base.client.bread.design"/></a>
                            </li>
                            <li class="active"><@my.message "base.recommend.title"/></li>
                        </ol>
</div>
<div class="row">
                        <div class="col-md-12">
						<input type="hidden" name="pageType" value="${type}"/>
                            <div class="portlet green box">
                                <div class="portlet-title">
                                    <div class="caption"><@my.message "page.search.content"/></div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                                    </div>
                                </div>
                                <div class="portlet-body ">
                                    <!-- BEGIN FORM-->
                                    <form action="#" class="form-horizontal">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.client"/></label>
                                                        <div class="col-md-9">
                                                            <select class="bs-select form-control" id="clients" multiple>
                                                                <#list clientList as client>
							                                    	<option value=${client.id}>${client.name}</option>
							                                    </#list>
                                                            </select>
                                                         </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.language"/></label>
                                                        <div class="col-md-9">
                                                            <select class="bs-select form-control" id="languages" multiple>
							                                    <#list languageList as language>
							                                    	<option value=${language.id}>${language.name}</option>
							                                    </#list>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.layoutmodule.layoutName"/></label>
                                                        <div class="col-md-9">
                                                            <select class="form-control" id="layout_id">
                                                                <#list layoutList as layout>
							                                    	<option value=${layout.id}>${layout.name}</option>
							                                    </#list>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <!--/span-->
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "base.recommend.name"/></label>
                                                        <div class="col-md-9">
                                                            <input type="text" id="name" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div>
                                                    <button id="searchS" type="button" class="btn green "><i class="fa fa-search"></i></button>
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
                                        <span class="caption-subject bold uppercase"><@my.message "base.layoutmodule.listtitle"/></span>
                                    </div>
                                    <div class="actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="btn-group">
                                                	<a href="/base/layoutModule/operation/0">
                                                    <button id="addButton" class="btn sbold green" ><i class="fa fa-plus"></i>
                                                    </button>
                                                    </a>
                                                </div>
                                                <div class="btn-group">
                                                    <button id="delT" class="btn sbold red"><i class="fa fa-trash"></i>
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
                                                    <input type="checkbox" class="group-checkable" id="mcMainCheckbox" data-set="#bodyT .checkboxes"></th>
                                                <th><@my.message "page.operation"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.client"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.language"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.layoutName"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.moduleName"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.moduleCode"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.position"/> </th>
                                                <th scope="col"> <@my.message "base.layoutmodule.url"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.number"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.sort"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.enabled"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.createuser"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.createdate"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.updateuser"/></th>
                                                <th scope="col"> <@my.message "base.layoutmodule.updatedate"/></th>
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
<script src="/js/baseModul/recommend.js" type="text/javascript"></script>