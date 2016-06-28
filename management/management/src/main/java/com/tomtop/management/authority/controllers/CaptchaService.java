package com.tomtop.management.authority.controllers;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.napp.kaptcha.Kaptcha;
import com.github.napp.kaptcha.KaptchaConfig;
import com.github.napp.kaptcha.KaptchaTextCreator;

/**
 * 
    * @ClassName: CaptchaService
    * @Description: TODO验证码服务类)
    * @author Guozy
    * @date 2016年1月29日
    *
 */
@Controller
public class CaptchaService{

	@RequestMapping(value = "/public/captchaManage", method = RequestMethod.GET)
	@ResponseBody
	public void createCaptcha( HttpSession session,OutputStream out) throws IOException {
		KaptchaConfig config = new KaptchaConfig();
		config.setHeight(30);
		config.setHeight(75);
		Kaptcha kaptcha = new Kaptcha(config);
		String v = KaptchaTextCreator.getText(4);
		session.setAttribute("v",v);
		ImageIO.write(kaptcha.createImage(v), "png", out);
	}

}
