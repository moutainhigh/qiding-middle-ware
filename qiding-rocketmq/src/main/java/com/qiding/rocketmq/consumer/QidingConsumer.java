package com.qiding.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.Charset;
import java.util.List;

public class QidingConsumer {
	public static void main(String[] args) throws MQClientException {

		DefaultMQPushConsumer mqPushConsumer=new DefaultMQPushConsumer("qiding-consumer-group");
		mqPushConsumer.setNamesrvAddr("master:9876");
		mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		mqPushConsumer.setConsumeTimeout(100000);
		mqPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		mqPushConsumer.subscribe("qiding-test","*");
		mqPushConsumer.setMessageListener((MessageListenerOrderly) (msgs, context) ->  {
		        msgs.forEach(msg->{
					System.out.println(new String(msg.getBody(), Charset.defaultCharset()));
				});
		        return ConsumeOrderlyStatus.SUCCESS;
		});
		mqPushConsumer.start();
	}
}
