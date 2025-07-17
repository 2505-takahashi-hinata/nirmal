package com.example.nirmal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AllApplication {
    private String name;
    private String dayofweek;
    private Date date;
    private int id;
    private String workStart;
    private String workEnd;
    private String breakStart;
    private String breakEnd;
    private Short status;
    private Short workStatus;
    private int userId;
}
