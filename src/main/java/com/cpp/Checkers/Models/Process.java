package com.cpp.Checkers.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name = "process")
@Getter
@Setter
@NoArgsConstructor
public class Process {
    public Process(String status) {
        this.status = status;
    }

    @Id
    @Column(name = "process_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int processid;

    @Column(name = "status")
    private String status;

    @Column(name = "init_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date init_date;

    @Column(name = "del_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date del_date;

    @Column(name = "jspath")
    private String JsPath;


    @Override
    public String toString() {
        return "Process{" +
                "processid=" + processid +
                ", status='" + status + '\'' +
                ", init_date=" + init_date +
                ", del_date=" + del_date +
                '}';
    }
}
