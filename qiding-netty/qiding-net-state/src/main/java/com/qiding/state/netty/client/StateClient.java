package com.qiding.state.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class StateClient {
	private String address;
	private int port;

	public StateClient(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void start(){
		EventLoopGroup work=new NioEventLoopGroup(1);
		Bootstrap bootstrap=new Bootstrap();
		bootstrap.group(work)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE,true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addFirst(new ClientHandler());
				}
			});
         bootstrap.connect(new InetSocketAddress(address,port)).channel();
	}

	public static void main(String[] args) {
		StateClient client=new StateClient("127.0.0.1",8099);
		client.start();
	}




}
