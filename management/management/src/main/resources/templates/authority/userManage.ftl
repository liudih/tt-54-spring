<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="/home"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="/authority"><@my.message "page.authorityConfig"/></a>
        </li>
        <li class="active"><@my.message "authority.user.navigation"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <@my.message "authority.user.searchTitle"/>
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
		                            <label class="control-label col-md-3"><@my.message "authority.user.name"/></label>
		                            <div class="col-md-9">
		                                <input type="text" id="userName" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "authority.user.jobNumber"/></label>
		                            <div class="col-md-9">
		                                <input type="text" id="jobNumber" class="form-control">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-4"><@my.message "authority.user.role"/></label>
		                            <div class="col-md-8">
		                                <select id="role" class="form-control">
		                                	<option></option>
		                                    <#list adminRoleList as adminRole>
		                                    	<option value=${adminRole.id}>${adminRole.roleName}</option>
		                                    </#list>
		                                </select>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-1 text-right">
                                <button type="button" id="dataSearch" class="btn green "><i class="fa fa-search"></i></button>
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
                    <span class="caption-subject bold uppercase"><@my.message "authority.user.listTitle"/></span>
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
	                            <th><@my.message "authority.user.name"/></th>
	                            <th><@my.message "authority.user.jobNumber"/></th>
	                            <th><@my.message "authority.user.email"/></th>
	                            <th><@my.message "authority.user.phone"/></th>
	                            <th><@my.message "base.resource.isEnabled"/></th>
	                            <th><@my.message "base.resource.createuser"/></th>
	                            <th><@my.message "base.resource.createdate"/></th>
	                            <th><@my.message "base.resource.updateuser"/></th>
	                            <th><@my.message "base.resource.updatedate"/></th>
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
<div id="userDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "authority.user.formTitle"/></h4>
            </div>
  			<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="userForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.name"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="userName" class="form-control">
                                </div>
                            </div>
                            <div class="form-group" id="passwordForm">
                                <label class="col-md-3 control-label"><@my.message "authority.user.password"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="password" class="form-control">
                                </div>
                                <!--<button id="getPasswordButton" class="col-md-2 btn sbold blue"><@my.message "authority.user.randomPassword"/></button>-->
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.jobNumber"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="jobNumber" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.email"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="email" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.phone"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="phone" class="form-control"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.role"/></label>
                                <div class="col-md-9">
                                    <#list adminRoleList as adminRole>
                                    	<input type="checkbox" id="adminRole${adminRole.id}" class="adminRoleCheckbox" value=${adminRole.id}>${adminRole.roleName}</input><br>
                                    </#list>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.site"/></label>
                                <div class="col-md-9">
                                    <#list siteList as site>
                                    	<input type="checkbox" id="site${site.id}" class="siteCheckbox" value=${site.id}>${site.name}</input><br>
                                    </#list>
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
<div id="passwordDiv" class="modal fade in" data-backdrop="static">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "authority.user.passwordTitle"/></h4>
            </div>
  			<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="resetPasswordForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="pid">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.newPassword"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" id="newPassword" name="newPassword" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"><@my.message "authority.user.newPasswordConfirm"/><span class="required" aria-required="true"> * </span></label>
                                <div class="col-md-9">
                                    <input type="text" name="confirmPassword" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="passwordSaveCancel" class="btn default" data-dismiss="modal"><@my.message "page.cancel"/></button>
                            <button type="submit" class="btn green"><@my.message "page.save"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
	<a href="#userDiv" id="userShowButton" class="btn hide" data-toggle="modal"></a>
	<a href="#passwordDiv" id="passwordShowButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="userNameCheck" class="hide"><@my.message "authority.user.nameCheck"/></span>
	<span id="userNameRepeatCheck" class="hide"><@my.message "authority.user.namerepeatCheck"/></span>
	<span id="jobNumberCheck" class="hide"><@my.message "authority.user.jobNumberCheck"/></span>
	<span id="jobNumberRepeatCheck" class="hide"><@my.message "authority.user.jobNumberRepeatCheck"/></span>
	<span id="emailCheck" class="hide"><@my.message "authority.user.emailCheck"/></span>
	<span id="emailFormatCheck" class="hide"><@my.message "authority.user.emailFormatCheck"/></span>
	<span id="phoneCheck" class="hide"><@my.message "authority.user.phoneCheck"/></span>
	<span id="phoneFormatCheck" class="hide"><@my.message "authority.user.phoneFormatCheck"/></span>
	<span id="passwordCheck" class="hide"><@my.message "authority.user.passwordCheck"/></span>
	<span id="newPasswordCheck" class="hide"><@my.message "authority.user.newPasswordCheck"/></span>
	<span id="passwordSecurityCheck" class="hide"><@my.message "authority.user.passwordSecurityCheck"/></span>
	<span id="passwordConfirmCheck" class="hide"><@my.message "authority.user.passwordConfirmCheck"/></span>
	<span id="passwordEqualCheck" class="hide"><@my.message "authority.user.passwordEqualCheck"/></span>
</div>
<script src="/js/authority/userManage.js" type="text/javascript"></script>