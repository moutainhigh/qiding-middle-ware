package com.qiding.test.roles;

import com.qiding.test.roles.param.ProposeRequest;
import com.qiding.test.roles.param.ProposeResponse;

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
