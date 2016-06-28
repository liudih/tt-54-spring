<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Tomtop | User Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <!--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />-->
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
        <link rel="shortcut icon" href="${styleurl}/favicon.ico" /> </head>
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
        <div class="content" style="border-radius: 10px !important;margin-top:150px; padding-bottom: 10px;">
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form"  role="form" action="/public/login" method="post">
                <h3 id="hhh_id" class="form-title font-green"><a href="index.html">
                <img src="${styleurl}/pages/img/logo_tomtop.png" alt=""> </a></h3>
               <div id="error_info_id" class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> Enter any ${error!''} ${errorCode!''}. </span>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Jobnumber" required autofocus name="jobNumber"/> </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" name="password" placeholder="Password" required />
                 </div>
                 <div class="form-group"  style="text-align: center;">
                    <label class="control-label visible-ie8 visible-ie9">CAPTCHA</label>
                   <input placeholder="CAPTCHA"  class="form-control form-control-solid placeholder-no-fix" name="v" autocomplete="off" type="text"  id="input1" />
                   <a onclick="createCode()">
                   	<img id="img_v_id" style="width: 200px;height:55px;" src="/public/captchaManage"/>
                   </a>
                 </div>
                <div  style="display:none;" class="form-actions" style="text-align: center">
                    <button id="login_submit_id" type="submit" class="btn green uppercase" style="padding: 10px 50px !important;">Login</button>
                </div>
            </form>
            <div class="form-actions" style="text-align: center">
                <button id="login_button_id" type="button" class="btn green uppercase" style="padding: 10px 50px !important;">Login</button>
            </div>
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
        <script src="/js/baseModul/loginManage.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
    </body>

</html>