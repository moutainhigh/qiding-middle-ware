package com.qiding.sentinel.config;

import lombok.Data;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-10
 */
@Data
public class RuleConfig {
    private String name;
    private Integer qps;
}
