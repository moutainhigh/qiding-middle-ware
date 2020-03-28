package com.qiding.state.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			ByteBuf in = (ByteBuf) msg;
			TimeUnit.SECONDS.sleep(10);
			in.markReaderIndex();
			ByteBuf out = ctx.alloc().buffer();
			out.writeBytes(ByteBufUtil.getBytes(in));
			ctx.writeAndFlush(out);
			ctx.close();
		} finally {
			ReferenceCountUtil.release(msg);
		}
//		try {
//		    ByteBuf byteBuf= ctx.alloc().buffer(1024);
//			byteBuf.readBytes((ByteBuf)msg);
//			ctx.writeAndFlush(msg);
//		}finally {
//			ReferenceCountUtil.release(msg);
//		}
	}

}
