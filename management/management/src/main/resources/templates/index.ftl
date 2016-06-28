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
        <title>tomtop | Dashboard</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN LAYOUT FIRST STYLES -->
        <!--link href="//fonts.googleapis.com/css?family=Oswald:400,300,700" rel="stylesheet" type="text/css" /-->
        <!-- END LAYOUT FIRST STYLES -->
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <!--link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" /-->
        <link href="${styleurl}/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/uniform/css/uniform.default.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
         <link href="${styleurl}/global/plugins/morris/morris.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${styleurl}/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${styleurl}/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="${styleurl}/layouts/layout5/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/layouts/layout5/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="${styleurl}/favicon.ico" /> 
        <link href="${styleurl}/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="${styleurl}/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <script src="${styleurl}/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${styleurl}/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="${styleurl}/pages/scripts/ui-blockui.min.js" type="text/javascript"></script>
        <script src="/js/plugins/jquery.form.js" type="text/javascript"></script>
        <script src="/js/plugins/jquery.json.js" type="text/javascript"></script>
        <script src="/js/common/page.js" type="text/javascript"></script>
        <script src="/js/common/menu.js" type="text/javascript"></script>
        <script src="/js/common/index.js" type="text/javascript"></script>
        <script src="/js/plugins/jquery.json.js" type="text/javascript"></script>
        <link href="${styleurl}/global/css/ttStyle.css" rel="stylesheet" type="text/css">
    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<div class="wrapper">
        <!-- BEGIN HEADER -->
        <#include "component/header.ftl">
        <!-- END HEADER -->
        <!-- BEGIN SECTION -->
        <div class="container-fluid">
	        <div class="page-content">
		        <#if section?? && section != "">
		        	<#include section>
		        </#if>
		        <#include "message.ftl">
	        </div>
	        <p class="copyright">2015 © tomtop</p>
		    <a href="#index" class="go2top" style="display: inline;">
			    <i class="icon-arrow-up"></i>
			</a>
        </div>
        <!-- END SECTION -->
        </div>
        <!-- BEGIN FOOTER -->
        <#include "component/footer.ftl">
        <!-- END FOOTER -->
        <#if section??>
        	<input type="hidden" id="sectionId" value=${section}>
    	</#if>
    </body>
</html>