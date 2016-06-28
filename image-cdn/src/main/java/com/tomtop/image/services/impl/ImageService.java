package com.tomtop.image.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tomtop.image.dao.IImageDao;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IImageService;

@Service()
public class ImageService implements IImageService {

	@Autowired
	IImageDao dao;

	@Override
	public List<Image> getImageByLimit(int start, int end) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		return this.dao.getImageByLimit(paras);
	}

	@Override
	public int getMaxId() {
		return this.dao.getMaxId();
	}

	@Override
	public boolean updateFastdfsUrl(String url, int id) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("url", url);
		paras.put("id", id);
		return this.dao.updateFastdfsUrl(paras);
	}

	@Override
	public Image getImageWithContentByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}
		return this.dao.getImageWithContentByPath(path);
	}

	@Override
	public Image getImageWithoutContentByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}
		return this.dao.getImageWithoutContentByPath(path);
	}

	@Override
	public List<Image> getImageCacheByLimit(int start, int end) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		return this.dao.getImageCacheByLimit(paras);
	}

	@Override
	public int getCacheMaxId() {
		return this.dao.getCacheMaxId();
	}

	@Override
	public boolean updateCacheFastdfsUrl(String url, int id) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("url", url);
		paras.put("id", id);
		return this.dao.updateCacheFastdfsUrl(paras);
	}

	@Override
	public ImageBo getImageWithoutContent(String dir, Integer width,
			Integer height) {
		Image img = this.dao.getImageWithoutContent(dir, width, height);

		if (img != null) {
			ImageBo bo = new ImageBo(img.getBcontent(), img.getCcontenttype());
			return bo;
		}
		return null;
	}

	@Override
	public ImageBo getImageWithContent(String dir, Integer width, Integer height) {
		Image img = this.dao.getImageWithContent(dir, width, height);
		if (img != null) {
			ImageBo bo = new ImageBo(img.getBcontent(), img.getCcontenttype());
			return bo;
		}
		return null;
	}

}
