package com.qiding.test.scenes.coordinator;

public enum  EnumState {
   RUNNING(1,"running"),
   STANDBY(2,"standby"),
   SEARCH(3,"search..")
    ;
    private Integer code;
    private String  desc;

    EnumState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
