package com.qiding.kafka;

import java.util.Properties;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-16
 */
public class QidingKafkaConfig {

   private String bootServers;

    public QidingKafkaConfig(String bootServers) {
        this.bootServers = bootServers;
    }

}
