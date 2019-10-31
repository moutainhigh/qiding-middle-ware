package com.qiding.demo.proposer;


import com.qiding.demo.Acceptor;
import com.qiding.demo.Proposer;
import com.qiding.demo.param.ProposeRequest;
import com.qiding.demo.param.ProposeResponse;

import java.util.List;

public class ProposerImpl implements Proposer,Acceptor {

    private List<Acceptor>   acceptors;

    @Override
    public String getValue(String value) {
        //获取随机数量的acceptor

        //准备提案

        //提交提案
         return "null";
    }


    @Override
    public ProposeResponse preparePropose(ProposeRequest request) {
        return null;
    }

    @Override
    public ProposeResponse acceptPropose(ProposeRequest request) {
        return null;
    }

    private List<Acceptor> chooseAcceptor(){
        //
        return null;
    }
}
