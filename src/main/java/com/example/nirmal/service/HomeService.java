package com.example.nirmal.service;

import com.example.nirmal.dto.workCalendar;
import com.example.nirmal.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HomeService {
    @Autowired
    AttendanceRepository attendanceRepository;

    public List<workCalendar> findAllAttendance(int loginUserId, LocalDate start, LocalDate end) throws ParseException {
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
            startDate = LocalDateTime.of(firstDate, LocalTime.of(0,0,0));
        }
        if(end != null){
            endDate = end.atTime(23,59,59);
        }else{
            endDate = LocalDateTime.of(lastDayOfMonth, LocalTime.of(23,59,59));
        }


        List<Object[]> works = attendanceRepository.findAllAttendance(loginUserId, startDate, endDate);
        return setDtoForm(works);
    }

    private List<workCalendar> setDtoForm(List<Object[]> works) throws ParseException {
        List<workCalendar> forms = new ArrayList<>();
        for (Object[] objects : works) {
            workCalendar work = new workCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd");
            work.setDate(dateFormat.format((Date)objects[0]));
            work.setDayofweek((String)objects[1]);
            work.setYear((int)objects[2]);
            work.setMonth((int)objects[3]);
            work.setWorkStart((String)objects[4]);
            work.setWorkEnd((String)objects[5]);
            work.setBreakStart((String)objects[6]);
            work.setBreakEnd((String)objects[7]);
            work.setStatus((Short)objects[8]);
            work.setWorkStatus((Short)objects[9]);
            forms.add(work);
        }
        return forms;
    }

}
