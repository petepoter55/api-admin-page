package com.apiadminpage.entity.product;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "PRODUCT", schema = DatabaseSchema.Product)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "PRODUCTCODE")
    private String productCode;
    @Column(name = "PRODUCTTYPE")
    private String productType;
    @Column(name = "PRODUCTNAME")
    private String productName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "PRODUCTQUANTITY")
    private String productQuantity;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "CREATEBY")
    private String createBy;
    @Column(name = "UPDATEBY")
    private String updateBy;
    @Column(name = "CREATEDATETIME")
    private Date createDateTime;
    @Column(name = "UPDATEDATETIME")
    private Date updateDateTime;
    @Column(name = "DELFLAG")
    private Boolean delFlag;
}
