package com.example.nirmal.service;

import com.example.nirmal.dto.workCalendar;
import com.example.nirmal.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HomeService {
    @Autowired
    AttendanceRepository attendanceRepository;

    public List<workCalendar> findAllAttendance(int loginUserId, LocalDate start, LocalDate end) throws ParseException {
        Date date = new Date();
        String strYear = null;
        String strMonth = null;
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        if(start != null) {
            strYear = yearFormat.format(start);
        } else {
            strYear = yearFormat.format(date);
        }

        if (end != null) {
            strMonth = monthFormat.format(end);
        } else {
            strMonth = monthFormat.format(date);
        }


        List<Object[]> works = attendanceRepository.findAllAttendance(loginUserId, strYear, strMonth);
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
