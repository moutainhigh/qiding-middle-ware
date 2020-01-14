package com.qiding.demo;

import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.impl.MQClientAPIImpl;
import org.apache.rocketmq.common.protocol.RequestCode;
import org.apache.rocketmq.common.protocol.header.UpdateConsumerOffsetRequestHeader;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MqAdminClient {



	public static void main(String[] args){

		String  addr1="172.28.61.25:10911";
	//	String addr2="172.28.61.26:9876";
		MQClientAPIImpl mqClientAPI = new MQClientAPIImpl(new NettyClientConfig(), null, null, new ClientConfig());
		mqClientAPI.start();
		UpdateConsumerOffsetRequestHeader requestHeader =new UpdateConsumerOffsetRequestHeader();
		requestHeader.setCommitOffset(4255400L);
		requestHeader.setQueueId(0);
		requestHeader.setConsumerGroup("notificationSingleChatConsumer");
		requestHeader.setTopic("temail-usermail");
		RemotingCommand  request=RemotingCommand.createRequestCommand(RequestCode.UPDATE_CONSUMER_OFFSET, requestHeader);
		try {
			RemotingCommand response1=  mqClientAPI.getRemotingClient().invokeSync(addr1,request, Duration.ofMillis(10000).toMillis());

		//	RemotingCommand response2=  mqClientAPI.getRemotingClient().invokeSync(addr2,request,Duration.ofMillis(100000).toMillis());

			response1.getBody();
		//	response2.getBody();


		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemotingConnectException e) {
			e.printStackTrace();
		} catch (RemotingSendRequestException e) {
			e.printStackTrace();
		} catch (RemotingTimeoutException e) {
			e.printStackTrace();
		}


	}

}
