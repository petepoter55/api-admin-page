package com.apiadminpage.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"userId", "phone", "birthDate", "citizenId", "province", "district", "createBy", "updateBy", "createDateTime", "updateDateTime", "delFlag"})
public class ResponseAccountInfo {
    @JsonProperty("userId")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "userId")
    private String userId;

    @JsonProperty("phone")
    @ApiModelProperty(position = 2, required = false, dataType = "String")
    private String phone;

    @JsonProperty("birthDate")
    @ApiModelProperty(position = 3, required = false, dataType = "String")
    private String birthDate;

    @JsonProperty("citizenId")
    @ApiModelProperty(position = 4, required = false, dataType = "String")
    private String citizenId;

    @JsonProperty("province")
    @ApiModelProperty(position = 5, required = false, dataType = "String")
    private String province;

    @JsonProperty("district")
    @ApiModelProperty(position = 6, required = false, dataType = "String")
    private String district;

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
}
