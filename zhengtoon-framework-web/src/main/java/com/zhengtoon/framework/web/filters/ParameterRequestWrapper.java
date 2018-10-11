package com.zhengtoon.framework.web.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * OPENAPI参数校验包装类
 * 将请求参数取出，重新设置保证后续controller接收参数正常
 */
@Slf4j
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

	//替换的值包括参数、url、uri
	private String requestParam;
	private String url;
	private String uri;

	public ParameterRequestWrapper(HttpServletRequest request, String requestParam) throws IOException {
		super(request);
		this.requestParam = requestParam;
		this.url = this.subString(request.getRequestURL().toString());
		this.uri = this.subString(request.getRequestURI());
	}

	/**
	 * 字符串截取,从url中去除appId和sign两个参数
	 *
	 * @param url http://ip:port/aaa/{appId}/{sign}?aa=bb
	 * @return http://ip:port/aaa?aa=bb
	 */
	private String subString(String url) {
		String[] content = url.split("\\?");
		String tail = "";
		if (content.length > 1) {
			tail = "?" + content[1];
		}
		return StringUtils.substringBeforeLast(
				StringUtils.substringBeforeLast(content[0], "/"), "/") + tail;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public String getRequestURI() {
		return uri;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer(url);
	}

	@Override
	public String getServletPath() {
		return uri;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(requestParam.getBytes());
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() {
				return bais.read();
			}
		};
	}
}
