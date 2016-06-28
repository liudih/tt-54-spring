/**
 * 
 */
package com.tomtop.image.thread;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.util.Maps;
import com.google.common.collect.FluentIterable;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.services.IProductImageService;
import com.tomtop.image.services.impl.ImageHelper;

/**
 * 图片抓取线程
 * 
 * @author lijun
 */
public class FetchThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(FetchThread.class);

	private static Map<String, String> prefix = Maps.newHashMap();

	static {
		prefix.put("http://www.guphotos.com/images", "p/gu1");
		prefix.put("http://www.guphotos.com/listingImages", "p/gu2");
		prefix.put("http://www.tomtop.com/media/catalog/product", "p/tt");
	}

	@Autowired
	IProductImageService service;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	HttpRequestFactory httpRequestFactory;

	@Autowired
	ImageHelper imgHelper;

	// 抓取开始页
	private int start;
	private int end;

	TrackerServer tracker = null;

	public FetchThread(int start, int end) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {

		logger.debug(Thread.currentThread().getName() + " start:" + start
				+ " end:" + end);
		int next = start;
		
		boolean isLast = false;
		
		while (true) {
			List<ProductImage> pImg = service.get(next, end);

			if (pImg == null || pImg.size() == 0) {
				break;
			}
			int lastId = pImg.get(pImg.size() - 1).getIid();
			if (next == lastId) {
				if(isLast){
					break;
				}else{
					isLast = true;
				}
				
			}
			next = lastId;

			FluentIterable
					.from(pImg)
					.forEach(
							i -> {
								String imgUrl = i.getCoriginalurl();
								if (StringUtils.isEmpty(imgUrl)) {
									imgUrl = i.getCimageurl();
								}
								if (StringUtils.isNotEmpty(imgUrl)
										&& imgUrl.trim().startsWith("http://")) {
									try {
										GenericUrl url = new GenericUrl(imgUrl);
										HttpRequest request = httpRequestFactory
												.buildGetRequest(url);
										HttpResponse response = null;
										try {
											response = request.execute();
										} catch (HttpResponseException e) {
											int code = e.getStatusCode();

											service.changeHttpStatusCodeById(
													i.getIid(), code);
											return;

										}

										String type = response.getContentType();

										String extName = type.substring(type
												.indexOf("/") + 1);

										ByteArrayOutputStream baos = new ByteArrayOutputStream();
										response.download(baos);

										byte[] img = baos.toByteArray();

										boolean isFix = false;
										String relativeUrl = i.getCimageurl();

										if (relativeUrl.startsWith("http")) {
											Iterator<String> iterator = prefix
													.keySet().iterator();

											while (iterator.hasNext()) {
												String key = iterator.next();
												int index = relativeUrl
														.indexOf(key);
												if (index != -1) {
													relativeUrl = prefix
															.get(key)
															+ relativeUrl
																	.substring(key
																			.length());
													isFix = true;
													break;
												}
											}

											if (!isFix) {
												int index = relativeUrl
														.indexOf("/", 7);
												relativeUrl = relativeUrl
														.substring(index + 1);
												isFix = true;
											}
										}

										if (relativeUrl.startsWith("/")) {
											relativeUrl = relativeUrl
													.substring(1);
										}
										ImageBo image = new ImageBo(img,
												extName);

										image.setPath(relativeUrl);

										boolean succeed = this.imgHelper
												.saveProductOriginalImage(image);
										if (succeed) {
											if (isFix) {
												service.changeFetchToTrueById(
														i.getIid(), relativeUrl);
											} else {
												service.changeFetchToTrueById(i
														.getIid());
											}
										}

									} catch (Exception e) {
										logger.error(
												"fetch image error for iid:{}",
												i.getIid(), e);
									}
								}
							});

		}

		logger.debug(Thread.currentThread().getName() + " end");

	}

}
