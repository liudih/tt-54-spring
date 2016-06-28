package com.tomtop.image.controllers;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;
import com.tomtop.image.services.impl.ImageHelper;

/**
 * 
 * 图片服务对外接口
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping(value = "product", method = RequestMethod.GET)
public class ImageApiController {

	private static Logger logger = LoggerFactory
			.getLogger(ImageApiController.class);
	public static String IF_NONE_MATCH = "If-None-Match";
	public static String CACHE_CONTROL = "Cache-Control";
	public static String ETAG = "Etag";

	@Autowired
	ImageHelper imgHelper;

	@Autowired
	IFileRouteService routeService;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	@Qualifier("fastdfs")
	IImagePlugin imgPlugin;

	/**
	 * 获取原图
	 */
	@RequestMapping(value = "/original/**")
	@ResponseBody
	public void getOriginal(HttpServletRequest request,
			HttpServletResponse response) {
		String path = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request
				.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		AntPathMatcher apm = new AntPathMatcher();
		String dir = apm.extractPathWithinPattern(bestMatchPattern, path);
		try {

			logger.debug("route:{}", dir);

			Image image = routeService.getImageByPath(dir);

			String previous = request.getHeader(IF_NONE_MATCH);

			if (image != null) {
				String md5 = image.getCmd5();
				if (md5 != null && md5.equals(previous)) {
					// Not Modified
					response.setStatus(304);
					return;
				} else {
					ImageBo img = imgHelper.getImage(dir);
					if (img != null) {
						response.setHeader("Content-Type",
								"image/" + img.getType());
						response.setCharacterEncoding("UTF-8");
						if (md5 == null) {
							md5 = Hex.encodeHexString(MessageDigest
									.getInstance("MD5").digest(img.getData()));
						}
						response.setHeader(CACHE_CONTROL, "max-age=604800");
						response.setHeader(ETAG, md5);

						ServletOutputStream os = response.getOutputStream();
						os.write(img.getData());
						os.flush();
						return;
					}
				}
			}

		} catch (Exception e) {
			logger.error("get img error:{}", dir);
		}
		response.setStatus(404);
		return;
	}

	/**
	 * 获取压缩图片
	 */
	@RequestMapping(value = "/xy/{width}/{height}/**")
	@ResponseBody
	public void getReduce(@PathVariable("width") Integer width,
			@PathVariable("height") Integer height, HttpServletRequest request,
			HttpServletResponse response) {

		String path = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request
				.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		AntPathMatcher apm = new AntPathMatcher();
		String dir = apm.extractPathWithinPattern(bestMatchPattern, path);

		// 检查图片是否合法尺寸
		boolean isValid = this.isValidImg(width, height, dir);
		if (!isValid) {
			response.setStatus(404);
			return;
		}

		try {

			logger.debug("route:{}", dir);
			logger.debug("width:{}", width);
			logger.debug("height:{}", height);

			Image image = routeService.getImageByPath(dir, width, height);

			String previous = request.getHeader(IF_NONE_MATCH);

			if (image != null) {
				String md5 = image.getCmd5();
				if (md5 != null && md5.equals(previous)) {
					// Not Modified
					response.setStatus(304);
					return;
				}
			}
			ImageBo img = imgHelper.getImage(dir, width, height);
			if (img != null) {
				response.setHeader("Content-Type", "image/" + img.getType());
				response.setCharacterEncoding("UTF-8");

				String md5 = Hex.encodeHexString(MessageDigest.getInstance(
						"MD5").digest(img.getData()));

				response.setHeader(CACHE_CONTROL, "max-age=604800");
				response.setHeader(ETAG, md5);

				ServletOutputStream os = response.getOutputStream();
				os.write(img.getData());
				os.flush();
				return;
			}

		} catch (Exception e) {
			logger.error("get img error:{}", dir);
		}
		response.setStatus(404);
		return;
	}

	@RequestMapping(value = "v1/updateRoute", method = RequestMethod.POST)
	public void updateRoute(@RequestBody String json) {

		List<NameValuePair> nvp = URLEncodedUtils.parse(json,
				Charset.forName("UTF-8"));

		Map<String, String> paras = Maps.newLinkedHashMap();

		FluentIterable.from(nvp).forEach(e -> {
			paras.put(e.getName(), e.getValue());
		});
		logger.debug("{}", json);

		if (paras != null && paras.containsKey("path")) {
			String path = paras.get("path").toString();
			if (!StringUtils.isEmpty(path)) {
				String width = paras.get("width");
				Integer widthInt = null;
				if (width != null) {
					widthInt = (int) Double.parseDouble(width);
				}

				String height = paras.get("height");
				Integer heightInt = null;
				if (height != null) {
					heightInt = (int) Double.parseDouble(height);
				}

				String md5 = paras.get("md5");
				String type = paras.get("type");
				String route = paras.get("route");

				boolean existed;
				if (width == null || height == null) {
					existed = this.routeService.existed(path);
				} else {
					existed = this.routeService.existed(path, widthInt,
							heightInt);
				}

				if (existed) {
					this.routeService.update(path, route, type, widthInt,
							heightInt, md5);
				} else {
					this.routeService.insert(path, route, type, widthInt,
							heightInt, md5);
				}
			}
		}
	}

	private boolean isValidImg(int w, int h, String dir) {
		if (dir.startsWith("clip/")) {
			return this.setting.chicuuXyList.contains(w + "x" + h);
		} else {
			return this.setting.isValidImg(w, h);
		}
	}
}
