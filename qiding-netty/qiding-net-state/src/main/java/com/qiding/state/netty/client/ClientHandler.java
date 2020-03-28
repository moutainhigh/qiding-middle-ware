package com.qiding.state.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.fireChannelRead(msg);
//		System.out.println(new String(ByteBufUtil.getBytes((ByteBuf)msg)));
//		System.out.println("开始读取数据");
//		TimeUnit.SECONDS.sleep(20);
//		ctx.writeAndFlush(msg);
//		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf byteBuf = ctx.alloc().buffer();
		byteBuf.writeBytes("qiding hello".getBytes());
		ctx.writeAndFlush(byteBuf);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel  lose connect success");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
