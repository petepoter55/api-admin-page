package com.apiadminpage.model.request.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"productName", "productCode", "productType", "description", "productQuantity", "price", "status", "createBy", "updateBy",
        "delFlag", "createDateTimeBefore", "createDateTimeEnd", "pageSize", "pageNumber"})
public class ProductInquiryRequest {
    @Size(max = 255)
    @JsonProperty("productName")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "product name")
    private String productName;

    @Size(max = 255)
    @JsonProperty("productCode")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "product type")
    private String productCode;

    @Size(max = 255)
    @JsonProperty("productType")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "product type")
    private String productType;

    @Size(max = 255)
    @JsonProperty("description")
    @ApiModelProperty(position = 4, required = true, dataType = "String", notes = "description")
    private String description;

    @Size(max = 50)
    @JsonProperty("productQuantity")
    @ApiModelProperty(position = 5, required = true, dataType = "String", notes = "product quantity")
    private String productQuantity;

    @Size(max = 50)
    @JsonProperty("price")
    @ApiModelProperty(position = 6, required = true, dataType = "String", notes = "price")
    private String price;

    @Size(max = 50)
    @JsonProperty("status")
    @ApiModelProperty(position = 7, required = true, dataType = "String", notes = "status")
    private String status;

    @JsonProperty("createBy")
    @ApiModelProperty(position = 8, required = false, dataType = "String")
    private String createBy;

    @JsonProperty("updateBy")
    @ApiModelProperty(position = 9, required = false, dataType = "String")
    private String updateBy;

    @JsonProperty("delFlag")
    @ApiModelProperty(position = 10, required = false, dataType = "String")
    private String delFlag;

    @JsonProperty("createDateTimeBefore")
    @ApiModelProperty(position = 11, required = false, dataType = "String")
    private String createDateTimeBefore;

    @JsonProperty("createDateTimeEnd")
    @ApiModelProperty(position = 12, required = false, dataType = "String")
    private String createDateTimeEnd;

    @JsonProperty("pageSize")
    @ApiModelProperty(position = 13, required = false, dataType = "Integer")
    private Integer pageSize;

    @JsonProperty("pageNumber")
    @ApiModelProperty(position = 14, required = false, dataType = "Integer")
    private Integer pageNumber;
}
