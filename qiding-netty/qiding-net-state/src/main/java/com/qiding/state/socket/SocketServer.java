package com.qiding.state.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SocketServer {
	private int port;

	public SocketServer(int port) {
		this.port = port;
	}

	public void start() throws IOException, InterruptedException {
		ServerSocket serverSocket=new ServerSocket();
		//监听端口
		serverSocket.bind(new InetSocketAddress(port));

		while(true){
			//接受socket
			Socket chatSocket=serverSocket.accept();
			//使用socket通信
			chatSocket.setKeepAlive(true);
			//等待时间
            TimeUnit.SECONDS.sleep(5);

			CountDownLatch countDownLatch=new CountDownLatch(1);

			InputStream inputStream= chatSocket.getInputStream();
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
					while (inputStream.available()!=0){
						outputStream.write(inputStream.read());
					}
					System.out.println(new String(outputStream.toByteArray()));


//					countDownLatch.await();

//            new Thread(()->{
//				try {
//					InputStream inputStream= chatSocket.getInputStream();
//					countDownLatch.await();
//					ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//					while (inputStream.available()!=0){
//						outputStream.write(inputStream.read());
//					}
//					System.out.println(new String(outputStream.toByteArray()));
//					outputStream.reset();
//					countDownLatch.countDown();
//				} catch (IOException | InterruptedException e) {
//					e.printStackTrace();
//				}
//			},"readThead").start();
            //关闭socket

			//countDownLatch.await();
			chatSocket.close();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SocketServer socketServer=new SocketServer(8099);
		socketServer.start();
	}
}
