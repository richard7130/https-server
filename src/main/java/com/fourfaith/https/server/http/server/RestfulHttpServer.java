/** 
 * Project Name:restful-server 
 * File Name:RestfulHttpServer.java 
 * Package Name:com.fourfaith.restful.server.http.server 
 * Date:2016年3月26日上午8:52:09 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.apache.log4j.Logger;

/**
 * ClassName: RestfulHttpServer <br/>
 * Function: Restful http 服务端. <br/>
 * date: 2017年1月26日 上午8:52:09 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public final class RestfulHttpServer implements Serializable {

	private static final long serialVersionUID = 3034789762089774175L;
	private static final Logger logger = Logger.getLogger(RestfulHttpServer.class);
	private int httpServerPort;// http端口
	private int httpServerThreadCount;// http线程数
	private String sslUrl;// 证书路径
	private String sslPass;// 证书秘钥

	/**
	 * @param http通信端口
	 * @param http线程数
	 * @param 证书路径
	 * @param 证书秘钥
	 */
	public RestfulHttpServer(int _httpServerPort, int _httpServerThreadCount, String _sslUrl, String _sslPass) {
		this.httpServerPort = _httpServerPort;
		this.httpServerThreadCount = _httpServerThreadCount;
		this.sslUrl = _sslUrl;
		this.sslPass = _sslPass;
	}

	public void start() throws Exception {

		final SSLContext sslContext = getSslContext(this.sslUrl, this.sslPass);
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup(this.httpServerThreadCount);
        
		try {

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ChannelPipeline p = sc.pipeline();
					if (sslContext != null) {
						SSLEngine sslEngine = sslContext.createSSLEngine();
						sslEngine.setUseClientMode(false); // 服务器端模式
						sslEngine.setNeedClientAuth(false); // 不需要验证客户端
						p.addLast("ssl", new SslHandler(sslEngine));
					}
					p.addLast("codec", new HttpServerCodec());
					p.addLast("http-aggregator", new HttpObjectAggregator(65536));
					p.addLast("handler", new RestfulHttpServerHandler());
				}
			});

			logger.info("Http server binding port [" + httpServerPort + "].");
			Channel ch = b.bind(httpServerPort).sync().channel();
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();

		}
	}

	protected SSLContext getSslContext(String _sslUrl, String _sslPass) throws Exception {
		KeyStore ks = KeyStore.getInstance("JKS");
		InputStream ksInputStream = new FileInputStream(sslUrl);
		ks.load(ksInputStream, sslPass.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, sslPass.toCharArray());
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), null, null);
		return sslContext;
	}
}
