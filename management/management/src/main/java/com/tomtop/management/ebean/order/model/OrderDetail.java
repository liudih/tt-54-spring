package com.tomtop.management.ebean.order.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_detail")
public class OrderDetail extends OrderModel {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 2905010901096637880L;
	/**
	 * 
	 */
	private String cid;
	private String title;
	
	@Column(name = "order_id")
	private Integer orderid;
	
	@Column(name = "listing_id")
	private String listingid;
	
	private Integer qty;
	
	private Double price;
	
	@Column(name = "total_prices")
	private Double totalprices;
	
	private String sku;
	
	@Column(name = "order_reate_date")
	private Date createdate;
	
	@Column(name = "parent_id")
	private String parentid;
	
	@Column(name = "original_price")
	private Double originalprice;
	
	private Double weight;
	
	@Column(name = "comment_id")
	private Integer commentid;	//评论id


	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getListingid() {
		return listingid;
	}

	public void setListingid(String listingid) {
		this.listingid = listingid;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalprices() {
		return totalprices;
	}

	public void setTotalprices(Double totalprices) {
		this.totalprices = totalprices;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Double getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(Double originalprice) {
		this.originalprice = originalprice;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getCommentid() {
		return commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}
}
