package com.cpp.Checkers.Models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "process")
public class Process {

    @Id
    @Column(name = "process_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int process_id;

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

    public String getJsPath() {
        return JsPath;
    }

    public void setJsPath(String jsPath) {
        JsPath = jsPath;
    }

    public Process() {

    }

    public int getProcess_id() {
        return process_id;
    }

    public void setProcess_id(int process_id) {
        this.process_id = process_id;
    }

    public Process(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDel_date() {
        return del_date;
    }

    public void setDel_date(Date del_date) {
        this.del_date = del_date;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    @Override
    public String toString() {
        return "Process{" +
                "process_id=" + process_id +
                ", status='" + status + '\'' +
                ", init_date=" + init_date +
                ", del_date=" + del_date +
                '}';
    }
}
