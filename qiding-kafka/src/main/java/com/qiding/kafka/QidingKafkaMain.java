package com.qiding.kafka;

import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-16
 */
public class QidingKafkaMain {
    public static void main(String[] args) {
        QidingKafkaConfig config=new QidingKafkaConfig("172.29.11.25:9092,172.29.11.14:9092,172.29.17.49:9092");
     //   KafkaConsumer kafkaConsumer=new KafkaConsumer(config.consumer());
//        kafkaConsumer.subscribe("kim_kim_user_quit_company_event", new ConsumerRebalanceListener() {
//            @Override
//            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//                System.out.println("222");
//            }
//
//            @Override
//            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//                System.out.println("111");
//            }
//        });
    }
}
