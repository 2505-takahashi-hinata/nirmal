package com.example.nirmal.controller.form;

import com.example.nirmal.validator.AttendanceAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AttendanceForm {
    private int id;
//    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "出勤時刻を入力してください")
    @AttendanceAnnotation
    private String workStart;
//    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "退勤時刻を入力してください")
    @AttendanceAnnotation
    private String workEnd;
//    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "休憩開始時刻を入力してください")
    @AttendanceAnnotation
    private String breakStart;
//    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "休憩終了時刻を入力してください")
    @AttendanceAnnotation
    private String breakEnd;
    @NotNull(message = "勤務区分を入力してください")
    private Short workStatus;
    private Short status;
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00.0")
    private Date workDate;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
}
