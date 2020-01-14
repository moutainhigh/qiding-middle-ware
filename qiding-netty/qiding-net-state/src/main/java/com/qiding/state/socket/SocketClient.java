package com.qiding.test.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @author qiding
 */
public class SocketClient {
	private String host;
	private Integer port;

	public SocketClient(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws IOException, InterruptedException {

		CountDownLatch countDownLatch=new CountDownLatch(1);
		//新建socket
		Socket socket=new Socket();
        //打开链接
		socket.connect(new InetSocketAddress(host,port));
		OutputStream outputStream=socket.getOutputStream();
//		while (true){
//			//不停写数据
			byte[]bytes ="hello world".getBytes();
			outputStream.write(bytes);
			outputStream.flush();
		    countDownLatch.await();
		//}
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		SocketClient client=new SocketClient("127.0.0.1",8099);
		client.start();

	}
}
