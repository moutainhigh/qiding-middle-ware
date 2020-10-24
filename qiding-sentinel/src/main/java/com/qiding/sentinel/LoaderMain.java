package com.qiding.sentinel;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.qiding.sentinel.loader.HelloWorld2;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-15
 */
public class LoaderMain {
    public static void main(String[] args) {

        HelloWorld2 helloWorld2=new HelloWorld2();

        ServiceLoader<IHelloWorld> helloWords= ServiceLoader.load(IHelloWorld.class,HelloWorld2.class.getClassLoader());
        for (IHelloWorld helloWord:helloWords){
            helloWord.world();
        }


        ServiceLoader<InitFunc> helloWords2= ServiceLoader.load(InitFunc.class);
        Iterator iterator2=helloWords2.iterator();
        System.out.println(iterator2.hasNext());



    }
}
