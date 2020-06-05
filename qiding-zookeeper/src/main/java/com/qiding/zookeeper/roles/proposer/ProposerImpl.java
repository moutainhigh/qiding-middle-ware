package com.qiding.test.roles.proposer;


import com.qiding.test.roles.Acceptor;
import com.qiding.test.roles.Proposer;
import com.qiding.test.roles.param.ProposeRequest;
import com.qiding.test.roles.param.ProposeResponse;

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
