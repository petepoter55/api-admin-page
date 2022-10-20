package com.apiadminpage.entity.log;

import com.apiadminpage.environment.DatabaseSchema;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "LOG_DESCRIPTION", schema = DatabaseSchema.LogDescription)
public class LogDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DESCRIPTION")
    private String description;
}
