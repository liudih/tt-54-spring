<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>tomtop | User Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <!--link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" /--tomtop>
        <link href="${styleurl}/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="${styleurl}/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${styleurl}/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${styleurl}/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="${styleurl}/pages/css/login.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> </head>
    <!-- END HEAD -->

    <body class=" login">
        <div class="menu-toggler sidebar-toggler"></div>
        <!-- END SIDEBAR TOGGLER BUTTON -->
        <!-- BEGIN LOGO -->
        <!--<div class="logo">-->
            <!--<a href="index.html">-->
                <!--<img src="${styleurl}/pages/img/logo_tomtop.png" alt="" /> </a>-->
        <!--</div>-->
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content" style="border-radius: 10px !important;margin-top: 100px; padding: 30px;width: 1000px;">
         <#if updateCheck?exists || update?exists || reset?exists>
         
	           <#if updateCheck?exists>
	           		<pre> 
		           		<#list updateCheck?keys as key>  
			                     ${updateCheck[key]}
						</#list> 
					</pre>
					 <a href="/public/liquibase/update?doit=true&fixChecksum=false&contextParams=">执行更新</a>
					 
					 <#if resetSwitch>
		                      &nbsp&nbsp&nbsp&nbsp&nbsp<a href="/public/liquibase/false">重建初始化数据</a>
	           		</#if>
					
	           </#if>
	           
	            <#if update?exists>
	            	<pre>
		            	<#list update?keys as key>  
			                      ${update[key]}
						</#list> 
					</pre>
					 <a href="/public/liquibase/update?doit=false&fixChecksum=false&contextParams=">检查更新</a>
					 <#if resetSwitch>
		                      &nbsp&nbsp&nbsp&nbsp&nbsp<a href="/public/liquibase/false">重建初始化数据</a>
	           		</#if>
	           </#if>
	           
	           <#if reset?exists>
		               <pre>       ${reset} </pre>
					 <a href="/public/liquibase/update?doit=false&fixChecksum=false&contextParams=">检查更新</a>
	           </#if>
         <#else>
         	  <a href="/public/liquibase/update?doit=false&fixChecksum=false&contextParams=">检查更新</a>
         </#if>
            <!-- END LOGIN FORM -->
            </div>
        <div class="copyright"> 2016 © TOMTOP.</div>
        <!--[if lt IE 9]>
<script src="${styleurl}/global/plugins/respond.min.js"></script>
<script src="${styleurl}/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="${styleurl}/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="${styleurl}/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="${styleurl}/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="${styleurl}/pages/scripts/login.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
    </body>

</html>