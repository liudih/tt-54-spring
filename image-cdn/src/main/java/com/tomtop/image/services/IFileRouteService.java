package com.tomtop.image.services;

import java.util.List;

import com.tomtop.image.models.dto.Image;
import com.tomtop.image.models.dto.Range;

/**
 * 
 * @author lijun
 *
 */
public interface IFileRouteService {

	/**
	 * 获取图片信息
	 * 
	 * @param path
	 * @return
	 */
	public Image getImageByPath(String path);

	/**
	 * 获取图片信息
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public Image getImageByPath(String path, Integer width, Integer height);

	public boolean insert(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5);

	public boolean insert(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5, boolean isCompressed);

	/**
	 * 更新图片信息
	 * 
	 * @param cpath
	 * @param croute
	 * @param ctype
	 * @param iwidth
	 * @param iheight
	 * @param cmd5
	 * @return
	 */
	public boolean update(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5);

	/**
	 * 检查是否已经存在
	 * 
	 * @param cpath
	 * @param iwidth
	 * @param iheight
	 * @return
	 */
	public boolean existed(String cpath, Integer iwidth, Integer iheight);

	public boolean existed(String cpath);

	/**
	 * 该方法是临时方法,为了跑压缩图用
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> selectXy(String path);

	/**
	 * 获取原图数据库最大id
	 * 
	 * @return
	 */
	public int getOriginalMaxId();

	/**
	 * 获取 start=<iid<=end 的图片
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Image> getOriginalImageByLimit(int start, int end);

	/**
	 * 是否已经存在path的图片
	 * 
	 * @param path
	 * @return
	 */
	public boolean isExisted(String path);

	public boolean isExisted(String path, Integer iwidth, Integer iheight);

	public Image selectRoute(String path);

	public Image selectRoute(String path, Integer width, Integer height);

	public boolean updateById(int id, String croute, String ctype, String cmd5);

	public boolean changeCompressToTrueById(int id);

	public List<Image> getImage(int start, int end, int width, int height,
			boolean modified);

	public boolean updateById(int id, String croute, String cmd5);

	public List<Image> getImagesByLimit(int start, int end);

	/**
	 * 获取图片还没有转移到亚马逊的最大最小iid
	 */
	public Range getMaxMinNoAmazon();

	public boolean changeBamazonToTrueById(List<Integer> ids);

	public boolean changeBamazonToTrueById(int id);
}
