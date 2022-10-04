package com.apiadminpage.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "role", "firstname", "lastname", "email", "createBy", "updateBy", "createDateTime", "updateDateTime", "delFlag", "accountInfo"})
public class ResponseAccount {
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "username")
    private String username;

    @JsonProperty("role")
    @ApiModelProperty(position = 2, required = false, dataType = "String")
    private String role;

    @JsonProperty("firstname")
    @ApiModelProperty(position = 3, required = true, dataType = "String")
    private String firstname;

    @JsonProperty("lastname")
    @ApiModelProperty(position = 4, required = true, dataType = "String")
    private String lastname;

    @JsonProperty("email")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String email;

    @JsonProperty("createBy")
    @ApiModelProperty(position = 6, required = false, dataType = "String")
    private String createBy;

    @JsonProperty("updateBy")
    @ApiModelProperty(position = 7, required = false, dataType = "String")
    private String updateBy;

    @JsonProperty("createDateTime")
    @ApiModelProperty(position = 8, required = false, dataType = "String")
    private String createDateTime;

    @JsonProperty("updateDateTime")
    @ApiModelProperty(position = 9, required = false, dataType = "Date")
    private Date updateDateTime;

    @JsonProperty("delFlag")
    @ApiModelProperty(position = 10, required = false, dataType = "Boolean")
    private Boolean delFlag;

    @JsonProperty("accountInfo")
    @ApiModelProperty(position = 11, required = false, dataType = "ResponseAccountInfo")
    private ResponseAccountInfo responseAccountInfo;
}
