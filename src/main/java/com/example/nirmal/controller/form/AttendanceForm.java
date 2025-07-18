package com.example.nirmal.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AttendanceForm {
    private int id;
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "時刻を入力してください")
    private String workStart;
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "時刻を入力してください")
    private String workEnd;
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "時刻を入力してください")
    private String breakStart;
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "時刻を入力してください")
    private String breakEnd;
    private Short workStatus;
    private Short status;
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00.0")
    private Date workDate;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
}
