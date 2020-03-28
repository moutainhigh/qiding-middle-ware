package com.qiding.state.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class StateServer {
	public int port;


	public StateServer(int port) {
		this.port = port;
	}

	public void start() throws InterruptedException {
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup workers=new NioEventLoopGroup();

		ServerBootstrap bootstrap=new ServerBootstrap();

		bootstrap.group(boss,workers);
		bootstrap.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,128)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addFirst(new ServerHandler());
				}
			})
			.childOption(ChannelOption.)
			.childOption(ChannelOption.SO_KEEPALIVE,true);

		ChannelFuture future=bootstrap.bind(port).sync();
		future.channel().closeFuture().sync();
	}

	public static void main(String[] args) throws InterruptedException {
		StateServer server=new StateServer(8099);
		server.start();
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			System.out.println("hello world");
		}));
	}
}
