package com.apiadminpage.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "password", "confirmPassword", "role", "firstname", "lastname", "email", "confirmEmail", "createBy", "delFlag"})
public class AccountRequest {
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "Username")
    private String username;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("password")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "Password")
    private String password;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("confirmPassword")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "Confirm-Password")
    private String confirmPassword;

    @NotNull
    @Size(min = 1, max = 10)
    @JsonProperty("role")
    @ApiModelProperty(position = 4, required = true, dataType = "String", notes = "permission account")
    private String role;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("firstname")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String firstname;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("lastname")
    @ApiModelProperty(position = 6, required = true, dataType = "String")
    private String lastname;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("email")
    @ApiModelProperty(position = 7, required = true, dataType = "String")
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("confirmEmail")
    @ApiModelProperty(position = 8, required = true, dataType = "String")
    private String confirmEmail;

    @JsonProperty("createBy")
    @ApiModelProperty(position = 9, required = false, dataType = "String")
    private String createBy;

    @JsonProperty("delFlag")
    @ApiModelProperty(position = 10, required = false, dataType = "Boolean")
    private Boolean delFlag;

    @Override
    public String toString() {
        return "AccountReq {" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", confirmEmail='" + confirmEmail + '\'' +
                ", createBy='" + createBy + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
