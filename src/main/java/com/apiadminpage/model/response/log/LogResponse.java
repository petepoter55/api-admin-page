package com.apiadminpage.model.response.log;

import lombok.Builder;
import lombok.Data;

@Data
public class LogResponse {
    private String username;
    private String description;
    private String createDateTime;

}
