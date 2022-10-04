package com.apiadminpage.entity.log;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "LOG_INFO", schema = DatabaseSchema.LogInfo)
public class LogInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "CREATEDATETIME")
    private Date createDateTime;
}
