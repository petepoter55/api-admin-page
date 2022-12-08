package com.apiadminpage.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "password"})
public class AccountLoginRequest {
    @NotNull
    @Size(min = 1, max = 15)
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "username")
    private String username;

    @NotNull
    @Size(min = 1, max = 15)
    @JsonProperty("password")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*]).{8,}$", message = "Format invalid because " +
            "At least one upper case English letter, " +
            "At least one lower case English letter, " +
            "At least one digit, (?=.*?[0-9]),  " +
            "At least one special character, " +
            "Minimum eight in length .{8,}")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "password")
    private String password;

    @Override
    public String toString() {
        return "AccountLoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
