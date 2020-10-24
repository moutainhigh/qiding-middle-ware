package com.qiding.sentinel.config;

import java.util.List;

import lombok.Data;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-10
 */
@Data
public class SentinelConfig {
        List<RuleConfig> rule;
}
