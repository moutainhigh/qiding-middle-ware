package com.qiding.kafka;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.record.KafkaLZ4BlockInputStream;

/**
 * Hello world!
 */
public class QidingKafkaConsumer{

    public Properties consumer(){
        //1.参数配置:不是每一非得配置
        Properties props = new Properties();
        //2.服务器地址
        props.put("bootstrap.servers", "127.0.0.1:9092");
        //3.groupId
        props.put("group.id", "qiding-kafka-consumer4");
        //3.是否自动提交
        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "10000");
        //4.每次拉取多少条
        props.put("max.poll.records", 20);
        //5.session-timeout
        props.put("session.timeout.ms", "30000");
        //6.消费起始位置
        props.put("auto.offset.reset", "earliest");
        //序列化
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    KafkaConsumer<String,String>kafkaConsumer=new KafkaConsumer<>(consumer());



    public void start(){
        kafkaConsumer.subscribe(Arrays.asList("qiding-helloworld"));
        while (true){
            System.out.println("\n开始时间="+Instant.now().toEpochMilli());
            ConsumerRecords<String,String> records= kafkaConsumer.poll(Duration.ofSeconds(1));
            System.out.println(records.count());
            System.out.println("结束时间="+Instant.now().toEpochMilli());
            records.forEach(record->{
                System.out.println("key="+record.key());
                System.out.println("value="+record.value());
            });
            kafkaConsumer.commitSync();
        }

    }



    public static void main(String[] args) {
        QidingKafkaConsumer consumer=new QidingKafkaConsumer();
        consumer.start();
    }

}
