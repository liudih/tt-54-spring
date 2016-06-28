package com.tomtop.interaction.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.ReviewCountAndScoreDto;
import com.tomtop.product.models.dto.ReviewTotalStartDto;

public interface ProductReviewsMapper {

	/**
	 * 获取商品评论等信息
	 * 
	 * @param listingId
	 * @return
	 */
	@Select({
			"<script>",
			"select clistingid as listingId,",
			"round(cast(avg(foverallrating) as numeric),1) avgScore,",
			"count(iid) as reviewCount",
			" from t_interaction_comment where istate=#{istate}",
			"  <if test=\"list !=null\">",
			"and clistingid in",
			"<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\"",
			"	separator=\",\" close=\")\">#{item}</foreach></if>",
			"group by clistingid", "</script>" })
	List<ReviewCountAndScoreDto> getScoreListByListingIds(
			@Param("list") List<String> listingId, @Param("istate") int istate);

	/**
	 * 获取商品评论星级及数量
	 * 
	 * @param listingId
	 * @return
	 */
	@Select({ "select clistingid as listingId,",
			"round(cast(avg(foverallrating) as numeric),1) avgScore,",
			"count(iid) as reviewCount", " from t_interaction_comment where ",
			" clistingid=#{listingId}", "group by clistingid" })
	ReviewCountAndScoreDto getScoreByListingId(
			@Param("listingId") String listingId);

	/**
	 * 获取商品评论星级及数量
	 * 
	 * @param listingId
	 * @return
	 */
	@Select({ "select clistingid as listingId,",
			"round(cast(avg(foverallrating) as numeric),1) avgScore,",
			"count(iid) as reviewCount", " from t_interaction_comment where ",
			" istate=#{istate} and clistingid=#{listingId}",
			"group by clistingid" })
	ReviewCountAndScoreDto getScoreByListingIdAdnStatue(
			@Param("listingId") String listingId, @Param("istate") int istate);

	/**
	 * 获取商品1、2、3、4、5星级对应数量
	 * 
	 * @param listingId
	 * @return
	 */
	@Select({
			"select ROUND(foverallrating) startNum,count(*) num from t_interaction_comment ",
			" where istate=#{istate} and clistingid=#{listingId}",
			"group by ROUND(foverallrating),clistingid" })
	List<ReviewTotalStartDto> getFoverallratingNumByListingId(
			@Param("listingId") String listingId, @Param("istate") int istate);

}
