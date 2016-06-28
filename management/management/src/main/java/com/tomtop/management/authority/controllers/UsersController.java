package com.tomtop.management.authority.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tomtop.management.authority.mapper.impl.UserRoleRepositoryImpl;
import com.tomtop.management.authority.services.iservice.IMenuService;
import com.tomtop.management.authority.services.iservice.IUserService;
import com.tomtop.management.authority.services.serviceImp.CheckLoginUser;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.authority.services.serviceImp.RoleServiceImpl;
import com.tomtop.management.authority.services.serviceImp.SpringSecurityUserLoginService;
import com.tomtop.management.authority.validator.UserCreateFormValidator;
import com.tomtop.management.common.MD5;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.ebean.authority.model.UserSiteMap;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.forms.CurrentUser;
import com.tomtop.management.forms.MenuDynamic;
import com.tomtop.management.forms.UserCreateForm;
import com.tomtop.management.services.UserService;

@Controller
public class UsersController {

	private static Logger log = Logger.getLogger(UsersController.class.getName());
	@Autowired
	IMenuService menuService;
	@Autowired
	UserService uService;

	private SessionRegistryImpl sessionRegister;
	@Autowired
	CheckLoginUser checkLoginUser;

	private final IUserService userService;

	private final UserCreateFormValidator userCreateFormValidator;

	@Autowired
	RoleServiceImpl roleServiceImpl;
	@Autowired
	UserRoleRepositoryImpl userRoleRepositoryImpl;
	@Autowired
	SpringSecurityUserLoginService springSecurityUserLoginService;
	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	public UsersController(IUserService userService, UserCreateFormValidator userCreateFormValidator) {
		this.userService = userService;
		this.userCreateFormValidator = userCreateFormValidator;
	}

	/**
	 * 
	 * @Title: getMainPage
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            userName
	 * @param @param
	 *            sysName
	 * @param @param
	 *            timestamp
	 * @param @param
	 *            encodeData
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月8日
	 */

	// @RequestMapping(value = "/public/login/user", method =
	// RequestMethod.POST)
	@RequestMapping("/public/login/user")
	public String publicLoginUser(Authentication authentication, Map<String, Object> model, String jobNumber,
			String sysName, String ts, String signData) {
		// log.debug("---------------->"+isPublicLoginUser(signData,jobNumber,timestamp,sysName));
		if (authentication == null) {
			log.debug("publicLoginUser authentication null");
			return publicLogin(authentication, model, jobNumber, sysName, ts, signData);
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || principal instanceof String) {
			return publicLogin(authentication, model, jobNumber, sysName, ts, signData);
		}
		return "redirect:/";// 重定向到首页
	}

	// @RequestMapping(value = "/public/login/user", method =
	// RequestMethod.POST)
	/*
	 * @RequestMapping("/public/manager/link") public String
	 * getManagerLink(Authentication authentication,Map<String, Object> model) {
	 * //log.debug("---------------->"+isPublicLoginUser(signData,jobNumber,
	 * timestamp,sysName)); if(authentication!=null &&
	 * authentication.getPrincipal() instanceof CurrentUser){//如果用户存在，则展示后台链接
	 * CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
	 * String jobNumber=currentUser.getJobNumber(); String timestamp=new
	 * Date().getTime()+""; String
	 * signData=checkLoginUser.getSign(jobNumber,timestamp); String managerHost
	 * = checkLoginUser.getManagerHost(); String
	 * url=managerHost+"/publicLogin?jobNumber="+jobNumber+
	 * "&sysName=123&timestamp="+timestamp+"&signData="+signData+"&username=";
	 * if(StringUtils.isEmpty(jobNumber) || StringUtils.isEmpty(timestamp) ||
	 * StringUtils.isEmpty(signData) || StringUtils.isEmpty(managerHost) ||
	 * StringUtils.isEmpty(url)){ url="#"; } model.put("managerUrl", url); }
	 * return "publicFrame ";//重定向到首页 }
	 */

	// 判断用户是否登录
	@RequestMapping("/public/user/islogin")
	@ResponseBody
	public Map<String, String> isPublicLoginUser(String token, String jobNumber, String timestamp, String sysName) {
		// public Map<String,String> isPublicLoginUser(String token,String
		// jobNumber,String timestamp,String sysName) {
		Map<String, String> map = Maps.newHashMap();
		map.put("status", "N");// 默认情况
		if (StringUtils.isEmpty(jobNumber) || StringUtils.isEmpty(token)) {
			log.debug("publicLoginUser  CurrentUser params null   token:" + token + "  jobNumber:" + jobNumber);
			map.put("error", "userName or token null ! token:" + token + "  jobNumber:" + jobNumber);
			return map;
		}
		String userSessionId = currentUserService.getUserSessionId(jobNumber);
		String encodeKey = checkLoginUser.getEncodeKey();
		String temp = encodeKey + jobNumber + timestamp + sysName + userSessionId;
		String md5 = MD5.md5(temp);
		log.debug("isPublicLoginUser  token：" + token + "  jobNumber:" + jobNumber + "   timestamp:" + timestamp
				+ "  sysName:" + sysName + "  userSessionId:" + userSessionId);
		if (token.equals(md5)) {
			log.info("isPublicLoginUser user is login ,sessionId:" + userSessionId + "  jobNumber:" + jobNumber);
			map.put("status", "Y");// 默认情况
		} else {
			log.debug("isPublicLoginUser not login  token：" + token + "  jobNumber:" + jobNumber + "   timestamp:"
					+ timestamp + "  sysName:" + sysName + "  userSessionId:" + userSessionId);
			map.put("error", "not login");// 默认情况
		}
		return map;
	}

	private String publicLogin(Authentication authentication, Map<String, Object> model, String jobNumber,
			String sysName, String timestamp, String signData) {
		log.debug("publicLoginUser logining jobNumber:" + jobNumber + " sysName:" + sysName + "  timestamp:" + timestamp
				+ "  signData:" + signData);

		AdminUser adminUser = new AdminUser();
		model.clear();
		Map<String, Object> validateLoginUser = Maps.newHashMap();
		validateLoginUser = checkLoginUser.validateLoginUser(jobNumber, sysName, timestamp, signData, adminUser);
		if (MapUtils.isNotEmpty(validateLoginUser) || adminUser == null || adminUser.getId() == null) {
			log.info("publicLoginUser validateLoginUser:" + validateLoginUser);
			model.put("error", validateLoginUser);
			return "redirect:/login";
		}

		List<AdminRole> rolesByUserId = userRoleRepositoryImpl.getRolesByUserId(adminUser.getId());
		if (CollectionUtils.isEmpty(rolesByUserId)) {
			validateLoginUser.put("jobNumber", "The user does not have permission");
			model.put("error", validateLoginUser);
			return "redirect:/login";
		}

		boolean islogin = springSecurityUserLoginService.login(adminUser, rolesByUserId);// 设置
																							// 当前用户
		if (!islogin) {
			validateLoginUser.put("islogin", "login error islogin:" + islogin);
			model.put("error", validateLoginUser);
			return "redirect:/login";
		}
		CurrentUser currentUser = currentUserService.getUserDetails();
		if (currentUser == null) {
			validateLoginUser.put("currentUser", "get currentUser error");
			model.put("error", validateLoginUser);
			return "redirect:/login";
		}
		return "redirect:/";
    }
    @RequestMapping("/login/user")
    @ResponseBody
    public Map<String, Object> getMainPage(Authentication authentication) {
    	
    	
    	CurrentUser currentUser = currentUserService.getUserDetails();
    	if(currentUser==null){
    		return null;
    	}
    	String name=currentUser.getUserName();
    	Map<String, MenuDynamic> dynamicMenu = menuService.getDynamicMenu(currentUser.getId()); 
    	Map<String, Object> model=Maps.newHashMap();
		MenuDynamic base=null;
    	MenuDynamic index=null;
    	MenuDynamic authority = null;
    	MenuDynamic system = null;
    	MenuDynamic market=null;
		if(!MapUtils.isEmpty(dynamicMenu)){
			 base=dynamicMenu.get("baseMode");
	    	 index= dynamicMenu.get("indexSet");
	    	 authority = dynamicMenu.get("authoritySet");
	    	 system = dynamicMenu.get("systemSet");
	    	 market= dynamicMenu.get("market");
		}
		model.put("baseMode", base);
		model.put("indexSet", index);
		model.put("loginUser", name);
		getOldWebUrl(authentication, model);// 获取旧站后台当前用户登录地址
		model.put("authoritySet", authority);
		model.put("systemSet", system);
		model.put("market", market);
        return model;
    }
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
    	
    	Optional<AdminUser> userById = userService.getUserById(id);
    	if(userById.isPresent()){
    		return new ModelAndView("user", "user", userById);
    	}else{
    		
    		throw new NoSuchElementException(String.format("User=%s not found", userById));
    	}
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new UserCreateForm());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("Cjobnumber.exists", "Cjobnumber already exists");
            return "user_create";
        }
        return "redirect:/users";
    }

     private void getOldWebUrl(Authentication authentication,Map<String, Object> model) {
		String url="";
		if (authentication != null
				&& authentication.getPrincipal() instanceof CurrentUser) {// 如果用户存在，则展示后台链接
			CurrentUser currentUser = (CurrentUser) authentication
					.getPrincipal();
			String jobNumber = currentUser.getUsername();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

			String timestamp = sdf.format(new Date());
			String signData = checkLoginUser.getSign(jobNumber, timestamp);
			String managerHost = checkLoginUser.getManagerHost();
			String sysName = checkLoginUser.getSysName();
			String sessionId = currentUserService.getUserSessionId(jobNumber);
			String params = "jobNumber=" + jobNumber + "&sysName=" + sysName + "&tp="
					+ java.net.URLEncoder.encode(timestamp) + "&signData=" + signData + "&sid=" + sessionId;

			log.info("getOldWebUrl params:" + params);
			url = managerHost + "/publicLogin?" + params;
			if (StringUtils.isEmpty(jobNumber) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(signData)
					|| StringUtils.isEmpty(managerHost)
					// || StringUtils.isEmpty(encode)
					|| StringUtils.isEmpty(url)) {
				url = "";
			}
		}
		model.put("oldWebUrl", url);
	}

	/**
	 * 
	 * @Title: getSiteByUser
	 * @Description: TODO(通过用户查询站点)
	 * @param @return
	 * @return List<Site>
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月21日
	 */
	@RequestMapping(value = "/login/getSiteByUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Site> getSiteByUser() {
		List<Site> siteList = new ArrayList<Site>();
		CurrentUser currentUser = currentUserService.getUserDetails();
		List<UserSiteMap> usList = uService.getUSListById(currentUser.getUser().getId());
		List<Long> siteIdList = Lists.transform(usList, us -> us.getSite_id());
		siteList = Site.db().find(Site.class).where().in("id", siteIdList).findList();
		return siteList;
	}

	/**
	 * 
	 * @Title: changeSite
	 * @Description: TODO(更改站点)
	 * @param @param
	 *            url
	 * @param @param
	 *            siteId
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月22日
	 */
	@RequestMapping(value = "/index/changeSite", method = RequestMethod.GET)
	public String changeSite(String url, Integer siteId) {
		Map<String, Integer> siteMap = new HashMap<String, Integer>();
		siteMap.put("siteId", siteId);
		CurrentUser currentUser = currentUserService.getUserDetails();
		currentUser.setSiteMap(siteMap);
		return "redirect:" + url;
	}

	// 判断用户是否登录
	@RequestMapping(value = "/public/login", method = RequestMethod.POST)
	public ModelAndView publicLogin(HttpServletRequest request, HttpServletResponse response, String jobNumber,
			String password, String v) {
		Optional<AdminUser> userByJobnumber = userService.getUserByJobnumber(jobNumber);
		if (userByJobnumber == null) {
			return new ModelAndView("login", "error", "username and password .");
		}
		AdminUser adminUser = userByJobnumber.get();
		HttpSession session = request.getSession();
		String attribute = (String) session.getAttribute("v");
		// response.getWriter().write("<span id='errorTips'
		// style='display:none'>文本</span>");
		if (attribute != null && attribute.equals(v)) {
			List<AdminRole> rolesByUserId = userRoleRepositoryImpl.getRolesByUserId(adminUser.getId());
			springSecurityUserLoginService.login(adminUser, rolesByUserId);
			return new ModelAndView("/");
		} else {
			return new ModelAndView("login", "errorCode", " verification code");
		}
	}
}