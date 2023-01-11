package com.apiadminpage.model.response.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtResponse {
    private String id;
    private Date expire;
    private Date issueDate;
}
