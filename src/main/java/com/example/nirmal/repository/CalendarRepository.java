package com.example.nirmal.repository;

import com.example.nirmal.controller.form.CalendarForm;
import com.example.nirmal.repository.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Repository

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
