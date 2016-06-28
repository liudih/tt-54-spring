package com.tomtop.image.dao;

import java.util.List;
import java.util.Map;

import com.tomtop.image.models.dto.Image;
import com.tomtop.image.models.dto.Range;

/**
 * 
 * @author lijun
 *
 */
public interface IFileRouteDao {

	/**
	 * insert 操作
	 * 
	 * @param paras
	 * @return
	 */
	public boolean insert(Map<String, Object> paras);

	public boolean update(Map<String, Object> paras);

	public List<Image> select(Map<String, Object> paras);

	/**
	 * 该方法是临时方法,为了跑压缩图用
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> selectXy(Map<String, Object> paras);

	/**
	 * 获取原图数据库最大id
	 * 
	 * @return
	 */
	public int getOriginalMaxId();

	public int count(Map<String, Object> paras);

	public List<Image> selectRoute(Map<String, Object> paras);

	public Range getMaxMin(Map<String, Object> paras);

	public boolean updateBamazon(List<Integer> ids);

	public boolean updateBamazon(int id);
}
