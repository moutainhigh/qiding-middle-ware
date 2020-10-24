package com.qiding.kafka;

import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-16
 */
class MyCallBack{
    static void onCompletion(RecordMetadata metadata, Exception exception){
        System.out.println("1111");
    }
}



public class QidingKafkaProducer {

   private  Properties producer(){
        //1.参数配置：端口、缓冲内存、最大连接数、key序列化、value序列化等等（不是每一个非要配置）
        java.util.Properties props=new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public void start() throws InterruptedException {
        KafkaProducer<String,String> kafkaProducer=new KafkaProducer<>(producer());
        while (true){
            ProducerRecord<String,String> record=new ProducerRecord<String,String>("qiding-helloworld",0,"qi","ding"+ Instant.now().toString());

            kafkaProducer.send(record, MyCallBack::onCompletion);



        }
    }

    public static void main(String[] args) throws InterruptedException {
        QidingKafkaProducer producer=new QidingKafkaProducer();
        producer.start();
    }
}
