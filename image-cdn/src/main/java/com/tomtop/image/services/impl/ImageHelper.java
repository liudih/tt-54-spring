package com.tomtop.image.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;

/**
 * 
 * @author lijun
 *
 */
@Service
public class ImageHelper {
	private static Logger Logger = LoggerFactory.getLogger(ImageHelper.class);

	private static int ogX = 2000;
	private static int ogY = 2000;

	@Autowired
	IFileRouteService routeService;

	@Autowired
	FastdfsSettings setting;

	private Set<IImagePlugin> imagePlugin;

	@Autowired
	public ImageHelper(Set<IImagePlugin> ip) {
		this.imagePlugin = FluentIterable.from(ip).toSortedSet(
				new Comparator<IImagePlugin>() {

					@Override
					public int compare(IImagePlugin o1, IImagePlugin o2) {
						return o1.getPriority() - o2.getPriority();
					}

				});
	}

	public ImageBo getImage(String dir) {
		for (IImagePlugin s : imagePlugin) {
			ImageBo image = s.getImage(dir);
			if (image != null) {
				return image;
			}
		}
		return null;
	}

	/**
	 * 获取 width:height 大小图片
	 * 
	 * @param dir
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageBo getImage(String dir, Integer width, Integer height) {
		for (IImagePlugin s : imagePlugin) {
			ImageBo image = s.getImage(dir, width, height);
			if (image != null) {
				return image;
			}
		}
		return null;
	}

	public byte[] getFile(String dir) {
		for (IImagePlugin s : imagePlugin) {
			byte[] bytes = s.getFile(dir);
			if (bytes != null) {
				return bytes;
			}
		}
		return null;
	}

	public boolean saveProductOriginalImage(ImageBo img) {
		String path = img.getPath();
		String type = img.getType();
		if (StringUtils.isEmpty(path)) {
			Logger.error("img path is null");
			return false;
		}
		if (StringUtils.isEmpty(type)) {
			Logger.error("img type is null");
			return false;
		}

		type = type.toLowerCase();

		List<ImageBo> compressImgs = null;
		// 生成压缩图片
		if ("jpeg".equals(type) || "jpg".equals(type)) {
			compressImgs = compress(img);
		} else {
			compressImgs = Lists.newLinkedList();
		}
		compressImgs.add(img);

		return save(compressImgs);
	}

	public boolean save(List<ImageBo> imgs) {
		boolean succeed = true;
		for (IImagePlugin s : imagePlugin) {
			boolean feedback = s.save(imgs);
			if (!feedback) {
				succeed = false;
			}
		}

		return succeed;
	}

	/**
	 * 压缩原图的各种尺寸
	 * 
	 * @param original
	 *            原图
	 * @return
	 */
	public List<ImageBo> compress(ImageBo original) {
		String path = original.getPath();
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}

		List<ImageBo> feedback = Lists.newLinkedList();

		List<Image> xyImg = this.routeService.selectXy(path);
		if (xyImg == null) {
			xyImg = new ArrayList<Image>();
		}
		List<String> xys = Lists.transform(xyImg, xy -> xy.getIwidth() + "x"
				+ xy.getIheight());
		// 判断所有的尺寸图片是否都已经存在了
		List<String> filterXy = FluentIterable.from(this.setting.getXyList())
				.filter(xy -> !xys.contains(xy)).toList();

		String ogxy = ogX + "x" + ogY;
		boolean isOgxy = true;
		if (xys.contains(ogxy)) {
			isOgxy = false;
		}
		// 后缀名称
		String type = original.getType();
		if (filterXy.size() > 0 || isOgxy) {
			List<Integer[]> cxy = Lists.transform(filterXy, xy -> {
				String[] sa = xy.split("x");
				Integer x = Integer.parseInt(sa[0]);
				Integer y = Integer.parseInt(sa[1]);
				Integer[] ret = { x, y };
				return ret;
			});

			try {
				byte[] originalBytes = original.getData();

				if (isOgxy) {

					ByteArrayOutputStream ogOut = new ByteArrayOutputStream();

					Thumbnails.of(new ByteArrayInputStream(originalBytes))
							.outputQuality(0.9).useOriginalFormat().scale(1.0)
							.toOutputStream(ogOut);

					byte[] ogBytes = ogOut.toByteArray();

					ImageBo cImage = new ImageBo(ogBytes, type);
					cImage.setPath(path);
					cImage.setWidth(this.ogX);
					cImage.setHeight(this.ogY);

					feedback.add(cImage);
				}
				for (Integer[] xy : cxy) {
					Integer x = xy[0];
					Integer y = xy[1];
					if (x > 0 && y > 0) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();

						Thumbnails.of(new ByteArrayInputStream(originalBytes))
								.size(x, y).keepAspectRatio(true)
								.useOriginalFormat().toOutputStream(out);
						byte[] bytes = out.toByteArray();

						ImageBo cImage = new ImageBo(bytes, type);
						cImage.setPath(path);
						cImage.setWidth(x);
						cImage.setHeight(y);

						feedback.add(cImage);
					}
				}
			} catch (Exception e) {
				Logger.error("{}  error", path, e);
			}
		}
		return feedback;

	}
}
