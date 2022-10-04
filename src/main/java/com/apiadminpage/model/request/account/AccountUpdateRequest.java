package com.apiadminpage.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "username", "role", "firstname", "lastname", "email", "updateBy", "delFlag"})
public class AccountUpdateRequest {
    @JsonProperty("id")
    @ApiModelProperty(position = 1, required = true, dataType = "Integer", notes = "Id")
    private Integer id;
    @NotBlank
    @Size(max = 255)
    @JsonProperty("username")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "Username")
    private String username;

    @Size(min = 1, max = 10)
    @JsonProperty("role")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "permission account")
    private String role;

    @Size(min = 1, max = 50)
    @JsonProperty("firstname")
    @ApiModelProperty(position = 4, required = true, dataType = "String")
    private String firstname;

    @Size(min = 1, max = 50)
    @JsonProperty("lastname")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String lastname;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("email")
    @ApiModelProperty(position = 6, required = true, dataType = "String")
    private String email;

    @JsonProperty("updateBy")
    @ApiModelProperty(position = 7, required = false, dataType = "String")
    private String updateBy;

    @JsonProperty("delFlag")
    @ApiModelProperty(position = 8, required = false, dataType = "Boolean")
    private Boolean delFlag;

    @Override
    public String toString() {
        return "AccountUpdateRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
