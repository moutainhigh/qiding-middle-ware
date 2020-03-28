package com.qiding.jmx;

public interface HelloMBean {
    public String getName();
    public void setName(String name);
    public String printHello();
    public String printHello(String whoName);
}
