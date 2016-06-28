package com.tomtop.image.handlers;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.tomtop.image.events.SaveImageEvent;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;

/**
 * 保存图片事件处理器 该处理器会把图片保存到fastdfs服务器
 * 
 * @author lijun
 *
 */
@Service
public class SaveImageEventHandler implements IEventHandler {

	private static final Logger Logger = LoggerFactory
			.getLogger(SaveImageEventHandler.class);

	private static final String JAVASCRIPT = "javascript";

	@Autowired
	TrackerClient tClient;;

	@Autowired
	@Qualifier("fastdfs")
	IImagePlugin imgPlugin;

	@Autowired
	IFileRouteService routeService;

	@Subscribe
	public void process(SaveImageEvent payload) {
		Logger.debug("****************start to handle SaveImageEvent****************");

		Image image = payload.getImage();

		if (image == null) {
			Logger.debug("image is null so igonre upload");
			return;
		}

		byte[] file_buff = image.getBcontent();
		if (file_buff == null || file_buff.length == 0) {
			Logger.debug("image byte is null so igonre upload");
			return;
		}
		String type = image.getCcontenttype();
		if (type == null || type.length() == 0) {
			Logger.debug("image type is null so igonre upload");
			return;
		}
		String[] types = type.split("/");
		if (types.length < 2) {
			Logger.debug("resolve image type error so igonre upload");
			return;
		}
		String ext_name = JAVASCRIPT.endsWith(types[1]) ? "js" : types[1];

		ImageBo bo = new ImageBo(image.getBcontent(), ext_name);

		String fullDir = imgPlugin.save(bo);
		if (!StringUtils.isEmpty(fullDir)) {
			routeService.insert(image.getCpath(), fullDir, ext_name,
					image.getIwidth(), image.getIheight(), image.getCmd5());
		}

	}
}
