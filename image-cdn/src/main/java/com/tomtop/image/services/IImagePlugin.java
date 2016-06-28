package com.tomtop.image.services;

import java.util.List;

import com.tomtop.image.models.bo.ImageBo;

/**
 * 图片插件
 * 
 * @author lijun
 *
 */
public interface IImagePlugin {

	/**
	 * 获取优先级
	 * 
	 * @return
	 */
	public int getPriority();

	/**
	 * 获取dir路径的图片数据
	 * 
	 * @param dir
	 * @return
	 */
	public ImageBo getImage(String dir);

	/**
	 * 获取 width:height 大小图片
	 * 
	 * @param dir
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageBo getImage(String dir, Integer width, Integer height);

	/**
	 * 保存图片
	 * 
	 * @param img
	 * @return 图片路径
	 */
	public String save(ImageBo img);

	/**
	 * 获取文件bytes
	 * 
	 * @param dir
	 * @return
	 */
	public byte[] getFile(String dir);

	/**
	 * 保存产品原图
	 * 
	 * @param img
	 * @return
	 */
	public boolean saveProductOriginalImage(ImageBo img);

	public boolean save(List<ImageBo> imgs);

}
