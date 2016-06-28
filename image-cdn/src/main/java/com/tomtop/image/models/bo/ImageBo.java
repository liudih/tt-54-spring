package com.tomtop.image.models.bo;

/**
 * 
 * @author lijun
 *
 */
public class ImageBo {

	private final byte[] data;

	// 后缀名
	private final String type;

	private Integer width;

	private Integer height;

	private String path;

	public ImageBo(byte[] data, String type) {
		this.data = data;
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public String getType() {
		return type;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ImageBo [width=" + width + ", height=" + height + ", path="
				+ path + "]";
	}

}
