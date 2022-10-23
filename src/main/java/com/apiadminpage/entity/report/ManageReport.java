package com.apiadminpage.entity.report;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "MANAGE_REPORT", schema = DatabaseSchema.ManageReport)
public class ManageReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "VERSION")
    private String version;
    @Column(name = "STARTDATETIME")
    private String startDateTime;
    @Column(name = "ENDDATETIME")
    private String endDateTime;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FORM")
    private byte[] form;
    @Column(name = "CREATEBY")
    private String createBy;
    @Column(name = "UPDATEBY")
    private String updateBy;
    @Column(name = "CREATEDATETIME")
    private Date createDateTime;
    @Column(name = "UPDATEDATETIME")
    private Date updateDateTime;
}
