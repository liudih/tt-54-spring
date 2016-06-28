package com.tomtop.image.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;

/**
 * AmazonS3
 * 
 * @author lijun
 *
 */
// @Service("amazons3")
public class AmazonS3ImagePlugin implements IImagePlugin {

	private static Logger Logger = LoggerFactory
			.getLogger(AmazonS3ImagePlugin.class);

	private static final String TYPE = "image/jpg";

	@Autowired
	AmazonawsService service;

	@Autowired
	IFileRouteService routeService;

	@Override
	public int getPriority() {
		return 3;
	}

	public AmazonS3ImagePlugin() {

	}

	@Override
	public ImageBo getImage(String dir) {
		return null;
	}

	@Override
	public ImageBo getImage(String dir, Integer width, Integer height) {
		return null;
	}

	@Override
	public String save(ImageBo img) {
		return null;
	}

	@Override
	public byte[] getFile(String dir) {
		return null;
	}

	@Override
	public boolean saveProductOriginalImage(ImageBo img) {
		return true;
	}

	@Override
	public boolean save(List<ImageBo> imgs) {
		if (imgs == null) {
			return true;
		}
		try {
			imgs.forEach(img -> {
				StringBuilder sb = new StringBuilder();
				if (img.getWidth() == null) {
					sb.append("product/original/");
				} else {
					sb.append("product/xy/");
					sb.append(img.getWidth());
					sb.append("/");
					sb.append(img.getHeight());
					sb.append("/");
				}

				sb.append(img.getPath());
				String relativePath = sb.toString();
				this.service.upload(img.getData(), TYPE, relativePath);
				// 入数据库
				String path = img.getPath();
				String type = img.getType();
				Integer width = img.getWidth();
				Integer height = img.getHeight();

				// 如果已经存在该图片,则先删除
				Image routeImg = null;
				if (img.getWidth() == null) {
					routeImg = routeService.selectRoute(img.getPath());
				} else {
					routeImg = routeService.selectRoute(img.getPath(),
							img.getWidth(), img.getHeight());
				}

				if (routeImg == null) {
					routeService.insert(path, null, type, width, height, null);
				}

			});
			return true;
		} catch (Exception e) {
			Logger.error("batch save image error", e);
			return false;
		}
	}
}
