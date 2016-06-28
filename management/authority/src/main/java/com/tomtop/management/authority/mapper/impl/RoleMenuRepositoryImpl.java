package com.tomtop.management.authority.mapper.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
/*import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;*/
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Transaction;
import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.tomtop.management.authority.mapper.IRoleMenuRepository;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.ebean.authority.model.AdminMenu;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.MenuRoleMap;

@Service
public class RoleMenuRepositoryImpl implements IRoleMenuRepository {

	private static Logger log = Logger.getLogger(RoleMenuRepositoryImpl.class
			.getName());

	@Autowired
	CurrentUserService currentUserService;
	@Override
	public List<AdminMenu> getMenusByRoleId(int roleId) {
		List<AdminMenu> list = Lists.newArrayList();

		String query = "SELECT  m.id,m.menu_name menuName,m.menu_level menuLevel,m.menu_url menuUrl,m.parent_id parentId ,m.menu_style menuStyle, "
				+ "m.menu_position menuPosition FROM admin_menu m  WHERE 1=1 "
				+ " AND EXISTS( "
				+ " SELECT mm.menu_id FROM admin_role r INNER JOIN menu_role_map mm "
				+ " on r.id=mm.role_id"
				+ " where mm.role_id="
				+ roleId
				+ " and m.id=mm.menu_id  and r.status='1' )"
				+ " and m.status='1' ORDER BY m.menu_level, m.menu_position";
		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(query);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {
				AdminMenu adminRole = new AdminMenu();
				adminRole.setId(sqlRow.getLong("id"));
				adminRole.setMenuName(sqlRow.getString("menuName"));
				adminRole.setMenuLevel(sqlRow.getInteger("menuLevel"));
				adminRole.setMenuUrl(sqlRow.getString("menuUrl"));
				adminRole.setParentId(sqlRow.getLong("parentId"));
				adminRole.setMenuStyle(sqlRow.getString("menuStyle"));
				adminRole.setMenuPosition(sqlRow.getString("menuPosition"));
				list.add(adminRole);
			}
		}
		return list;
	}

	@Override
	public List<AdminMenu> getMenusByUserId(Long userId) {
		List<AdminMenu> list = Lists.newArrayList();

		String query = "SELECT m.id,m.menu_name menuName,m.menu_level menuLevel,m.menu_url menuUrl,m.parent_id parentId ,m.menu_style menuStyle,"
				+ "m.menu_position menuPosition FROM admin_menu m  WHERE 1=1 "
				+ " AND EXISTS( "
				+ " SELECT mm.menu_id from user_role_map rm INNER JOIN admin_role r on rm.role_id=r.id   "
				+ "  INNER JOIN menu_role_map mm "
				+ " 	on r.id=mm.role_id "
				+ " 	where rm.user_id="
				+ userId
				+ " 	and m.id=mm.menu_id "
				+ " and r.status='1' "
				+ " ) "
				+ " and m.status='1' "
				+ "  ORDER BY m.menu_level, m.menu_position";
		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(query);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {
				AdminMenu adminMenu = new AdminMenu();
				adminMenu.setId(sqlRow.getLong("id"));
				adminMenu.setMenuName(sqlRow.getString("menuName"));
				adminMenu.setMenuLevel(sqlRow.getInteger("menuLevel"));
				adminMenu.setMenuUrl(sqlRow.getString("menuUrl"));
				adminMenu.setParentId(sqlRow.getLong("parentId"));
				adminMenu.setMenuStyle(sqlRow.getString("menuStyle"));
				adminMenu.setMenuPosition(sqlRow.getString("menuPosition"));
				list.add(adminMenu);
			}
		}
		return list;
	}
	@Override
	public List<AdminMenu> getAllMenu() {
		List<AdminMenu> list = Lists.newArrayList();

		String query = "SELECT m.id,m.menu_name menuName,m.menu_level menuLevel,m.menu_url menuUrl,m.parent_id parentId ,m.menu_style menuStyle,"
				+ "m.menu_position menuPosition FROM admin_menu m  WHERE 1=1 "
				+ " and m.status='1' "
				+ "  ORDER BY m.menu_level, m.menu_position";
		SqlQuery createSqlQuery = AdminRole.db().createSqlQuery(query);
		Set<SqlRow> findSet = createSqlQuery.findSet();
		if (CollectionUtils.isNotEmpty(findSet)) {
			for (SqlRow sqlRow : findSet) {
				AdminMenu adminMenu = new AdminMenu();
				adminMenu.setId(sqlRow.getLong("id"));
				adminMenu.setMenuName(sqlRow.getString("menuName"));
				adminMenu.setMenuLevel(sqlRow.getInteger("menuLevel"));
				adminMenu.setMenuUrl(sqlRow.getString("menuUrl"));
				adminMenu.setParentId(sqlRow.getLong("parentId"));
				adminMenu.setMenuStyle(sqlRow.getString("menuStyle"));
				adminMenu.setMenuPosition(sqlRow.getString("menuPosition"));
				list.add(adminMenu);
			}
		}
		return list;
	}
	@Override
	public AdminMenu getMenuById(int menuId) {
		return AdminMenu.db().find(AdminMenu.class, menuId);
	}
	@Override
	public List<String> getRoleNameByPath(String path,String jobNumber) {
		String sql="select r.role_name roleName from admin_role r  INNER JOIN user_role_map m "+
				   " on r.id= m.role_id  INNER JOIN user_manager um  "+
				" on m.user_id=um.id "+
				" where  um.job_number='"+jobNumber+"' "+
				" and  r.id in (  "+
									" SELECT m.role_id from admin_menu am INNER JOIN menu_role_map m on am.id=m.menu_id  "+
									" where am.menu_url='"+path+"') limit 1";
		String rote="";
		SqlQuery createSqlQuery = AdminMenu.db().createSqlQuery( sql);
		 List<SqlRow> findList = createSqlQuery.findList();
		 List<String> roteList=Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(findList)){
			roteList=Lists.transform(findList, i->i.getString("roleName"));
		}
		
		return roteList;
	}

	@Override
	public List<MenuRoleMap> getMenuByRoleIdMap(Integer roleId) {
		return MenuRoleMap.db().find(MenuRoleMap.class).where().eq("role_id", roleId).findList();
	}
	@Override
	public void deleteMenuByRoleIdMap(List<MenuRoleMap> list,Transaction transaction) {
		if(CollectionUtils.isNotEmpty(list)){
			MenuRoleMap.db().deleteAll(list ,transaction);
		}else{
			log.info("deleteMenuByRoleIdMap idList empty!");
		}
	}
	@Override
	public void addMenuByRoleIdMap(List<MenuRoleMap> list,Transaction transaction) {
		if(CollectionUtils.isNotEmpty(list)){
			MenuRoleMap.db().saveAll(list, transaction);
		}else{
			log.info("addMenuByRoleIdMap list empty!");
		}
	}
	@Override
	public void updateMenuList(List<MenuRoleMap> list,Transaction transaction) {
		if(CollectionUtils.isNotEmpty(list)){
			MenuRoleMap.db().updateAll(list, transaction);
		}else{
			log.info("updateMenuList list empty!");
		}
	}
	@Override
	public String updateRoleMenuMap(final Integer id,List<Integer> list) {
		log.info("updateRoleMenuMap id:"+id+"  list:"+list);
		if(CollectionUtils.isEmpty(list)){
			log.info("updateRoleMenuMap list empty!");
			return "N";
		}
		AdminRole find = AdminRole.db().find(AdminRole.class, id);
		if(find==null){
			log.info("updateRoleMenuMap AdminRole not exist!");
			return "N";
		}
		
		String result="";
		Transaction transaction = MenuRoleMap.db().beginTransaction();
		
		String currentUser=currentUserService.getCurrentUserJobNumber();//当前用户
		Timestamp timestamp=new Timestamp(new Date().getTime());
		try{
			transaction.setBatchSize(50);
			transaction.setBatchGetGeneratedKeys(false);
			
			List<MenuRoleMap> menuByRoleIdMap = getMenuByRoleIdMap(id);
			final Map<Integer,MenuRoleMap> map=Maps.newHashMap();
			Set<Long> oldMapIdSet=Sets.newHashSet();
			if(CollectionUtils.isNotEmpty(menuByRoleIdMap)){
				for(MenuRoleMap menuRoleMap : menuByRoleIdMap ){
					oldMapIdSet.add(menuRoleMap.getId());
					map.put(menuRoleMap.getMenuId(), menuRoleMap);
				}
			}
			List<MenuRoleMap> updateList=Lists.newArrayList();
			List<Long> deleteList=Lists.newArrayList();
			List<MenuRoleMap> addList=Lists.newArrayList();
			
			Set<Long> existMapIdSet=Sets.newHashSet();//已存在的
			for(Integer item : list){
				if(map.containsKey(item)){//更新
					MenuRoleMap menuRoleMap = map.get(item);
					menuRoleMap.setWhenModified(timestamp);
					menuRoleMap.setWhoModified(currentUser);
					updateList.add(menuRoleMap);
					existMapIdSet.add(menuRoleMap.getId());
				}else{//添加
					MenuRoleMap menuRoleMap =new MenuRoleMap();
					menuRoleMap.setMenuId(item);
					menuRoleMap.setRoleId(id);
					menuRoleMap.setWhoCreated(currentUser);
					menuRoleMap.setWhenCreated(timestamp);
					addList.add(menuRoleMap);
				}
			}
			//需要删除的
			SetView<Long> difference = Sets.difference(oldMapIdSet, existMapIdSet);
			deleteList.addAll(difference);
			

			List<MenuRoleMap> deleteBeanList=Lists.transform(deleteList, i->{
				MenuRoleMap mm=new MenuRoleMap();
				mm.setId(i);
				return mm;
			});
			deleteMenuByRoleIdMap(deleteBeanList,transaction);
			addMenuByRoleIdMap(addList,transaction);
			updateMenuList(updateList, transaction);
			transaction.commit();
			result="Y";
		}catch(Exception e){
			log.error("updateRoleMenuMap error:",e);
			transaction.rollback();
			result="E";
		}
		return result;
	}
}
