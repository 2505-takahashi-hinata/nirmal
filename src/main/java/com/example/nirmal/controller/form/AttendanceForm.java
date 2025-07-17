package com.example.nirmal.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceForm {
    private int id;
    private String workStart;
    private String workEnd;
    private String breakStart;
    private String breakEnd;
    private Short workStatus;
    private Short status;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
    private Date workDate;
}
