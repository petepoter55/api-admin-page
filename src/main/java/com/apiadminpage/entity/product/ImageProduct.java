package com.apiadminpage.entity.product;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "IMAGE_PRODUCT", schema = DatabaseSchema.ImageProduct)
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "PRODUCTCODE")
    private String productCode;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IMAGE")
    private byte[] image;
    @Column(name = "CREATEBY")
    private String createBy;
    @Column(name = "UPDATEBY")
    private String updateBy;
    @Column(name = "CREATEDATETIME")
    private Date createDateTime;
    @Column(name = "UPDATEDATETIME")
    private Date updateDateTime;
}
