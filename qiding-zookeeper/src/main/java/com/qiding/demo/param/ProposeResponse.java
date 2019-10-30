package com.qiding.demo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProposeResponse {
    private Long maxId;
    private Long acceptId;
    private String valueId;
    private Boolean pass;

    public ProposeResponse(Long maxId, Long acceptId, String valueId) {
        this.maxId = maxId;
        this.acceptId = acceptId;
        this.valueId = valueId;
    }
}
