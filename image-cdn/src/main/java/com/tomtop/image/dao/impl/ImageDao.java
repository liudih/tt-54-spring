package com.tomtop.image.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.image.dao.IImageDao;
import com.tomtop.image.mappers.ImageCacheMapper;
import com.tomtop.image.mappers.ImageMapper;
import com.tomtop.image.models.dto.Image;

@Service()
public class ImageDao implements IImageDao {

	@Autowired
	ImageMapper mapper;

	@Autowired
	ImageCacheMapper cacheMapper;

	@Override
	public List<Image> getImageByLimit(Map<String, Object> paras) {
		if (paras == null) {
			return null;
		}

		return this.mapper.getImageByLimit(paras);
	}

	@Override
	public int getMaxId() {
		return this.mapper.getMaxId();
	}

	@Override
	public boolean updateFastdfsUrl(Map<String, Object> paras) {
		return this.mapper.updateFastdfsUrl(paras);
	}

	@Override
	public Image getImageWithContentByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}
		return this.mapper.getImageWithContentByPath(path);
	}

	@Override
	public Image getImageWithoutContentByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}
		return this.mapper.getImageWithoutContentByPath(path);
	}

	@Override
	public List<Image> getImageCacheByLimit(Map<String, Object> paras) {
		return this.cacheMapper.getImageByLimit(paras);
	}

	@Override
	public int getCacheMaxId() {
		return this.cacheMapper.getMaxId();
	}

	@Override
	public boolean updateCacheFastdfsUrl(Map<String, Object> paras) {
		return this.cacheMapper.updateFastdfsUrl(paras);
	}

	@Override
	public Image getImageWithoutContent(String path, Integer width,
			Integer height) {
		Image img = this.cacheMapper.getImageWithoutContentByPath(path, width,
				height);

		return img;
	}

	@Override
	public Image getImageWithContent(String path, Integer width, Integer height) {
		Image img = this.cacheMapper.getImageWithContentByPath(path, width,
				height);

		return img;
	}

}
