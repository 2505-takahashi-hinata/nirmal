package com.example.nirmal.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceForm {
    private int id;
    private Date workStart;
    private Date workEnd;
    private Date breakStart;
    private Date breakEnd;
    private Short workStatus;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
}
