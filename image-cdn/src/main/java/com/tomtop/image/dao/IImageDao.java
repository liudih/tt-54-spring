package com.tomtop.image.dao;

import java.util.List;
import java.util.Map;

import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;

/**
 * 
 * @author lijun
 *
 */
public interface IImageDao {

	/**
	 * 获取图片信息(带图片数据)
	 * 
	 * @param path
	 * @return
	 */
	public Image getImageWithContentByPath(String path);

	/**
	 * 获取图片信息(不带图片数据)
	 * 
	 * @param path
	 * @return
	 */
	public Image getImageWithoutContentByPath(String path);

	/**
	 * 获取 width:height 大小图片(不带图片数据)
	 * 
	 * @param dir
	 * @param width
	 * @param height
	 * @return
	 */
	public Image getImageWithoutContent(String dir, Integer width,
			Integer height);

	/**
	 * 获取 width:height 大小图片(带图片数据)
	 * 
	 * @param dir
	 * @param width
	 * @param height
	 * @return
	 */
	public Image getImageWithContent(String dir, Integer width, Integer height);

	public List<Image> getImageByLimit(Map<String, Object> paras);

	public List<Image> getImageCacheByLimit(Map<String, Object> paras);

	/**
	 * 获取数据库最大id
	 * 
	 * @return
	 */
	public int getMaxId();

	/**
	 * 获取数据库最大id
	 * 
	 * @return
	 */
	public int getCacheMaxId();

	public boolean updateFastdfsUrl(Map<String, Object> paras);

	public boolean updateCacheFastdfsUrl(Map<String, Object> paras);

}
