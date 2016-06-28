<header class="page-header">
    <nav class="navbar mega-menu" role="navigation">
        <div class="container-fluid">
            <div class="clearfix navbar-fixed-top">
                <!-- Brand and toggle get grouped for better mobile display -->
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="toggle-icon">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </span>
                </button>
                <!-- End Toggle Button -->
                <!-- BEGIN LOGO -->
                <a id="index" class="page-logo" href="/">
                    <img src="${styleurl}/layouts/layout5/img/logo.png" alt="Logo"> </a>
                <!-- END LOGO -->
                <!-- BEGIN TOPBAR ACTIONS -->
                <div class="topbar-actions">
                	<div class="btn-group-img btn-group tt_website_box web_current">
                		<span><@my.message "page.header.currentSite"/></span>
                		<span id="currentSite"></span>
                	</div>
                	<!-- 旧站点 -->
	                <div id="oldWebUrlDiv" style="display:none" class="btn-group-img btn-group tt_website_box">
	                					<a target="_blank" href="#" style="display:none"><div id="oldWebSite">跳转</div></a>
	                                    <button id="oldButton" type="button" class="btn btn-sm dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
	                                        <span><@my.message "page.header.oldManage"/></span>
	                                    </button>
	                </div>
                	<div class="btn-group-img btn-group tt_website_box">
                        <button type="button" class="btn btn-sm dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <span><@my.message "page.header.site"/></span>
                        </button>
                        <ul class="dropdown-menu-v2" role="menu">
                        	<li>
	                          <div class="tt_website">
	                              <h6>TOMTOP</h6>
	                              <ul id="indexSiteUl" class="clearfix">
	                              </ul>
	                          </div>
                            </li>
                            <li class="divider"> </li>
                            <li>
                                <div class="tt_website">
                                    <h6><@my.message "page.header.brandSite"/></h6>
                                    <ul id="indexBrandSiteUl" class="clearfix">
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                	
                    <!-- BEGIN USER PROFILE -->
                    <div class="btn-group-img btn-group">
                        <button type="button" class="btn btn-sm dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <span id="loginUserInfo"></span>
                            <!--<img src="${styleurl}/layouts/layout5/img/avatar1.jpg" alt=""> -->
                        </button>
                        <ul class="dropdown-menu-v2" role="menu">
                            <!--<li>
                                <a href="page_user_profile_1.html">
                                    <i class="icon-user"></i> My Profile
                                    <span class="badge badge-danger">1</span>
                                </a>
                            </li>
                            <li>
                                <a href="app_calendar.html">
                                    <i class="icon-calendar"></i> My Calendar </a>
                            </li>
                            <li>
                                <a href="app_inbox.html">
                                    <i class="icon-envelope-open"></i> My Inbox
                                    <span class="badge badge-danger"> 3 </span>
                                </a>
                            </li>
                            <li>
                                <a href="app_todo_2.html">
                                    <i class="icon-rocket"></i> My Tasks
                                    <span class="badge badge-success"> 7 </span>
                                </a>
                            </li>
                            <li class="divider"> </li>-->
                            <li>
                                <a href="page_user_lock_1.html">
                                    <i class="icon-lock"></i> Lock Screen </a>
                            </li>
                            <li>
	                             <form action="/logout" method="post" style="display:none">
					                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					                <button type="submit" id="logoutBt" >Log out</button>
					            </form>
                                <a href="javascript:;" id="logoutId">
                                    <i class="icon-key"></i> Log Out </a>
                            </li>
                        </ul>
                    </div>
                    <!-- END USER PROFILE -->
                    <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                    <!--button type="button" class="quick-sidebar-toggler" data-toggle="collapse">
                        <span class="sr-only">Toggle Quick Sidebar</span>
                        <i class="icon-logout"></i>
                    </button-->
                    <!-- END QUICK SIDEBAR TOGGLER -->
                </div>
                <!-- END TOPBAR ACTIONS -->
            </div>
            <!-- BEGIN MENU -->
	        <#include "menu.ftl">
	        <!-- END MEBU -->
        </div>
        <!--/container-->
    </nav>
</header>