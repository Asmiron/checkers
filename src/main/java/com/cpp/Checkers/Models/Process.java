package com.cpp.Checkers.Models;

import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name = "process")
public class Process {

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

    public String getJsPath() {
        return JsPath;
    }

    public void setJsPath(String jsPath) {
        JsPath = jsPath;
    }

    public Process() {

    }

    public int getProcessid() {
        return processid;
    }

    public void setProcessid(int processid) {
        this.processid = processid;
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
                "processid=" + processid +
                ", status='" + status + '\'' +
                ", init_date=" + init_date +
                ", del_date=" + del_date +
                '}';
    }
}
