package com.qiding.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.Charset;

public class QidingProducer {

	public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer mqProducer=new DefaultMQProducer("qiding-product-group2");
		mqProducer.setNamesrvAddr("master:9876");
		mqProducer.setSendMsgTimeout(30000);
		mqProducer.start();
		Message message=new Message("qiding-test","hello-world".getBytes(Charset.defaultCharset()));

		mqProducer.send(message);

		mqProducer.send(message);
	}

}
