package com.tomtop.product.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.ProductImage;

public interface ProductImageMapper {
	int batchInsert(List<ProductImage> list);

	@Select("SELECT cimageurl from t_product_image WHERE clistingid = #{clistingid} and bbaseimage=true  LIMIT 1")
	String getBaseUrlByListingId(@Param("clistingid") String clistingid);

	@Select("SELECT * from t_product_image WHERE clistingid = #{clistingid} and bshowondetails=#{isShow}")
	List<ProductImage> getProductImgsByListingId(
			@Param("clistingid") String clistingid,
			@Param("isShow") Boolean isShow);

	@Select("select cimageurl from  t_product_image  where iorder = 1 and bbaseimage = true and clistingid =#{1} limit 1")
	String getProductBaseImageForEntityMapByListingId(String clistingId);

	@Delete("delete from t_product_image where clistingid=#{0} ")
	int deleteByListingId(String listingId);

	@Select("select cimageurl from  t_product_image  where iorder = 1 and bsmallimage = true and clistingid =#{1}")
	String getProductSmallImageForMemberViewsByListingId(String listingId);

	@Select("<script>SELECT * from t_product_image WHERE clistingid IN "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach> "
			+ "AND cimageurl LIKE 'http://%'</script>")
	List<ProductImage> getImageUrlByClistingid(
			@Param("list") List<String> clistingids);

	int updateImages(ProductImage record);

	@Select("SELECT * from t_product_image WHERE cimageurl LIKE 'http://%' LIMIT #{0}")
	List<ProductImage> getExternalImageUrls(int limit);

	List<ProductImage> getProductSmallImageForMemberViewsByListingIds(
			List<String> listingId);

	@Select("<script>SELECT * from t_product_image WHERE clistingid IN "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach> "
			+ "AND cimageurl LIKE 'http://%'</script>")
	List<ProductImage> getExternalImageUrlsByListingIDs(
			@Param("list") List<String> listingIDs);

	@Select("select cimageurl from  t_product_image  where clistingid =#{0}")
	List<String> getUrlByListingId(String listingId);

}