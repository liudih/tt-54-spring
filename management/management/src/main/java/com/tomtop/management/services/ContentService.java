package com.tomtop.management.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.management.common.DateUtil;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.ContentCatalogue;
import com.tomtop.management.ebean.manage.model.ContentDetails;
import com.tomtop.management.ebean.manage.model.ContentDetailsContext;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.ContentDetailsObject;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Service
public class ContentService {

	@Autowired
	ProductService productService;
	/**
	 * 
	 * @Title: getContentCatalogueByClient
	 * @Description: TODO(通过客户端id查询内容目录)
	 * @param @param clientId
	 * @param @return    
	 * @return List<ContentCatalogue>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public List<ContentCatalogue> getContentCatalogueByClient(Long clientId) {
		return ContentCatalogue.db().find(ContentCatalogue.class).where().eq("client_id", clientId).eq("is_deleted", 0)
				.orderBy("sort").findList();
	}

	/**
	 * 
	 * @Title: addContentCatalogue
	 * @Description: TODO(新增内容目录)
	 * @param @param cc
	 * @param @return    
	 * @return Long    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public Long addContentCatalogue(ContentCatalogue cc) {
		ContentCatalogue parentCatalogue = ContentCatalogue.db().find(ContentCatalogue.class, cc.getParent_id());
		cc.setIs_deleted(0);
		if (null != parentCatalogue) {
			cc.setLevel(parentCatalogue.getLevel() + 1);
		} else {
			cc.setLevel(1);
		}
		try {
			ContentCatalogue.db().insert(cc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cc.getId();
	}

	/**
	 * 
	 * @Title: updateContentCatalogue
	 * @Description: TODO(修改内容目录)
	 * @param @param cc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public int updateContentCatalogue(ContentCatalogue cc) {
		int updateCount = 0;
		try {
			ContentCatalogue.db().update(cc);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: deleteContentCatalogue
	 * @Description: TODO(删除内容目录)
	 * @param @param id
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public int deleteContentCatalogue(Long id) {
		int deleteCount = 0;
		try {
			ContentCatalogue cc = ContentCatalogue.db().find(ContentCatalogue.class, id);
			deleteAllCatalogue(cc);
			deleteCatalogue(cc);
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		deleteCount++;
		return deleteCount;
	}

	/**
	 * 
	 * @Title: getContentDetailsByCatalogueId
	 * @Description: TODO(通过目录id查询内容详情)
	 * @param @param id
	 * @param @return    
	 * @return ContentDetailsObject    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public ContentDetailsObject getContentDetailsByCatalogueId(Long id) {
		ContentDetails cd = ContentDetails.db().find(ContentDetails.class).where().eq("catalogue_id", id)
				.eq("is_deleted", 0).findUnique();
		ContentDetailsObject cdo = new ContentDetailsObject();
		if (null != cd) {
			try {
				BeanUtils.copyProperties(cdo, cd);
				cdo.setCrateTime(DateUtil.dateFormat(cdo.getWhenCreated()));
				cdo.setUpdateTime(DateUtil.dateFormat(cdo.getWhenModified()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return cdo;
	}

	/**
	 * 
	 * @Title: getContentDetailsContext
	 * @Description: TODO(通过详情id和语言id查询详情内容)
	 * @param @param detailsId
	 * @param @param languageId
	 * @param @return    
	 * @return ContentDetailsContext    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public ContentDetailsContext getContentDetailsContext(Long detailsId, Long languageId) {
		return ContentDetailsContext.db().find(ContentDetailsContext.class).where().eq("details_id", detailsId)
				.eq("language_id", languageId).findUnique();
	}

	/**
	 * 
	 * @Title: saveContentCatalogue
	 * @Description: TODO(保存详情和详情内容)
	 * @param @param cd
	 * @param @param cdc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	public ValidCheckInfo saveContentCatalogue(ContentDetails cd, ContentDetailsContext cdc) {
		ValidCheckInfo vci = new ValidCheckInfo();
		ContentDetails contentDetails = ContentDetails.db().find(ContentDetails.class).where()
				.eq("catalogue_id", cd.getCatalogue_id()).eq("is_deleted", 0).findUnique();
		vci = validateUrlAndSite(contentDetails, cdc);
		if(vci.getStatus() != 0){
			return vci;
		}
		try {
			if (null == contentDetails) {
				cd.setIs_deleted(0);
				ContentDetails.db().insert(cd);
				cdc.setDetails_id(cd.getId());
				ContentDetailsContext.db().insert(cdc);
			} else {
				cd.setId(contentDetails.getId());
				ContentDetails.db().update(cd);
				ContentDetailsContext contentDetailsContext = ContentDetailsContext.db()
						.find(ContentDetailsContext.class).where().eq("details_id", contentDetails.getId())
						.eq("language_id", cdc.getLanguage_id()).findUnique();
				if (null == contentDetailsContext) {
					cdc.setDetails_id(contentDetails.getId());
					ContentDetailsContext.db().insert(cdc);
				} else {
					cdc.setId(contentDetailsContext.getId());
					ContentDetailsContext.db().update(cdc);
				}
			}
		} catch (OptimisticLockException e) {
			vci.setStatus(2);
			vci.setType("error");
			vci.setDesc(e.getMessage());
			e.printStackTrace();
		}
		vci.setStatus(0);
		return vci;
	}

	/**
	 * 
	 * @Title: deleteAllCatalogue
	 * @Description: TODO(递归删除目录)
	 * @param @param cc    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	private void deleteAllCatalogue(ContentCatalogue cc) {
		List<ContentCatalogue> ccList = ContentCatalogue.db().find(ContentCatalogue.class).where()
				.eq("parent_id", cc.getId()).findList();
		if (null != ccList && ccList.size() > 0) {
			for (ContentCatalogue contentCatalogue : ccList) {
				deleteAllCatalogue(contentCatalogue);
				deleteCatalogue(contentCatalogue);
			}
		} else {
			deleteCatalogue(cc);
		}
	}

	/**
	 * 
	 * @Title: deleteCatalogue
	 * @Description: TODO(删除单个目录)
	 * @param @param cc    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	private void deleteCatalogue(ContentCatalogue cc) {
		cc.setIs_deleted(1);
		ContentCatalogue.db().update(cc);
	}

	/**
	 * 
	 * @Title: getEnglishUrl
	 * @Description: TODO(获取English的内容链接)
	 * @param @param detailsId
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	public String getEnglishUrl(Long detailsId) {
		Long lId = Language.db().find(Language.class).where().eq("name", "English").findUnique().getId();
		String url = "";
		if (lId != null) {
			ContentDetailsContext cdc = ContentDetailsContext.db().find(ContentDetailsContext.class).where()
					.eq("details_id", detailsId).eq("language_id", lId).findUnique();
			if (cdc != null && cdc.getUrl() != null) {
				url = cdc.getUrl();
			}
		}
		return url;
	}

	/**
	 * 
	 * @Title: validateUrlAndSite
	 * @Description: TODO(校验同一站点里url是否重复)
	 * @param @param contentDetails
	 * @param @param cdc
	 * @param @return    
	 * @return ValidCheckInfo    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	private ValidCheckInfo validateUrlAndSite(ContentDetails contentDetails, ContentDetailsContext cdc) {
		ValidCheckInfo vci = new ValidCheckInfo();
		vci.setStatus(0);
		Long siteId = productService.getCurrentSiteId();
		List<Client> clients = Client.db().find(Client.class).where().eq("site_id", siteId).findList();
		String q = null;
		if (clients != null && clients.size() > 0) {
			q = "find ContentCatalogue where client_id in (" + clientToString(clients) + ") and is_deleted = 0";
			List<ContentCatalogue> ccs = ContentCatalogue.db().createQuery(ContentCatalogue.class, q).findList();
			if (ccs != null && ccs.size() > 0) {
				q = "find ContentDetails where catalogue_id in (" + contentCatalogueToString(ccs)
						+ ") and is_deleted = 0";
				List<ContentDetails> cds = ContentDetails.db().createQuery(ContentDetails.class, q).findList();
				if (cds != null && cds.size() > 0) {
					q = "find ContentDetailsContext where details_id in (" + ContentDetailsToString(cds)
							+ ") and url = '" + cdc.getUrl() + "'";
					List<ContentDetailsContext> cdcs = ContentDetailsContext.db()
							.createQuery(ContentDetailsContext.class, q).findList();
					if (cdcs.size() > 0) {
						if (contentDetails == null) {
							vci.setStatus(1);
							vci.setType("url");
						} else {
							for (int i = 0; i < cdcs.size(); i++) {
								if (cdcs.get(i).getDetails_id() != contentDetails.getId()) {
									vci.setStatus(1);
									vci.setType("url");
									break;
								}
							}
						}
					}
				}
			}
		}
		return vci;
	}

	/**
	 * 
	 * @Title: clientToString
	 * @Description: TODO(Client集合转为String)
	 * @param @param clients
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	private String clientToString(List<Client> clients) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		if (clients != null && clients.size() > 0) {
			for (Client c : clients) {
				sb.append("," + c.getId());
			}
			s = sb.toString().substring(1);
		}
		return s;
	}

	/**
	 * 
	 * @Title: contentCatalogueToString
	 * @Description: TODO(ContentCatalogue集合转为String)
	 * @param @param ccs
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	private String contentCatalogueToString(List<ContentCatalogue> ccs) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		if (ccs != null && ccs.size() > 0) {
			for (ContentCatalogue c : ccs) {
				sb.append("," + c.getId());
			}
			s = sb.toString().substring(1);
		}
		return s;
	}

	/**
	 * 
	 * @Title: ContentDetailsToString
	 * @Description: TODO(ContentDetails集合转为String)
	 * @param @param cds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	private String ContentDetailsToString(List<ContentDetails> cds) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		if (cds != null && cds.size() > 0) {
			for (ContentDetails c : cds) {
				sb.append("," + c.getId());
			}
			s = sb.toString().substring(1);
		}
		return s;
	}
}
