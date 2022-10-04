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
@JsonPropertyOrder({"userId", "phone", "birthDate", "citizenId", "province", "district", "createBy", "updateBy", "delFlag"})
public class AccountInfoRequest {
    @NotNull
    @Size(min = 1, max = 10)
    @JsonProperty("userId")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "user Id")
    private String userId;
    @Size(max = 25)
    @JsonProperty("phone")
    @ApiModelProperty(position = 2, required = false, dataType = "String", notes = "phone")
    private String phone;
    @Size(max = 20)
    @JsonProperty("birthDate")
    @Pattern(regexp = "(19|20|21)[0-9]{2}-((0(0|1|2|3|4|5|6|7|8|9))|(10|11|12))-(([0-2][0-9])|30|31)", message = "Expected format YYYY-MM-DD")
    @ApiModelProperty(position = 3, required = false, dataType = "String", notes = "birthDate")
    private String birthDate;
    @Size(max = 50)
    @JsonProperty("citizenId")
    @ApiModelProperty(position = 4, required = false, dataType = "String", notes = "citizenId")
    private String citizenId;
    @Size(max = 150)
    @JsonProperty("province")
    @ApiModelProperty(position = 5, required = false, dataType = "String", notes = "province")
    private String province;
    @Size(max = 150)
    @JsonProperty("district")
    @ApiModelProperty(position = 6, required = false, dataType = "String", notes = "district")
    private String district;
    @Size(max = 20)
    @JsonProperty("createBy")
    @ApiModelProperty(position = 7, required = false, dataType = "String", notes = "createBy")
    private String createBy;
    @Size(max = 20)
    @JsonProperty("updateBy")
    @ApiModelProperty(position = 8, required = false, dataType = "String", notes = "updateBy")
    private String updateBy;
    @JsonProperty("delFlag")
    @ApiModelProperty(position = 8, required = false, dataType = "Boolean", notes = "delFlag")
    private Boolean delFlag;
}
