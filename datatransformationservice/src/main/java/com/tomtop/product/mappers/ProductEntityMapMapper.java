package com.tomtop.product.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.ProductAttributeItem;
import com.tomtop.product.models.dto.ProductEntityMap;

public interface ProductEntityMapMapper {
	int deleteByPrimaryKey(Integer iid);

	int insert(ProductEntityMap record);

	int batchInsert(List<ProductEntityMap> list);

	int insertSelective(ProductEntityMap record);

	@Select("SELECT tentity.iid,tentity.clistingid,tentity.csku,tentity.bshow,tkey.ilanguageid,tentity.ikey,tentity.ivalue, "
			+ " max(case when tkey.ckeyname is null then key_de.ckeyname else tkey.ckeyname end) ckeyname, "
			+ " max(case when tvalue.cvaluename is null then value_de.cvaluename else tvalue.cvaluename end) cvaluename "
			+ " FROM  t_product_entity_map  tentity "
			+ " left join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid"
			+ " left join t_attribute_value_name tvalue on tentity.ivalue= tvalue.ivalueid and tvalue.ilanguageid=tkey.ilanguageid "
			+ " left join t_attribute_key_name key_de on tentity.ikey=key_de.ikeyid and key_de.ilanguageid=tkey.ilanguageid"
			+ " left join t_attribute_value_name value_de on tentity.ivalue= value_de.ivalueid and value_de.ilanguageid=tkey.ilanguageid "
			+ " WHERE tentity.clistingid = #{0} and tkey.ilanguageid=#{1}"
			+ " group by tentity.iid,tentity.clistingid,tentity.csku,tentity.bshow,tkey.ilanguageid,tentity.ikey,tentity.ivalue")
	List<ProductEntityMap> getProductEntityMapByListingId(String listingID,
			Integer lang);

	@Select("SELECT tentity.iid,tentity.clistingid,tentity.csku,tentity.bshow,tkey.ilanguageid,tentity.ikey,tentity.ivalue, "
			+ " max(case when tkey.ckeyname is null then key_de.ckeyname else tkey.ckeyname end) ckeyname, "
			+ " max(case when tvalue.cvaluename is null then value_de.cvaluename else tvalue.cvaluename end) cvaluename "
			+ " FROM  t_product_entity_map  tentity "
			+ " left join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid"
			+ " left join t_attribute_value_name tvalue on tentity.ivalue= tvalue.ivalueid and tvalue.ilanguageid=tkey.ilanguageid "
			+ " left join t_attribute_key_name key_de on tentity.ikey=key_de.ikeyid and key_de.ilanguageid=tkey.ilanguageid"
			+ " left join t_attribute_value_name value_de on tentity.ivalue= value_de.ivalueid and value_de.ilanguageid=tkey.ilanguageid "
			+ " WHERE tentity.clistingid = #{0} "
			+ " group by tentity.iid,tentity.clistingid,tentity.csku,tentity.bshow,tkey.ilanguageid,tentity.ikey,tentity.ivalue")
	List<ProductEntityMap> getListByListingId(String listingID);

	@Select("SELECT tvalue.cvaluename "
			+ " FROM  t_product_entity_map tentity "
			+ " left join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid"
			+ " left join t_attribute_value_name tvalue on tentity.ivalue= tvalue.ivalueid and tvalue.ilanguageid=tkey.ilanguageid "
			+ " WHERE tentity.clistingid = #{0} and tkey.ckeyname='brand'"
			+ " limit 1")
	String getProductBrandByListingId(String listingID);

	List<ProductEntityMap> getProductEntityMapListByListingIds(
			@Param("list") List<String> listingIds, Integer languageid,
			Integer websiteId);

	@Delete("delete from t_product_entity_map where clistingid=#{0} ")
	int deleteByListingId(String listingId);

	@Select("<script>"
			+ "SELECT * FROM  t_product_entity_map tentity "
			+ "WHERE clistingid IN "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach> "
			+ "</script>")
	List<ProductEntityMap> getProductEntityMapByListingids(
			@Param("list") List<String> listingIDs);

	@Delete("delete from t_product_entity_map where clistingid =#{0} and ikey = #{1}")
	int deleteByListingIdAndKeyId(String listingId, Integer keyId);

	@Select("<script>"
			+ "SELECT clistingid as listingId,csku as sku,ikey as keyId,ivalue as valueId,iwebsiteid as websiteId,bshow as visible,multiattribute,ckeyname as key,cvaluename as value  FROM  t_product_entity_map tentity "
			+ "inner join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid  "
			+ "inner join t_attribute_value_name  tvalue on tentity.ivalue= tvalue.ivalueid and tkey.ilanguageid=tvalue.ilanguageid "
			+ "WHERE clistingid IN "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach> "
			+ "</script>")
	List<ProductAttributeItem> getProductAttributeByListingids(
			@Param("list") List<String> listingIDs);

	@Select("select ikey as keyId,ivalue as valueId,iwebsiteid as websiteId,bshow as visible,multiattribute,ckeyname as key,cvaluename as value  FROM  t_product_entity_map tentity "
			+ "inner join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid and tkey.ilanguageid=#{1} "
			+ "inner join t_attribute_value_name  tvalue on tentity.ivalue= tvalue.ivalueid and tkey.ilanguageid=tvalue.ilanguageid and tvalue.ilanguageid=#{1}"
			+ "where clistingid = #{0} ")
	List<ProductAttributeItem> getProductItemsByListingAndLanguage(
			String clistingid, int languageid);

	@Select("select tentity.clistingid as clistingid, tentity.csku as csku, min(tvalue.cvaluename) as cvaluename, tkey.ckeyname as ckeyname FROM t_product_entity_map tentity "
			+ "inner join t_attribute_key_name tkey on tentity.ikey=tkey.ikeyid and tkey.ilanguageid=#{1} and tkey.ckeyname = #{2} "
			+ "inner join t_attribute_value_name tvalue on tentity.ivalue= tvalue.ivalueid and tvalue.ilanguageid = #{1} "
			+ "where clistingid in (select distinct m.clistingid "
			+ "from t_product_entity_map m "
			+ "where m.csku in ( "
			+ "select distinct ts.csku FROM t_product_base tb2 "
			+ "inner join t_product_multiattribute_sku ts on ts.cparentsku = tb2.cparentsku "
			+ "where tb2.clistingid = #{0} and tb2.iwebsiteid = #{3} "
			+ ") and m.iwebsiteid = #{3}) group by tentity.clistingid, tentity.csku, tkey.ckeyname")
	List<ProductEntityMap> getEntityMaps(String listingID, Integer langid,
			String keyname, Integer websiteid);
}