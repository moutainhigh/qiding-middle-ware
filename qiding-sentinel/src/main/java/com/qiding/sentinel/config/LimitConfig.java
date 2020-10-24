package com.qiding.sentinel.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;


/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-10
 */
public class LimitConfig {
    public void initLimit(){
        Properties properties=new Properties();
        try (InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("sentinel.properties")){
            properties.load(inputStream);
            SentinelConfig sentinelConfig =new SentinelConfig();
            BeanWrapper beanWrapper=new BeanWrapperImpl(sentinelConfig);
            beanWrapper.setAutoGrowNestedPaths(true);
            beanWrapper.setPropertyValues(properties);
            List<FlowRule> limitRules= sentinelConfig.getRule().stream()
                    .map(LimitConfig::flowRule)
                    .collect(Collectors.toList());
            FlowRuleManager.loadRules(limitRules);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static FlowRule flowRule(RuleConfig ruleConfig){
        FlowRule flowRule=new FlowRule();
        flowRule.setResource(ruleConfig.getName());
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(ruleConfig.getQps());
        return flowRule;
    }
}
