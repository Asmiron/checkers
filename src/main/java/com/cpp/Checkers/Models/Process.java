package com.cpp.Checkers.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

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
    private Timestamp init_date;

    @Column(name = "del_date")
    private Timestamp del_date;

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

    public Timestamp getDel_date() {
        return del_date;
    }

    public void setDel_date(Timestamp del_date) {
        this.del_date = del_date;
    }

    public Timestamp getInit_date() {
        return init_date;
    }

    public void setInit_date(Timestamp init_date) {
        this.init_date = init_date;
    }
}
