package com.apiadminpage.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "password", "role", "firstname", "lastname", "email", "createBy",
        "updateBy", "createDateTime", "updateDateTime", "delFlag"})
public class AccountRequest {
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "Username")
    private String username;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("username")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "Password")
    private String password;

    @NotNull
    @Size(min = 1, max = 10)
    @JsonProperty("role")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "permission account")
    private String role;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("firstname")
    @ApiModelProperty(position = 4, required = true, dataType = "String")
    private String firstname;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("lastname")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String lastname;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("email")
    @ApiModelProperty(position = 6, required = true, dataType = "String")
    private String email;

    @JsonProperty("createBy")
    @ApiModelProperty(position = 7, required = false, dataType = "String")
    private String createBy;

    @JsonProperty("updateBy")
    @ApiModelProperty(position = 8, required = false, dataType = "String")
    private String updateBy;

    @JsonProperty("createDateTime")
    @ApiModelProperty(position = 9, required = false, dataType = "Date")
    private Date createDateTime;

    @JsonProperty("updateDateTime")
    @ApiModelProperty(position = 10, required = false, dataType = "Date")
    private Date updateDateTime;
    
    @JsonProperty("delFlag")
    @ApiModelProperty(position = 11, required = false, dataType = "Boolean")
    private Boolean delFlag;

    @Override
    public String toString() {
        return "AccountReq {" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
