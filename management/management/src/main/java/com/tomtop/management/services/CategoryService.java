package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.api.client.util.Lists;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.data.model.CategoryData;
import com.tomtop.management.service.model.report.CategoryThreeLevel;

@Service
public class CategoryService {

	@Autowired
	ProductService pService;

	/**
	 * 
	 * @Title: getCategoryByLevel
	 * @Description: TODO(通过等级查询品类)
	 * @param @param
	 *            level
	 * @param @return
	 * @return List<Category>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<Category> getCategoryByLevel(int level) {
		return Category.db().find(Category.class).where().eq("ilevel", level)
				.eq("iwebsiteid", pService.getCurrentSiteId()).findList();
	}

	/**
	 * 
	 * @Title: getCategoryByParentId
	 * @Description: TODO(通过父id查询品类)
	 * @param @param
	 *            parentId
	 * @param @return
	 * @return List<Category>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<Category> getCategoryByParentId(Integer parentId) {
		if (null != parentId) {
			return Category.db().find(Category.class).where().eq("iparentid", parentId)
					.eq("iwebsiteid", pService.getCurrentSiteId()).findList();
		} else {
			return new ArrayList<Category>();
		}
	}

	/**
	 * 
	 * @Title: getCategoryById
	 * @Description: TODO(通过id查询品类)
	 * @param @param
	 *            id
	 * @param @return
	 * @return Category
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public Category getCategoryById(Integer id) {
		if (null != id) {
			return Category.db().find(Category.class).where().eq("icategoryid", id)
					.eq("iwebsiteid", pService.getCurrentSiteId()).findUnique();
		} else {
			return null;
		}
	}

	public List<CategoryThreeLevel> getCategorysLevel(String skuParam, int language) {

		String sql = "select t1.csku,t3.ctitle,t2.dcreatedate,t5.cname,t4.ilevel from  "
				+ "t_product_category_mapper t1 inner join t_product_base t2 on t2.csku=t1.csku  "
				+ "inner join t_product_translate t3 on t3.csku=t2.csku and t3.ilanguageid= " + language + " "
				+ "inner join t_category_base t4 on t4.iid=t1.icategory  "
				+ "inner join t_category_name t5 on t5.icategoryid=t1.icategory and t5.ilanguageid= " + language + " "
				+ "where t4.ilevel in (1,2,3) and t1.csku='" + skuParam + "' ";
		List<CategoryThreeLevel> list = Lists.newArrayList();
		SqlQuery createSqlQuery = Category.db().createSqlQuery(sql);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {
				CategoryThreeLevel categoryThreeLevel = new CategoryThreeLevel();
				categoryThreeLevel.setCsku(sqlRow.getString("csku"));
				categoryThreeLevel.setCtitle(sqlRow.getString("ctitle"));
				categoryThreeLevel.setDcreatedate(sqlRow.getTimestamp("dcreatedate"));
				categoryThreeLevel.setCname(sqlRow.getString("cname"));
				categoryThreeLevel.setIlevel(sqlRow.getInteger("ilevel"));
				list.add(categoryThreeLevel);
			}
		}
		return list;
	}

	/**
	 * 
	 * @Title: getCategoryIdsByParentId
	 * @Description: 查询一级品类下的所有子类id
	 * @param @param
	 *            parent categoryId
	 * @param @return
	 *            参数
	 * @return List<Integer> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年3月16日
	 */
	public List<Integer> getCategoryIdsByParentId(int categoryId) {
		List<Category> categories = Category.db().find(Category.class).where().eq("iparentid", categoryId)
				.eq("iwebsiteid", pService.getCurrentSiteId()).findList();
		List<Integer> categoryIds = Lists.newArrayList();
		if (null != categories && categories.size() > 0) {
			for (Category c : categories) {
				categoryIds.add(c.getIid());
				List<Category> cList = Category.db().find(Category.class).where().eq("iparentid", c.getIid())
						.eq("iwebsiteid", pService.getCurrentSiteId()).findList();
				if (null != cList && cList.size() > 0) {
					for (Category cc : cList) {
						categoryIds.add(cc.getIid());
						List<Category> cListt = Category.db().find(Category.class).where().eq("iparentid", cc.getIid())
								.eq("iwebsiteid", pService.getCurrentSiteId()).findList();
						if (null != cListt && cListt.size() > 0) {
							for (Category ccc : cListt) {
								categoryIds.add(ccc.getIid());
							}
						}
					}
				}
			}
		}
		return categoryIds;
	}
}
