package com.zhengtoon.framework.message.im.common;


import com.google.common.collect.Lists;
import com.zhengtoon.framework.message.im.bean.ContentObject;
import com.zhengtoon.framework.message.im.bean.IMEntity;
import com.zhengtoon.framework.message.im.bean.enums.MsgTypeEnum;
import com.zhengtoon.framework.message.im.bean.NoticeSimpleParam;
import org.apache.commons.lang3.StringUtils;

/**
 * IMEntity对象创建工厂类
 *
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
public class IMEntityFactory {

	/**
	 *
	 * 创建通知消息对象的最简参数方法，用于简化创建消息对象的过程
	 *
	 * @param noticeSimpleParam 简单消息通知参数
	 * @return 消息对象
	 */
	public static IMEntity createNoticeEntity(NoticeSimpleParam noticeSimpleParam) {
		IMEntity imEntity = new IMEntity();
		if(noticeSimpleParam.getMsgID() != null){
			imEntity.setMsgid(noticeSimpleParam.getMsgID().toString());
		}
		if(noticeSimpleParam.getBizNo() != null){
			imEntity.setMsgid(noticeSimpleParam.getBizNo().toString());
		}
		imEntity.setMsgType(MsgTypeEnum.SINGLENOTICE);
		imEntity.setToClient(noticeSimpleParam.getToClient());
		imEntity.setPushInfo(noticeSimpleParam.getPushInfo());
		imEntity.setTo(noticeSimpleParam.getToClient());

		IMEntity.Content imEntityContent = imEntity.getContent();
		imEntityContent.setSubCatalog(noticeSimpleParam.getTitle());

		//消息对象内容
		ContentObject contentObject = new ContentObject();

		ContentObject.ContentDetail detail = new ContentObject.ContentDetail();
		detail.setContent(noticeSimpleParam.getContent());
		contentObject.setContentDetail(Lists.newArrayList(detail));
		//图片
		String imgUrl = noticeSimpleParam.getImgUrl();
		if(StringUtils.isNotBlank(imgUrl)){
			ContentObject.ImageDetail imageDetail = new ContentObject.ImageDetail();
			imageDetail.setIconUrl(imgUrl);
			imageDetail.setUrl(noticeSimpleParam.getImgRedictUrl());
			imageDetail.setDefaultUrl(noticeSimpleParam.getImgDefaulttUrl());
			contentObject.setImageDetail(Lists.newArrayList(imageDetail));
		}
		//按钮
		String btnContent = noticeSimpleParam.getBtnContent();
		if(StringUtils.isNotBlank(btnContent)){
			ContentObject.HandleButton handleButton = new ContentObject.HandleButton();
			handleButton.setContent(btnContent);
			handleButton.setUrl(noticeSimpleParam.getBtnUrl());
			handleButton.setDefaultUrl(noticeSimpleParam.getBtnDefaultUrl());
			contentObject.setHandleButton(Lists.newArrayList(handleButton));
		}
		imEntityContent.setContent(contentObject);
		return imEntity;
	}
}
