package com.qiding.demo.roles;

import com.qiding.demo.roles.param.ProposeRequest;
import com.qiding.demo.roles.param.ProposeResponse;

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
