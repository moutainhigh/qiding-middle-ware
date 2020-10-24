package com.qiding.sentinel.loader;

import com.qiding.sentinel.IHelloWorld;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-15
 */
public class HelloWorld2 implements IHelloWorld {
    public HelloWorld2() {
        System.out.println(222222);
    }

    @Override
    public void world() {
        System.out.println("hello2");
    }
}
