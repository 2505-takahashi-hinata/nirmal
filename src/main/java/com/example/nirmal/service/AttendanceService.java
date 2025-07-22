package com.example.nirmal.service;


import com.example.nirmal.dto.AllApplication;
import com.example.nirmal.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;


    public List<AllApplication>findAllApplication(LocalDate start, LocalDate end, String name , Integer work_status){
        //現在月の1日を取得
        LocalDate now = LocalDate.now();
        LocalDate firstDate = now.withDayOfMonth(1);
        //現在月の末日を取得
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        //LocalDateTime型の入れ物を作っておく
        LocalDateTime startDate;
        LocalDateTime endDate;

        if(start != null){
            startDate = start.atStartOfDay();
        }else{
            startDate = LocalDateTime.of(firstDate,LocalTime.of(0,0,0));
        }
        if(end != null){
            endDate = end.atTime(23,59,59);
        }else{
            endDate = LocalDateTime.of(lastDayOfMonth, LocalTime.of(23,59,59));
        }

        if (StringUtils.isEmpty(name) && work_status == null) {
            List<Object[]> reqApplication = attendanceRepository.findApplication(startDate, endDate);
            return setDto(reqApplication);
        } else if (StringUtils.isEmpty(name)) {
            List<Object[]> reqApplication = attendanceRepository.findStatusApplication(startDate, endDate, work_status);
            return setDto(reqApplication);
        } else if (work_status == null) {
            List<Object[]> reqApplication = attendanceRepository.findNameApplication(startDate, endDate, name);
            return setDto(reqApplication);
        }else {
            List<Object[]> reqApplication = attendanceRepository.findAllApplication(startDate, endDate, name, work_status);
            return setDto(reqApplication);
        }
    }

    //Object[]を詰めたリストから一つずつ取り出し、AllApplicationDtoに詰め直してる
    public List<AllApplication> setDto(List<Object[]>reqApplication) {
        List<AllApplication> form = new ArrayList<>();
        for (Object[] objects:reqApplication){
            AllApplication dto = new AllApplication();
            dto.setName((String)objects[0]);
            dto.setDayofweek((String) objects[1]);
            dto.setDate((Date)objects[2]);
            dto.setId((int) objects[3]);
            dto.setWorkStart((String) objects[4]);
            dto.setWorkEnd((String) objects[5]);
            dto.setBreakStart((String) objects[6]);
            dto.setBreakEnd((String)objects[7]);
            dto.setStatus((Short) objects[8]);
            dto.setWorkStatus((Short) objects[9]);
            dto.setUserId((int) objects[10]);
            form.add(dto);
        }
        return form;
    }


    public void saveStatus(int id,int newStatus){
        attendanceRepository.saveStatus(id,newStatus);
    }

}
