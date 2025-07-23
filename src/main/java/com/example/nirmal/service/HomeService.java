package com.example.nirmal.service;

import com.example.nirmal.controller.form.AttendanceForm;
import com.example.nirmal.controller.form.CalendarForm;
import com.example.nirmal.dto.workCalendar;
import com.example.nirmal.repository.AttendanceRepository;
import com.example.nirmal.repository.CalendarRepository;
import com.example.nirmal.repository.entity.Attendance;
import com.example.nirmal.repository.entity.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
        LocalDate now = LocalDate.now();
        LocalDate firstDate = now.withDayOfMonth(1);

        YearMonth currentYearMonth = YearMonth.now();
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();

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
            work.setDate((Date)objects[0]);
            work.setDayofweek((String)objects[1]);
            work.setYear((int)objects[2]);
            work.setMonth((int)objects[3]);
            work.setWorkStart((String)objects[4]);
            work.setWorkEnd((String)objects[5]);
            work.setBreakStart((String)objects[6]);
            work.setBreakEnd((String)objects[7]);
            work.setStatus((Short)objects[8]);
            work.setWorkStatus((Short)objects[9]);
            work.setUserId((Integer) objects[10]);
            work.setId((Integer) objects[11]);
            forms.add(work);
        }
        return forms;
    }
    public void saveAttendance(AttendanceForm attendance) throws ParseException {

        Attendance saveAttendance = setAttendanceEntity(attendance);
        attendanceRepository.save(saveAttendance);
    }

    public Attendance setAttendanceEntity(AttendanceForm repWork) {
        Attendance work = new Attendance();
        work.setId(repWork.getId());
        work.setWorkStart(repWork.getWorkStart());
        work.setWorkEnd(repWork.getWorkEnd());
        work.setBreakStart(repWork.getBreakStart());
        work.setBreakEnd(repWork.getBreakEnd());
        work.setStatus(repWork.getStatus());
        work.setWorkStatus(repWork.getWorkStatus());
        work.setWorkDate(repWork.getWorkDate());
        work.setUserId(repWork.getUserId());
        work.setCreatedDate(repWork.getCreatedDate());
        work.setUpdatedDate(repWork.getUpdatedDate());
        return work;
    }

    public boolean checkDate(Date workDate) {
        if (workDate != null) {
            return attendanceRepository.existsByWorkDate(workDate);
        }
        return false;
    }

    public void deleteWork(Integer id){
        attendanceRepository.deleteById(id);
    }


}
