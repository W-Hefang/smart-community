package edu.xcc.smartcommunity.handler;

import lombok.Data;

@Data
public class BizException extends  Exception{
    public BizException(String message){
        super((message));
    }
}
