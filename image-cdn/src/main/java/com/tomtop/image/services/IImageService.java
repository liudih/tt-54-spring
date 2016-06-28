package com.tomtop.image.services;

import java.util.List;

import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;

public interface IImageService {

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
	public ImageBo getImageWithoutContent(String dir, Integer width,
			Integer height);

	/**
	 * 获取 width:height 大小图片(带图片数据)
	 * 
	 * @param dir
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageBo getImageWithContent(String dir, Integer width, Integer height);

	/**
	 * 获取 start=<iid<=end 的图片
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Image> getImageByLimit(int start, int end);

	/**
	 * 获取 start=<iid<=end 的图片
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Image> getImageCacheByLimit(int start, int end);

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

	public boolean updateFastdfsUrl(String url, int id);

	public boolean updateCacheFastdfsUrl(String url, int id);
}
