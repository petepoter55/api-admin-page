package com.apiadminpage.entity.account;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ACCOUNT_INFO", schema = DatabaseSchema.AccountInfo)
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "USERID")
    private String userId;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "BIRTHDATE")
    private String birthDate;
    @Column(name = "CITIZENID")
    private String citizenId;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "DISTRICT")
    private String district;
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
