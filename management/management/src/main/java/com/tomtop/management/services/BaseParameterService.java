package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.service.model.BaseParameterObject;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: LayoutService
 * @Description: TODO(布局服务类)
 * @author Guozy
 * @date 2015年12月16日
 *
 */
@Service
public class BaseParameterService {
	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @Title: getBaseParametersPage
	 * @Description: TODO(通过相应条件，获取参数的数据信息)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return Page<BaseParameterModel> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public Page<BaseParameterObject> getBaseParametersPage(int pageNo, int pageLimit, BaseParameter baseParameter,
			String clients, String languages) {
		Page<BaseParameterObject> paramPage = new Page<BaseParameterObject>();
		String query = "find BaseParameter where 1 = 1 and is_deleted=0 ";
		String clientStr = "";
		if (StringUtil.isNotEmpty(clients)) {
			query += " and client_id in (" + clients + ") ";
		} else {
			List<Client> clientss = commonService.getAllClient();
			if (clientss.size() > 0 && clientss != null) {
				for (int i = 0; i < clientss.size(); i++) {
					if (i == 0) {
						clientStr = clientss.get(i).getId() + "";
					} else {
						clientStr += "," + clientss.get(i).getId();
					}
				}
				query += " and client_id in (" + clientStr + ") ";
			}
		}
		if (StringUtil.isNotEmpty(languages)) {
			query += " and language_id in (" + languages + ") ";
		}
		if (null != baseParameter.getType()) {
			query += " and type like '%" + baseParameter.getType() + "%'";
		}

		PagedList<BaseParameter> paramPageList = BaseParameter.db().createQuery(BaseParameter.class, query)
				.order().desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		paramPageList.loadRowCount();
		paramPage.setCount(paramPageList.getTotalRowCount());
		paramPage.setLimit(pageLimit);
		paramPage.setPageNo(pageNo);
		List<BaseParameterObject> baseParameterModels = new ArrayList<BaseParameterObject>();
		for (BaseParameter parameter : paramPageList.getList()) {
			BaseParameterObject baseParam = new BaseParameterObject();
			try {
				BeanUtils.copyProperties(baseParam, parameter);
				baseParameterModels.add(baseParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (BaseParameterObject baseParameterModel : baseParameterModels) {
			if (baseParameterModel.getClient_id() != null) {
				baseParameterModel.setClient(Client.db().find(Client.class, baseParameterModel.getClient_id()));
			}
			if (baseParameterModel.getLanguage_id() != null) {
				baseParameterModel.setLanguage(Language.db().find(Language.class, baseParameterModel.getLanguage_id()));
			}
			baseParameterModel.setCreateTime(DateUtil.dateFormat(baseParameterModel.getWhenCreated()));
			baseParameterModel.setUpdateTime(DateUtil.dateFormat(baseParameterModel.getWhenModified()));
		}

		paramPage.setList(baseParameterModels);
		return paramPage;
	}

	/**
	 * 
	 * @Title: getBaseParameterByid
	 * @Description: TODO(根据参数编号，获取参数的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return BaseParameter 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public BaseParameter getBaseParameterByid(int id) {
		return BaseParameter.db().find(BaseParameter.class, id);
	}

	/**
	 * 
	 * @Title: delBaseParameterById
	 * @Description: TODO(通过编号，删除参数的数据信息)
	 * @param @param
	 *            param
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public int delBaseParameterById(String param) {
		BaseParameter baseParameter;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				baseParameter = BaseParameter.db().find(BaseParameter.class, sourceStrArray[i]);
				baseParameter.setIs_deleted(1);
				BaseParameter.db().save(baseParameter);
				count++;
			}
		} else {
			baseParameter = BaseParameter.db().find(BaseParameter.class, param);
			baseParameter.setIs_deleted(1);
			BaseParameter.db().save(baseParameter);
			count++;
		}
		return count;
	};

	/**
	 * 
	 * @Title: validateParams
	 * @Description: TODO(验证信息)
	 * @param @param
	 *            baseParameter
	 * @param @return
	 *            参数
	 * @return List<BaseParameter> 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2015年12月17日
	 */
	public List<BaseParameter> validateParams(BaseParameter baseParameter) {
		return BaseParameter.db().find(BaseParameter.class).where().eq("type", baseParameter.getType())
				.eq("value", baseParameter.getValue()).eq("name", baseParameter.getName()).eq("is_deleted", 0)
				.findList();
	};

	/**
	 * 
	 * @Title: getAllParam
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return
	 *            参数
	 * @return List<BaseParameter> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月12日
	 */
	public List<BaseParameter> getAllParamBybanner() {
		String q = " find BaseParameter where type = 'BANNER-POSITION' ";
		return BaseParameter.db().createQuery(BaseParameter.class, q).where().eq("is_deleted", 0).eq("is_enabled", 1)
				.orderBy("sort").findList();
	}

	/**
	 * 
	 * @Title: getAllParamByCurrency
	 * @Description: TODO(根据参数类型，获取货币信息)
	 * @param @return
	 *            参数
	 * @return List<BaseParameter> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月12日
	 */
	public List<BaseParameter> getAllParamByCurrency() {
		return BaseParameter.db().find(BaseParameter.class).where().eq("type", "CURRENCY-POSITION").eq("is_deleted", 0)
				.eq("is_enabled", 1).findList();
	}
}
