package com.tomtop.image.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.tomtop.image.models.dto.Image;

public interface ImageCacheMapper {

	@Select("SELECT iid, cpath, bcontent, ccontenttype, cmd5 "
			+ "FROM t_img_cache WHERE cpath = #{0} AND iwidth=#{1} "
			+ "AND iheight=#{2} limit 1")
	Image getImageWithContentByPath(String path, int width, int height);

	@Insert("INSERT INTO t_img_cache (cpath, bcontent, ccontenttype, cmd5, iwidth, iheight) "
			+ "VALUES (#{0.cpath}, #{0.bcontent}, #{0.ccontenttype}, #{0.cmd5}, #{1}, #{2})")
	@Options(useGeneratedKeys = true, keyProperty = "0.iid", keyColumn = "iid")
	long createImage(Image img, int width, int height);

	@Select("SELECT iid, cpath, ccontenttype, cmd5 FROM t_img_cache WHERE cpath = #{0} "
			+ "AND iwidth=#{1} AND iheight=#{2} limit 1")
	Image getImageWithoutContentByPath(String cpath, int width, int height);

	@Insert("UPDATE t_img_cache SET bcontent = #{0.bcontent}, cmd5 = #{0.cmd5} where cpath = #{0.cpath} "
			+ "AND iwidth=#{1} AND iheight=#{2}")
	long updateImage(Image image, int width, int height);

	
	List<Image> getImageByLimit(Map<String,Object> paras);
	int getMaxId();
	
	boolean updateFastdfsUrl(Map<String,Object> paras);
}
