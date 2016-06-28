package com.tomtop.image.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.tomtop.image.events.SaveImageEvent;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IImagePlugin;
import com.tomtop.image.services.IImageService;

/**
 * 数据库取图片服务类
 * 
 * @author lijun
 *
 */
//@Service("db")
public class DatabaseImagePlugin implements IImagePlugin {

	private static Logger Logger = LoggerFactory
			.getLogger(DatabaseImagePlugin.class);

	@Autowired
	IImageService imgService;

	@Autowired
	EventBus eventBus;

	@Override
	public int getPriority() {
		return 2;
	}

	@Override
	public ImageBo getImage(String dir) {
		// 数据库取数据
		Image img = imgService.getImageWithContentByPath(dir);
		if (img != null) {
			ImageBo bo = new ImageBo(img.getBcontent(), img.getCcontenttype());
			return bo;
		}
		return null;
	}

	@Override
	public ImageBo getImage(String dir, Integer width, Integer height) {
		ImageBo img = this.imgService.getImageWithContent(dir, width, height);
		if (img != null) {
			return img;
		}

		Image imgDo = imgService.getImageWithContentByPath(dir);
		if (imgDo != null) {
			Logger.debug("*****************get image from db*****************");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				// 开始裁剪图片并压缩
				Thumbnails.of(new ByteArrayInputStream(imgDo.getBcontent()))
						.size(width, height).keepAspectRatio(true)
						.useOriginalFormat().toOutputStream(out);

				img = new ImageBo(out.toByteArray(), imgDo.getCcontenttype());
				// 保存图片到fastdfs
				imgDo.setBcontent(out.toByteArray());
				imgDo.setIwidth(width);
				imgDo.setIheight(height);
				SaveImageEvent event = new SaveImageEvent(imgDo);
				eventBus.post(event);
				return img;
			} catch (IOException e) {
				Logger.error("image compress failed", e);
			}
		}
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
		return true;
	}

}
