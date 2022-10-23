package com.apiadminpage.model.request.report;

import lombok.Data;

@Data
public class ManageReportRequest {
    private String name;
    private String startDate;
    private String endDate;
    private String createBy;
}
