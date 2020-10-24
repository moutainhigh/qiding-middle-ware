package com.qiding.sentinel.loader;

import com.qiding.sentinel.IHelloWorld;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-15
 */
public class HelloWorld1 implements IHelloWorld {
    public HelloWorld1() {
        System.out.println(1111);
    }

    @Override
    public void world() {
        System.out.println("1");
    }
}
