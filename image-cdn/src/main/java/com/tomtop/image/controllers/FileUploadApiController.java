package com.tomtop.image.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.common.collect.Maps;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.services.IImagePlugin;
import com.tomtop.image.services.IProductImageService;
import com.tomtop.image.services.impl.ImageHelper;

/**
 * 文件上传服务
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping(value = "file", method = RequestMethod.POST)
public class FileUploadApiController {

	private static Logger logger = LoggerFactory
			.getLogger(FileUploadApiController.class);
	private static final String DEPLOY_PRODUCT = "product";
	private static final String DEPLOY_UAT = "uat";

	private static boolean BATCH_START = false;

	@Autowired
	@Qualifier("fastdfs")
	IImagePlugin imgPlugin;

	@Autowired
	ImageHelper imgHelper;

	@Value("${domain.product}")
	String domainProduct;

	@Value("${domain.uat}")
	String domainUat;

	@Value("${deploy}")
	String deploy;

	@Value("${localhost}")
	String localhost;

	@Value("${secure.token}")
	String secureToken;

	@Autowired
	IProductImageService service;

	@Autowired
	HttpRequestFactory httpRequestFactory;

	@Value("${batchHttpRequestProductDomain}")
	String domain;
	
	@RequestMapping(value = "/upload")
	public Map<String, Object> upload(@RequestParam String token,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> feedback = Maps.newLinkedHashMap();

		if (!secureToken.equals(token)) {
			// bad request
			feedback.put("succeed", false);
			feedback.put("error", "your token invalid");
			return feedback;
		}

		if (file != null && !file.isEmpty()) {
			try {
				String domain = null;
				if (DEPLOY_PRODUCT.equals(deploy)) {
					domain = domainProduct;
				} else if (DEPLOY_UAT.equals(deploy)) {
					domain = domainUat;
				} else {
					domain = localhost;
				}
				if (!domain.endsWith("/")) {
					domain = domain + "/";
				}

				String name = file.getOriginalFilename();
				// 获取文件后缀名
				int index = name.lastIndexOf(".");
				String type = name.substring(index + 1);
				byte[] bytes = file.getBytes();
				ImageBo img = new ImageBo(bytes, type);
				String route = imgPlugin.save(img);
				route = domain + "file/fast/" + route;
				feedback.put("succeed", true);
				feedback.put("path", route);
				return feedback;
			} catch (Exception e) {
				logger.error("upload file error", e);
			}
		}
		feedback.put("succeed", false);
		return feedback;
	}

	@RequestMapping(value = "/fast/**", method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request,
			HttpServletResponse response) {

		// if(!secureToken.equals(token)){
		// //bad request
		// response.setStatus(400);
		// return;
		// }

		String previous = request.getHeader(ImageApiController.IF_NONE_MATCH);

		if (StringUtils.isNotEmpty(previous)) {
			// Not Modified
			response.setStatus(304);
			return;
		}

		String path = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request
				.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		AntPathMatcher apm = new AntPathMatcher();
		String dir = apm.extractPathWithinPattern(bestMatchPattern, path);

		try {
			byte[] bytes = imgHelper.getFile(dir);
			if (bytes != null) {
				response.setHeader(ImageApiController.CACHE_CONTROL,
						"max-age=604800");
				response.setHeader(ImageApiController.ETAG, UUID.randomUUID()
						.toString());
				ServletOutputStream os = response.getOutputStream();
				os.write(bytes);
				os.flush();
			} else {
				response.setStatus(404);
			}
			return;
		} catch (Exception e) {
			logger.debug("download file:{} error", dir, e);
			response.setStatus(404);
			return;
		}

	}

	@RequestMapping(value = "/batchHttpRequestProduct", method = RequestMethod.GET)
	public Map<String, Object> batchHttpRequestProduct(
			@RequestParam("q") boolean q, @RequestParam("size") Integer size) {
		Map<String, Object> feedback = Maps.newLinkedHashMap();

		if (q) {
			feedback.put("succeed", !BATCH_START);
			return feedback;
		}
		boolean canStart = canBatch();

		if (!canStart) {
			feedback.put("succeed", false);
			feedback.put("error", "this job has started");
			return feedback;
		}
		List<ProductUrl> urls = service.getProductUrl(0, size);

		if (urls == null) {
			feedback.put("succeed", true);
			return feedback;
		}

		while (true) {
			if (urls == null || urls.size() == 0) {
				break;
			}
			urls.forEach(url -> {
				try {
					String urlStr = url.getCurl();

					if (StringUtils.isEmpty(urlStr)) {
						logger.debug("error:{}", url.getIid());
						return;
					}

					GenericUrl gUrl = new GenericUrl(domain + urlStr + ".html");
					HttpRequest request = httpRequestFactory
							.buildGetRequest(gUrl);

					request.execute();
					logger.debug("ok:{}", url.getIid());
				} catch (Exception e) {
					logger.error("error:{}", url.getIid());
					return;

				}
			});

			urls = service.getProductUrl(urls.get(urls.size() - 1).getIid(),
					size);
		}

		feedback.put("succeed", true);
		return feedback;
	}

	private static synchronized boolean canBatch() {
		if (BATCH_START) {
			return false;
		}

		BATCH_START = true;
		return true;
	}
}
