package com.zhengtoon.framework.socket;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求的响应的接收方法
 * @author 601344
 * @since 2018/6/12
 * Copyright: Copyright (c) 2018
 * Company:北京政务通科技有限公司
 */
@Slf4j
public abstract class SocketHandler<T> extends SimpleChannelInboundHandler<T> {

	private Channel channel;

	/**
	 * 响应数据
	 */
	private T data;

	/**
	 * 向服务端发送数据
	 */
	public T sendMsg(T msg) throws Exception {
		//向服务端发送数据
		ChannelPromise channelPromise = channel.writeAndFlush(msg).channel().newPromise();
		channelPromise.await();
		return data;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
		this.data = msg ;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		String id = channel.id().asLongText();
		log.info("成功建立一个新的连接,连接的id:{}",id);
	}

}
