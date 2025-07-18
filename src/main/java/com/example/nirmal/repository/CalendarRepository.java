package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
