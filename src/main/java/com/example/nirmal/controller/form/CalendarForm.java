package com.example.nirmal.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class CalendarForm {
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00.0")
    private Date date;
    private int year;
    private int month;
    private String dayofweek;
    private int holiday;
}
