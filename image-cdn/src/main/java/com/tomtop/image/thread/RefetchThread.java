/**
 * 
 */
package com.tomtop.image.thread;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.collect.FluentIterable;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;
import com.tomtop.image.services.IImageService;

/**
 * 图片抓取线程
 * 
 * @author lijun
 */
public class RefetchThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(RefetchThread.class);

	List<String> imgPaths;

	@Autowired
	IImageService service;

	@Autowired()
	@Qualifier("fastdfs")
	IImagePlugin imgPlugin;

	@Autowired
	IFileRouteService routeService;

	public RefetchThread(List<String> imgPaths) {
		this.imgPaths = imgPaths;
	}

	@Override
	public void run() {

		if (imgPaths == null) {
			return;
		}

		FluentIterable.from(imgPaths).forEach(
				path -> {
					boolean existed = routeService.existed(path);
					if (existed) {
						return;
					}
					Image img = this.service.getImageWithContentByPath(path);
					if (img == null) {
						return;
					}
					String fastUrl = img.getCfastdfsurl();

					String type = img.getCcontenttype();
					if (type == null || type.length() == 0) {
						return;
					}
					String[] types = type.split("/");
					if (types.length < 2) {
						return;
					}
					String ext_name = "javascript".endsWith(types[1]) ? "js"
							: types[1];

					if (StringUtils.isNotEmpty(fastUrl)) {
						routeService.insert(path, fastUrl, ext_name, null,
								null, img.getCmd5(), false);
					}
					ImageBo ib = new ImageBo(img.getBcontent(), ext_name);
					String fullPath = this.imgPlugin.save(ib);
					if (StringUtils.isNotEmpty(fullPath)) {
						routeService.insert(path, fullPath, ext_name, null,
								null, img.getCmd5(), false);
					}

				});

		logger.debug(Thread.currentThread().getName() + " end");

	}

}
