package com.tomtop.management.event;

/**
 * @ClassName: MessageBaseEvent
 * @Description: eventbus 删除缓存事件
 * @author fcl
 * @date 2015年12月30日
 *
 */
public class AdvertCacheEvent {

	private String tableName;

	private EventType eventType;

	public AdvertCacheEvent(String tableName, EventType eventType) {
		super();
		this.tableName = tableName;
		this.eventType = eventType;
	}

	public String getTableName() {
		return tableName;
	}

	public EventType getEventType() {
		return eventType;
	}
	
}
