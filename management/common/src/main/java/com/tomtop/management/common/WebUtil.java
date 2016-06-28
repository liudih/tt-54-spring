package com.tomtop.management.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import freemarker.core.ParseException;

/**
 * 
 * @ClassName: WebUtil
 * @Description: (网络服务操作)
 * @author gzy
 * @date 2015年12月23日
 *
 */
public class WebUtil {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(WebUtil.class);

	public static String executeGet(String url) throws Exception {

		BufferedReader in = null;
		String content = null;
		CloseableHttpClient client = null;
		try {
			url = url.trim();
			// 定义HttpClient
			// 实例化HTTP方法
			client = HttpClients.createDefault();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			// String s = new RestTemplate().getForEntity(url, String.class)
			// .getBody();
			in.close();
			content = sb.toString();
			client.close();
		} catch (Exception ex) {
			log.error("get url error: ss", ex);
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != client) {
				client.close();
			}
			return content;
		}
	}

	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		try {
			httpclient.close();
		} catch (Exception e) {
			log.error("------错误---", e);
		}

		return body;
	}

	private static String invoke(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
		log.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

}
