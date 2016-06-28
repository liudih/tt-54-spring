package com.tomtop.image.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.image.models.dto.Image;

public interface ImageMapper {

	@Select("SELECT iid, cpath, bcontent, ccontenttype, cmd5 FROM t_img WHERE cpath = #{path}")
	Image getImageWithContentByPath(String path);

	@Select("SELECT iid, cpath, ccontenttype, cmd5 FROM t_img WHERE cpath = #{path}")
	Image getImageWithoutContentByPath(String path);

	@Insert("INSERT INTO t_img (cpath, bcontent, ccontenttype, cmd5) "
			+ "VALUES (#{cpath}, #{bcontent}, #{ccontenttype}, #{cmd5})")
	@Options(useGeneratedKeys = true, keyProperty = "iid", keyColumn = "iid")
	public long createImage(Image img);

	@Select("<script>select * from t_img where bcontent is NOT NULL <if test=\"contenttype != null\">"
			+ "and ccontenttype like '%${contenttype}%' </if><if test=\"path != null\">and cpath like '%${path}%' </if>"
			+ "order by iid desc limit #{pageSize} offset (#{pageSize} * (#{pageNum} - 1))"
			+ "</script>")
	List<Image> getImgByPage(@Param("contenttype") String contenttype,
			@Param("path") String path, @Param("pageSize") Integer pageSize,
			@Param("pageNum") Integer pageNum);

	@Select("<script>select count(*) from t_img where bcontent is NOT NULL <if test=\"contenttype != null\">"
			+ "and ccontenttype like '%${contenttype}%' </if><if test=\"path != null\">and cpath like '%${path}%' </if>"
			+ "</script>")
	Integer getImgCount(@Param("contenttype") String contenttype,
			@Param("path") String path);

	@Delete("DELETE FROM t_img WHERE iid = #{0}")
	int deleteImageById(Integer iid);

	@Insert("UPDATE t_img SET bcontent = #{bcontent}, cmd5 = #{cmd5} where cpath = #{cpath}")
	public long updateImage(Image img);

	@Delete("<script>delete from t_img where cpath in "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach></script>")
	public int deleteImageByPaths(@Param("list") List<String> paths);

	/**
	 * 
	 * @Title: getImageById
	 * @Description: TODO(通过id查询图片)
	 * @param @param id
	 * @param @return
	 * @return Img
	 * @throws 
	 * @author yinfei
	 */
	@Select("select * from t_img where iid = #{templateId}")
	Image getImageById(int id);
	
	
	/**
	 * 查询图片
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> getImageByLimit(Map<String, Object> paras);
	
	/**
	 * 获取数据库最大id
	 * 
	 * @return
	 */
	public int getMaxId();
	
	
	public boolean updateFastdfsUrl(Map<String, Object> paras);
}
