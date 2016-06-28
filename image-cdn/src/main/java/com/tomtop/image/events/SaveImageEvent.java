package com.tomtop.image.events;

import com.tomtop.image.models.dto.Image;

/**
 * 保存图片事件
 * 
 * @author lijun
 *
 */
public class SaveImageEvent {
	private final Image image;

	public SaveImageEvent(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

}
