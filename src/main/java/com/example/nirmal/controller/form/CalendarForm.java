package com.example.nirmal.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CalendarForm {
    private int id;
    private String date;
    private int year;
    private int month;
    private String dayofweek;
    private int holiday;
}
