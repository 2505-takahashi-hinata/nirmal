package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.User;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Query("SELECT u.name,c.dayofweek,c.date,a.id,a.workStart,a.workEnd, " +
            "a.breakStart,a.breakEnd,a.status,a.workStatus,a.userId " +
            "FROM Attendance a LEFT JOIN User u ON a.userId = u.id " +
            "LEFT JOIN Calendar c ON a.workDate = c.date " +
            "WHERE a.status IN (1, 2, 3) " +
            "AND c.date BETWEEN :startDate AND :endDate " +
            "ORDER BY c.date ASC ")
    public List<Object[]> findApplication(@Param("startDate")LocalDateTime startDate,
                                          @Param("endDate")LocalDateTime endDate);

    @Query("SELECT u.name,c.dayofweek,c.date,a.id,a.workStart,a.workEnd, " +
            "a.breakStart,a.breakEnd,a.status,a.workStatus,a.userId " +
            "FROM Attendance a LEFT JOIN User u ON a.userId = u.id " +
            "LEFT JOIN Calendar c ON a.workDate = c.date " +
            "WHERE a.status IN (1, 2, 3) " +
            "AND c.date BETWEEN :startDate AND :endDate " +
            "AND a.workStatus = :workStatus " +
            "ORDER BY c.date ASC ")
    public List<Object[]>findStatusApplication(@Param("startDate")LocalDateTime startDate,
                                               @Param("endDate")LocalDateTime endDate,
                                               @Param("workStatus")int work_status);

    @Query("SELECT u.name,c.dayofweek,c.date,a.id,a.workStart,a.workEnd, " +
            "a.breakStart,a.breakEnd,a.status,a.workStatus,a.userId " +
            "FROM Attendance a LEFT JOIN User u ON a.userId = u.id " +
            "LEFT JOIN Calendar c ON a.workDate = c.date " +
            "WHERE a.status IN (1, 2, 3) " +
            "AND c.date BETWEEN :startDate AND :endDate " +
            "AND u.name LIKE %:name% " +
            "ORDER BY c.date ASC ")
    public List<Object[]>findNameApplication(@Param("startDate")LocalDateTime startDate,
                                             @Param("endDate")LocalDateTime endDate,
                                             @Param("name")String name);

    @Query("SELECT u.name,c.dayofweek,c.date,a.id,a.workStart,a.workEnd, " +
            "a.breakStart,a.breakEnd,a.status,a.workStatus,a.userId " +
            "FROM Attendance a LEFT JOIN User u ON a.userId = u.id " +
            "LEFT JOIN Calendar c ON a.workDate = c.date " +
            "WHERE a.status IN (1, 2, 3) " +
            "AND c.date BETWEEN :startDate AND :endDate " +
            "AND a.workStatus = :workStatus " +
            "AND u.name LIKE %:name% " +
            "ORDER BY c.date ASC ")
    public List<Object[]>findAllApplication(@Param("startDate")LocalDateTime startDate,
                                            @Param("endDate")LocalDateTime endDate,
                                            @Param("name")String name,
                                            @Param("workStatus")int work_status);

    @Transactional
    @Modifying
    @Query("UPDATE Attendance a SET a.status = :status WHERE a.id = :id ")
    public void saveStatus(@Param("id") int id, @Param("status") int newStatus);

    //当月の勤怠情報取得
    @Modifying
    @Query("SELECT c.date, c.dayofweek, c.year, c.month, " +
            "a.workStart, a.workEnd, a.breakStart, a.breakEnd, a.status, a.workStatus, a.userId, a.id " +
            "FROM Calendar c LEFT JOIN Attendance a ON c.date = a.workDate " +
//            "WHERE c.year = :year AND c.month = :month " +
            "WHERE c.date BETWEEN :start AND :end " +
//            "AND a.userId = :loginUserId " +
            "ORDER BY c.date ASC")
    public List<Object[]> findAllAttendance(int loginUserId, LocalDateTime start, LocalDateTime end);


    boolean existsByWorkDate(Date workDate);
    boolean existsByWorkDateNot(Date workDate);
}
