package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    //当月の勤怠情報取得
    @Modifying
    @Query("SELECT c.date, c.dayofweek, c.year, c.month, " +
            "a.workStart, a.workEnd, a.breakStart, a.breakEnd, a.workStatus, a.status " +
            "FROM Calendar c LEFT JOIN Attendance a ON c.date = a.workDate " +
            "WHERE c.year = :year AND c.month = :month " +
//            "WHERE c.date BETWEEN :start AND :end " +
//            "AND a.userId = :loginUserId " +
            "ORDER BY c.date ASC")
    public List<Object[]> findAllAttendance(int loginUserId, String year, String month);

}
