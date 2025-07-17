package com.example.nirmal.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class workCalendar {
    private String date;
    private String dayofweek;
    private int year;
    private int month;
    private String workStart;
    private String workEnd;
    private String breakStart;
    private String breakEnd;
    private Short status;
    private Short workStatus;
    private Date createdDate;
    private Date updatedDate;
}