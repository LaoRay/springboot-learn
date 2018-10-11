package com.zhengtoon.framework.message.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.SDKExceptionCodes;
import com.zhengtoon.framework.message.im.bean.IMEntity;
import com.zhengtoon.framework.message.im.bean.IMResult;
import com.zhengtoon.framework.message.im.config.ImProperties;
import com.zhengtoon.framework.message.im.service.ToonIMService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.UUID;

/**
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Slf4j
public class ToonIMServiceImpl implements ToonIMService {

	private ImProperties imProperties;

	private OkHttpClient okHttpClient;

	public ToonIMServiceImpl(OkHttpClient okHttpClient , ImProperties imProperties) {
		this.okHttpClient = okHttpClient;
		this.imProperties = imProperties;
	}

	@Override
	public IMResult sendMessage(IMEntity imEntity) {
		this.handerDefaultValue(imEntity);
		this.verifyMessageParam(imEntity);
		Request.Builder builder = new Request.Builder()
				.url(imProperties.getServerUrl())
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), createSubmitParam(imEntity)));
		try {
			Response response = okHttpClient.newCall(builder.build()).execute();
			if (response.isSuccessful()) {
				String responseStr = response.body().string();
				log.debug("IM消息响应:{}",responseStr);
				JSONObject jsonObject = JSON.parseObject(responseStr);
				Integer status = jsonObject.getInteger("status");
				if(status == 0){
					//发送成功
					return jsonObject.getJSONObject("result").getJSONObject("msgAck").toJavaObject(IMResult.class);
				}
				String message = jsonObject.getString("message");
				throw new RuntimeException(message);
			}
		} catch (Exception e) {
			log.error("发送IM消息失败",e);
			throw new BusinessException(SDKExceptionCodes.SEND_IM_MESSAGE_FAIL);
		}
		return null;
	}

	/**
	 * 处理默认值
	 * @param imEntity 消息对象
	 */
	private void handerDefaultValue(IMEntity imEntity) {
		if(StringUtils.isBlank(imEntity.getMsgid())){
			imEntity.setMsgid(UUID.randomUUID().toString());
		}
		if(StringUtils.isBlank(imEntity.getContent().getBizNo())){
			imEntity.getContent().setBizNo(UUID.randomUUID().toString());
		}
		if(StringUtils.isBlank(imEntity.getFrom())){
			imEntity.setFrom(imProperties.getAppId());
		}
		imEntity.getContent().setCatalogId(imProperties.getCatalogId());
	}


	/**
	 * 生成提交参数
	 * @param imEntity 消息对象
	 * @return 参数
	 */
	private String createSubmitParam(IMEntity imEntity){
		imEntity.setTo_toon_type(imProperties.getToonType());
		imEntity.setAppid(imProperties.getAppId());
		String param = JSON.toJSONString(imEntity);
		log.debug("IM发送消息地址:{},请求参数:{}",imProperties.getServerUrl(),param);
		Map map = JSON.parseObject(param, Map.class);
		return Joiner.on("&").withKeyValueSeparator("=").join(map);
	}

	/**
	 * 参数校验
	 */
	private void verifyMessageParam(IMEntity imEntity){
		Assert.notNull(imEntity,"IM消息对象不能为null");
		Assert.hasLength(imProperties.getServerUrl(),"IM服务地址不能为空");
		Assert.hasLength(imProperties.getAppId(),"IM appId不能为空");
		Assert.hasLength(imEntity.getFrom(),"IM from不能为空");
		Assert.hasLength(imEntity.getTo(),"IM to不能为空");
		Assert.hasLength(imEntity.getToClient(),"IM toClient不能为空");
		Assert.notNull(imEntity.getMsgType(),"IM msgType不能为空");
//		IMEntity.Content content = imEntity.getContent();
//		Assert.notNull(content.getCatalogId(),"IM CatalogId不能为空");
	}


}
