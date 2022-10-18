package com.apiadminpage.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "email", "role", "firstname", "lastname", "delFlag","createDateTimeBefore","createDateTimeEnd", "pageSize", "pageNumber"})
public class InquiryAccountRequest {
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "Username")
    private String username;

    @JsonProperty("email")
    @ApiModelProperty(position = 2, required = true, dataType = "String")
    private String email;

    @JsonProperty("role")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "permission account")
    private String role;

    @JsonProperty("firstname")
    @ApiModelProperty(position = 4, required = true, dataType = "String")
    private String firstname;

    @JsonProperty("lastname")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String lastname;

    @JsonProperty("delFlag")
    @ApiModelProperty(position = 6, required = false, dataType = "Boolean")
    private Boolean delFlag;

    @JsonProperty("createDateTimeBefore")
    @ApiModelProperty(position = 7, required = false, dataType = "String")
    private String createDateTimeBefore;

    @JsonProperty("createDateTimeEnd")
    @ApiModelProperty(position = 8, required = false, dataType = "String")
    private String createDateTimeEnd;

    @JsonProperty("pageSize")
    @ApiModelProperty(position = 9, required = false, dataType = "Integer")
    private Integer pageSize;

    @JsonProperty("pageNumber")
    @ApiModelProperty(position = 10, required = false, dataType = "Integer")
    private Integer pageNumber;

    @Override
    public String toString() {
        return "InquiryAccountRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", delFlag=" + delFlag +
                ", createDateTimeBefore='" + createDateTimeBefore + '\'' +
                ", createDateTimeEnd='" + createDateTimeEnd + '\'' +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
