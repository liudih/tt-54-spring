package com.tomtop.image.mappers;

import java.util.List;
import java.util.Map;

import com.tomtop.image.models.dto.Image;
import com.tomtop.image.models.dto.Range;

/**
 * 
 * @author lijun
 *
 */
public interface FileRouteMapper {

	/**
	 * insert 操作
	 * 
	 * @param paras
	 */
	public int insert(Map<String, Object> paras);

	/**
	 * 查询操作
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> select(Map<String, Object> paras);

	/**
	 * 
	 * @param paras
	 * @return
	 */
	public int update(Map<String, Object> paras);

	/**
	 * 该方法是临时方法,为了跑压缩图用
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> selectXy(Map<String, Object> paras);

	/**
	 * 获取数据库最大id
	 * 
	 * @return
	 */
	public int getMaxId();

	public int count(Map<String, Object> paras);

	public List<Image> selectRoute(Map<String, Object> paras);

	public Range selectMaxMin(Map<String, Object> paras);

	public int updateBamazon(Map<String, Object> paras);
}
