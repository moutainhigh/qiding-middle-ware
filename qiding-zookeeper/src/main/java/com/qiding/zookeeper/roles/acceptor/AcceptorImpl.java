package com.qiding.test.roles.acceptor;

import com.qiding.test.roles.Acceptor;
import com.qiding.test.roles.param.ProposeRequest;
import com.qiding.test.roles.param.ProposeResponse;
import lombok.Data;

@Data
public class AcceptorImpl implements Acceptor {

    private Long maxId;
    private Long acceptId;
    private String acceptValue;

    @Override
    public ProposeResponse preparePropose(ProposeRequest request) {
        //1.比较提案  requestId<=maxId  返回失败
        ProposeResponse response=new ProposeResponse(maxId,acceptId,acceptValue);
        response.setPass(Boolean.TRUE);

       if(request.getRequestId().compareTo(maxId) != 1){
           //接收过提案，新提案不合适
           response.setPass(Boolean.FALSE);
       }else{
           //接受提案
           this.maxId=request.getRequestId();
       }
        return response;
    }

    @Override
    public ProposeResponse acceptPropose(ProposeRequest request) {
        ProposeResponse response=new ProposeResponse(maxId,acceptId,acceptValue);
        if(request.getRequestId()>= this.maxId){

        }







        return null;
    }
}
