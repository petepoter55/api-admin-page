package com.apiadminpage.model.request.log;

import lombok.Data;

import java.util.Date;

@Data
public class LogRequest {
    private String createBy;
    private String type;
    private Date createDateTime;

    public LogRequest(String createBy, String type, Date createDateTime) {
        this.createBy = createBy;
        this.type = type;
        this.createDateTime = createDateTime;
    }
}
