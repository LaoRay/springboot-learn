package com.zhengtoon.framework.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 601344
 * @since 2018/6/12
 * Copyright: Copyright (c) 2018
 * Company:北京政务通科技有限公司
 */
@Slf4j
public class ServerRunner implements ApplicationRunner {

	private ExecutorService executors = Executors.newSingleThreadExecutor();

	@Autowired
	private SocketHandler socketHandler;

	@Autowired
	private SocketProperties socketProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		executors.submit(new Runnable() {
			@Override
			public void run() {
				startServer();
			}
		});
	}

	/**
	 * 启动socket客户端
	 */
	private void startServer(){
		EventLoopGroup group = new NioEventLoopGroup(socketProperties.getThread());
		Bootstrap bootstrap = new Bootstrap()
				.group(group)
				.channel(NioSocketChannel.class)
				//降低延迟
				.option(ChannelOption.TCP_NODELAY, true)
				//超时
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, socketProperties.getConnectTimeOut())
				.handler(socketHandler);
		try {
			final String host = socketProperties.getHost();
			final Integer port = socketProperties.getPort();
			bootstrap.connect(host,port)
					.addListener(new ChannelFutureListener(){
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							String formatter = String.format("[ip:%s,port:%s]",host,port);
							if(future.isSuccess()){
								log.info("socket服务连接成功 {}",formatter);
							}else{
								log.info("socket服务连接异常 {}",formatter);
							}
						}
					})
					.sync()
					.channel()
					.closeFuture()
					.sync();
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}finally {
			group.shutdownGracefully();
		}
	}
}
