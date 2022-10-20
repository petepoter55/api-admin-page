package com.apiadminpage.entity.account;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "IMAGE_ACCOUNT", schema = DatabaseSchema.ImageAccount)
public class ImageAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "USERID")
    private String userId;
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
