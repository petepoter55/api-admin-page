package com.apiadminpage.model.request.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"productName", "productType", "description", "productQuantity", "price", "createBy", "updateBy", "delFlag"})
public class ProductRequest {
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("productName")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "product name")
    private String productName;
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("productType")
    @ApiModelProperty(position = 2, required = true, dataType = "String", notes = "product type")
    private String productType;
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("description")
    @ApiModelProperty(position = 3, required = true, dataType = "String", notes = "description")
    private String description;
    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("productQuantity")
    @ApiModelProperty(position = 4, required = true, dataType = "String", notes = "product quantity")
    private String productQuantity;
    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty("price")
    @ApiModelProperty(position = 5, required = true, dataType = "String", notes = "price")
    private String price;
    @JsonProperty("createBy")
    @ApiModelProperty(position = 6, required = false, dataType = "String")
    private String createBy;
    @JsonProperty("updateBy")
    @ApiModelProperty(position = 7, required = false, dataType = "String")
    private String updateBy;
    @JsonProperty("delFlag")
    @ApiModelProperty(position = 8, required = false, dataType = "String")
    private String delFlag;
}
