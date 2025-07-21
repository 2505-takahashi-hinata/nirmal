package com.example.nirmal.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class workCalendar {
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00.0")
    private Date date;
    private String dayofweek;
    private int year;
    private int month;
    private String workStart;
    private String workEnd;
    private String breakStart;
    private String breakEnd;
    private Short workStatus;
    private int userId;
    private Short status;
    private Integer id;
    private Date createdDate;
    private Date updatedDate;
}