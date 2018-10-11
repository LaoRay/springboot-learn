package com.zhengtoon.framework.jwt.security;

import com.alibaba.fastjson.JSON;
import com.zhengtoon.framework.jwt.bean.JwtResult;
import com.zhengtoon.framework.jwt.bean.dto.UserInfo;
import com.zhengtoon.framework.jwt.config.JwtProperteis;
import com.zhengtoon.framework.jwt.constant.JwtConstant;
import com.zhengtoon.framework.utils.codec.DesUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TokenHelper {

	private JwtProperteis jwtProperteis;

	public TokenHelper(JwtProperteis jwtProperteis) {
		this.jwtProperteis = jwtProperteis;
	}

	public JwtProperteis getJwtProperteis() {
		return jwtProperteis;
	}

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public JwtResult generateToken(String userInfo) {
		return this.generateToken(JwtConstant.USERINFO_KEY, userInfo);
	}

	public JwtResult generateToken(String key, String value) {
		String encryptedValue = DesUtils.encode(value, jwtProperteis.getSecret());
		String token = Jwts.builder().claim(key, encryptedValue)
				.setIssuer(jwtProperteis.getName())
				.setAudience("")
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				.signWith(SIGNATURE_ALGORITHM, jwtProperteis.getSecret())
				.compact();
		return new JwtResult(token, JwtConstant.BEARER_KEY, jwtProperteis.getExpires());
	}

	public UserInfo getUserInfo(HttpServletRequest request) {
		return JSON.parseObject(this.getValue(JwtConstant.USERINFO_KEY, request), UserInfo.class);
	}

	public String getValue(String key, HttpServletRequest request) {
		Claims claims = this.getClaimsFromToken(this.getToken(request));
		if (claims != null) {
			return DesUtils.decode(claims.get(key, String.class), jwtProperteis.getSecret());
		}
		return null;
	}

	private Date generateExpirationDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, jwtProperteis.getExpires());
		return cal.getTime();
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(jwtProperteis.getSecret())
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith(JwtConstant.BEARER_KEY)) {
			String token = authHeader.substring(7);
			log.debug("Authorization token:{}", token);
			return token;
		}
		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(JwtConstant.AUTHORIZATION_KEY);
	}

}