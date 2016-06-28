package com.tomtop.management.event;

import java.util.Date;

/**
 * @ClassName: MessageBaseEvent
 * @Description: eventbus 删除缓存事件
 * @author fcl
 * @date 2015年12月30日
 *
 */
public class SalePriceCacheEvent {

	private String listingId;

	private Float price;

	private Date dBegindate;

	private Date dEndDate;

	private EventType eventType;

	public SalePriceCacheEvent(String listingId, Float price, Date dBegindate, Date dEndDate, EventType eventType) {
		super();
		this.listingId = listingId;
		this.price = price;
		this.dBegindate = dBegindate;
		this.dEndDate = dEndDate;
		this.eventType = eventType;
	}

	public String getListingId() {
		return listingId;
	}

	public Float getPrice() {
		return price;
	}

	public Date getdBegindate() {
		return dBegindate;
	}

	public Date getdEndDate() {
		return dEndDate;
	}

	public EventType getEventType() {
		return eventType;
	}

}
