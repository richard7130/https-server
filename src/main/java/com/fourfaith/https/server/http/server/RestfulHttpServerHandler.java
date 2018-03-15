/** 
 * Project Name:restful-server 
 * File Name:RestfulHttpServerHandler.java 
 * Package Name:com.fourfaith.restful.server.http.server 
 * Date:2016年3月26日上午9:13:45 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http.server;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fourfaith.https.server.comm.RestfulConstant;
import com.fourfaith.https.server.doc.RestfulDocGenerator;
import com.fourfaith.https.server.http.RestfulServiceHandler;

/**
 * ClassName: RestfulHttpServerHandler <br/>
 * Function: Restful http server handler. <br/>
 * date: 2016年3月26日 上午9:13:45 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements Serializable {

	private static final long serialVersionUID = 1712641460843516921L;
	private static final Logger logger = Logger.getLogger(RestfulHttpServerHandler.class);

	/**
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	/**
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext,
	 *      java.lang.Object)
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

		if (HttpUtil.is100ContinueExpected(request)) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}
		boolean keepAlive = HttpUtil.isKeepAlive(request);

		if (StringUtils.endsWithAny(request.uri(), RestfulConstant.SKIP_URI_1, RestfulConstant.SKIP_URI_2)) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE)).addListener(
					ChannelFutureListener.CLOSE);
			return;
		}

		FullHttpResponse response = null;

		// 生成restful api 文档
		if (StringUtils.equals(request.uri(), RestfulConstant.DOC_URI_1)
				|| StringUtils.equals(request.uri(), RestfulConstant.DOC_URI_2)) {

			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, RestfulDocGenerator.generate());
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
		} else {
			String jsonResult = null;
			if (StringUtils.equals(request.uri(), RestfulConstant.DOC_JSON)) {
				jsonResult = RestfulDocGenerator.getDocItemJSON();
			} else {
				jsonResult = RestfulServiceHandler.invoke(request);
			}

			logger.debug("JSON Result: " + jsonResult);
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK, Unpooled.copiedBuffer(jsonResult.getBytes("utf-8")));
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
		}

		response.headers().set(HttpHeaderNames.ACCEPT_CHARSET, "utf-8");
		response.headers().set(HttpHeaderNames.ACCEPT_ENCODING, "uft-8");
		response.headers().set(HttpHeaderNames.CONTENT_ENCODING, "utf-8");
		response.headers().set(HttpHeaderNames.SERVER, "HTTPS RESTFUL SERVER 1.0.0");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.headers().set(HttpHeaderNames.DATE, new Date());
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode("key1", "value1"));
		response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode("key2", "value2"));

		if (!keepAlive) {
			ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		} else {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
			ctx.write(response);
		}
	}

	/**
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext,
	 *      java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		ctx.close();
	}

}
