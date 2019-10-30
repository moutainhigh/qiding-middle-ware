package com.qiding.demo;

import com.qiding.demo.param.ProposeRequest;
import com.qiding.demo.param.ProposeResponse;

public interface Acceptor {
    /**
     * 申请提案
     * @param request
     * @return
     */
     ProposeResponse preparePropose(ProposeRequest request);



    /**
     * 接受提案
     * @param request
     * @return
     */
    ProposeResponse acceptPropose(ProposeRequest request);

}
