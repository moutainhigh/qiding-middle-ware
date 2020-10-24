package com.qiding.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qiding.sentinel.config.LimitConfig;

/**
 * Hello world!
 *
 */
public class SentinelMain
{
    public static void main( String[] args )
    {
        LimitConfig limitConfig=new LimitConfig();
        limitConfig.initLimit();

        while (true) {
            try(Entry entry= SphU.entry("qi")) {
                /*您的业务逻辑 - 开始*/
                System.out.println("hello world");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                /*流控逻辑处理 - 开始*/
                System.out.println("block!");
                /*流控逻辑处理 - 结束*/
            }
        }
    }
}
