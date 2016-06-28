
                    <!-- BEGIN BREADCRUMBS -->
                    <div class="breadcrumbs">
                        <ol class="breadcrumb tx_l">
                            <li>
                                <a href="#"><@my.message "page.home"/></a>
                            </li>
                            <li>
                                 <a href="#"><@my.message "page.authorityConfig"/></a>
                            </li>
                            <li class="active"><@my.message "authority.menurole.mapping.title"/></li>
                        </ol>
                    </div>
                    <!-- END BREADCRUMBS -->
                    <!-- BEGIN 广告内容查询 -->
                  <div class="row">
                        <div class="col-md-12">
                            <div class="portlet green box">
                                <div class="portlet-title">
                                    <div class="caption"><@my.message "authority.menurole.mapping.title"/></div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                                    </div>
                                </div>
                                <div class="portlet-body ">
                                    <!-- BEGIN FORM-->
                                    <form action="#" class="form-horizontal">
                                        <div class="form-body">
                                            <div class="row ">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3"><@my.message "authority.menurole.mapping.roleName"/><span class="required"> * </span></label>
                                                        <div class="col-md-9">
                                                            <select id="roleId" class="form-control">
                                                                <option value="-1"><@my.message "authority.menurole.select"/></option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--/span-->
                                                <div class="col-md-1">
                                                    <button class="btn sbold green" id="searchData"><i class="fa fa-save"></i></button>
                                                </div>
                                            </div>
                                            <div class="portlet-body">
                                                <table class="table table-bordered table-striped table-condensed flip-content table_juese ">
                                                    <thead class="flip-content">
                                                    <tr>
                                                        <th width="15%" style="text-align: center;min-width: 100px;"><@my.message "authority.menurole.mapping.model"/></th>
                                                        <th style="text-align: center"><@my.message "authority.menurole.mapping.function"/></th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="menuList">
                                                    <tr style="display:none">
                                                        <td class="juese_title">
                                                            <label style="font-weight: bold"><span id="rootLabel"></span><div class="checker"><span><input type="checkbox" class="checkboxes"></span></div></label>
                                                        </td>
                                                        <td id="menuItemTemp">
                                                                 <label class=" w_200"  style="display:none"><div class="checker"><span><input type="checkbox" class="checkboxes"></span></div><span id="menuItemTempLabel">用户管理</span></label>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </form>
                                    <!-- END FORM-->
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END 广告内容查询 -->
                    
                <div id="roleDeleteAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
			    	<div class="alert alert-block alert-info fade in">
			            <button type="button" class="close" data-dismiss="modal"></button>
			            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
			            <p><@my.message "authority.menurole.role.empty"/></p>
			        </div>
			    </div>
			   <!--  <div id="parentCheckMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
			    	<div class="alert alert-block alert-info fade in">
			            <button type="button" class="close" data-dismiss="modal"></button>
			            <h4 class="alert-heading"><@my.message "page.tip"/></h4>
			            <p id="parentCheckMessageTips"></p>
			        </div>
			    </div>  -->
			    
			  <div id="parentCheckMessage" class="modal fade" style="margin:auto;width:600px;height:200px">
						<div class="alert alert-block alert-danger fade in">
					        <button type="button" class="close" data-dismiss="modal"></button>
					        <h4 class="alert-heading"><@my.message "page.tip"/></h4>
					        <p id="parentCheckMessageTips"><@my.message "authority.menurole.update.fail"/></p>
					    </div>
				</div>
				<div id="menuUpdateSuccessAlert" class="modal fade" style="margin:auto;width:600px;height:200px">
					<div class="alert alert-block alert-success fade in">
					    <button type="button" class="close" data-dismiss="modal"></button>
					    <h4 class="alert-heading"><@my.message "page.tip"/></h4>
					    <p><@my.message "authority.menurole.update.success"/></p>
					</div>
				</div>
 <!--  提示信息  -->
 <a href="#roleDeleteAlert" id="roleEmpty" onclick="closeBtn()" class="btn hide" data-toggle="modal"></a>
 <a href="#parentCheckMessage" id="parentCheckboxValidate" onclick="closeBtn()" class="btn hide" data-toggle="modal"></a>
 <a href="#menuUpdateSuccessAlert" id="menuUpdateSuccessAlertBtn" onclick="closeBtn()" class="btn hide" data-toggle="modal"></a>
 <span id="loadDataError" class="hide"><@my.message "authority.menurole.loadData.error"/></span>
 <span id="parentCheckTips" class="hide"><@my.message "authority.menurole.validate.select"/></span>
<script src="/js/authority/menuRoleMap.js" type="text/javascript"></script>