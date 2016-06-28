package com.tomtop.image.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tomtop.image.dao.IFileRouteDao;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.Range;
import com.tomtop.image.services.IFileRouteService;

/**
 * 
 * @author lijun
 *
 */
@Service
public class FileRouteService implements IFileRouteService {

	@Autowired
	IFileRouteDao dao;

	@Override
	public boolean insert(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("cpath", cpath);
		paras.put("croute", croute);
		paras.put("ctype", ctype);
		paras.put("iwidth", iwidth);
		paras.put("iheight", iheight);
		paras.put("cmd5", cmd5);
		return this.dao.insert(paras);
	}

	@Override
	public Image getImageByPath(String path) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		List<Image> result = this.dao.select(paras);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public Image getImageByPath(String path, Integer width, Integer height) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		paras.put("width", width);
		paras.put("height", height);

		List<Image> result = this.dao.select(paras);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	@Override
	public boolean update(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("cpath", cpath);
		paras.put("croute", croute);
		paras.put("ctype", ctype);
		paras.put("iwidth", iwidth);
		paras.put("iheight", iheight);
		paras.put("cmd5", cmd5);

		return this.dao.update(paras);
	}

	@Override
	public boolean existed(String cpath, Integer iwidth, Integer iheight) {

		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", cpath);
		paras.put("width", iwidth);
		paras.put("height", iheight);

		List<Image> result = this.dao.select(paras);
		if (result != null && result.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean existed(String cpath) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", cpath);

		List<Image> result = this.dao.select(paras);
		if (result != null && result.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public List<Image> selectXy(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		return this.dao.selectXy(paras);
	}

	@Override
	public int getOriginalMaxId() {
		return this.dao.getOriginalMaxId();
	}

	@Override
	public List<Image> getOriginalImageByLimit(int start, int end) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		paras.put("compress", true);
		return this.dao.select(paras);
	}

	@Override
	public boolean isExisted(String path) {
		return false;
	}

	@Override
	public boolean isExisted(String path, Integer iwidth, Integer iheight) {

		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		paras.put("width", iwidth);
		paras.put("height", iheight);

		int count = this.dao.count(paras);

		return count > 0 ? true : false;
	}

	@Override
	public Image selectRoute(String path) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		List<Image> result = this.dao.selectRoute(paras);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public Image selectRoute(String path, Integer width, Integer height) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("path", path);
		paras.put("width", width);
		paras.put("height", height);

		List<Image> result = this.dao.selectRoute(paras);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	@Override
	public boolean updateById(int id, String croute, String ctype, String cmd5) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("croute", croute);
		paras.put("ctype", ctype);
		paras.put("cmd5", cmd5);

		return this.dao.update(paras);
	}

	@Override
	public boolean insert(String cpath, String croute, String ctype,
			Integer iwidth, Integer iheight, String cmd5, boolean isCompressed) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("cpath", cpath);
		paras.put("croute", croute);
		paras.put("ctype", ctype);
		paras.put("iwidth", iwidth);
		paras.put("iheight", iheight);
		paras.put("cmd5", cmd5);
		paras.put("bcompress", isCompressed);
		return this.dao.insert(paras);
	}

	@Override
	public boolean changeCompressToTrueById(int id) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("bcompress", true);

		return this.dao.update(paras);
	}

	@Override
	public List<Image> getImage(int start, int end, int width, int height,
			boolean modified) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		paras.put("width", width);
		paras.put("height", height);
		paras.put("modified", modified);
		return this.dao.select(paras);
	}

	@Override
	public boolean updateById(int id, String croute, String cmd5) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("croute", croute);
		paras.put("modify", true);
		paras.put("cmd5", cmd5);

		return this.dao.update(paras);
	}

	@Override
	public List<Image> getImagesByLimit(int start, int end) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		paras.put("all", true);
		return this.dao.select(paras);
	}

	@Override
	public Range getMaxMinNoAmazon() {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("amazon", "0");
		return this.dao.getMaxMin(paras);
	}

	@Override
	public boolean changeBamazonToTrueById(List<Integer> ids) {
		return this.dao.updateBamazon(ids);
	}

	@Override
	public boolean changeBamazonToTrueById(int id) {
		return this.dao.updateBamazon(id);
	}

}
