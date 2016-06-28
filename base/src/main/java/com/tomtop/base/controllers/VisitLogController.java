package com.tomtop.base.controllers;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tomtop.base.models.dto.VisitLogDto;
import com.tomtop.base.models.vo.VisitLogVo;
import com.tomtop.base.service.IVisitLogService;
import com.tomtop.framework.core.utils.Result;

/**
 * 访问记录接口
 * 
 * @author liulj
 *
 */
@RestController
public class VisitLogController {

	@Autowired
	private IVisitLogService service;

	@RequestMapping(method = RequestMethod.POST, value = "/base/visitLog/v1")
	public Result addRecord(@RequestBody String body) {
		VisitLogVo vo = JSON.parseObject(body, VisitLogVo.class);
		if (vo != null) {
			String aid = null;
			String url = null;
			if (vo.getUrl() != null) {
				MultiMap<String> values = new MultiMap<String>();
				UrlEncoded.decodeTo(vo.getUrl().getQuery(), values, "UTF-8",
						1000);
				aid = values.getString("aid");
				url = vo.getUrl().toString();
			}
			int row = service.insert(new VisitLogDto(aid, vo.getClient(), vo
					.getIp(), url));
			if (row > 0) {
				return new Result(Result.SUCCESS, null);
			}
		}
		return new Result(Result.FAIL, null);
	}
}
