package com.qiding.demo.roles.proposer;


import com.qiding.demo.roles.Acceptor;
import com.qiding.demo.roles.Proposer;
import com.qiding.demo.roles.param.ProposeRequest;
import com.qiding.demo.roles.param.ProposeResponse;

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
