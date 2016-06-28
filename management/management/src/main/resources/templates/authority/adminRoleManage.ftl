<div class="breadcrumbs">
	<ol class="breadcrumb tx_l">
        <li>
            <a href="#"><@my.message "page.home"/></a>
        </li>
        <li>
            <a href="#"><@my.message "page.authorityConfig"/></a>
        </li>
        <li class="active"><@my.message "authority.role.title"/></li>
    </ol>
</div>
<div class="row">
    <div class="col-md-12">
		<div class="portlet box green">
			<div class="portlet-title">
		        <div class="caption">
		            <i class="fa fa-gift"></i><@my.message "authority.user.searchTitle"/>
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
		                            <label class="control-label col-md-3">  <@my.message "authority.role.name"/></label>
		                            <div class="col-md-9">
		                                <input type="text" id="roleName" class="form-control" maxlength="30">
		                            </div>
		                        </div>
		                    </div>
		                    <!--/span-->
		                    <div class="col-md-3">
		                        <div class="form-group">
		                            <label class="control-label col-md-3"><@my.message "authority.role.status"/></label>
		                            <div class="col-md-9">
		                               <select id="status" class="form-control">
		                                	<option></option>
		                                    <option value="1"> <@my.message "authority.role.enble"/></option>
		                                    <option value="0"><@my.message "authority.role.unable"/></option>
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
	                            <th><@my.message "authority.role.id"/></th>
	                            <th><@my.message "authority.role.name"/></th>
	                            <th><@my.message "authority.role.status"/></th>
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
<div id="roleDiv" class="modal fade in">
  	<div class="modal-dialog">
	  	<div class="modal-content">	
	  		<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><@my.message "authority.user.formTitle"/></h4>
            </div>
  			<div class="modal-body">
                <div class="portlet light form no-space">
                    <form id="roleForm" class="form-horizontal cmxform" role="form" method="post">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="id">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label"> <@my.message "authority.role.name"/></label>
                                <div class="col-md-9">
                                    <input type="text" name="roleName" class="form-control input-lg" maxlength="30">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"> <@my.message "authority.role.status"/></label>
                                <div class="col-md-6">
                                    <select  name="status" class="form-control">
	                                	<option></option>
	                                    <option value="1"><@my.message "authority.role.enble"/></option>
	                                    <option value="0"><@my.message "authority.role.unable"/></option>
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
	<a href="#roleDiv" id="roleShowButton" class="btn hide" data-toggle="modal"></a>
</div>
<div>
	<span id="roleNameCheck" class="hide"><@my.message "authority.role.roleNameCheck"/></span>
	<span id="roleNameRepeatCheck" class="hide"><@my.message "authority.role.roleNameRepeatCheck"/></span>
	<span id="statusCheck" class="hide"><@my.message "authority.role.statusCheck"/></span>
	
</div>
<script src="/js/authority/adminRoleManage.js" type="text/javascript"></script>