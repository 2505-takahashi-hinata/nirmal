package com.example.nirmal.specification;

import com.example.nirmal.repository.entity.Attendance;
import com.example.nirmal.repository.entity.Calendar;
import com.example.nirmal.repository.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AttendanceSpecification {

    public static Specification<Attendance> applicationFilter(
            LocalDate startDate,
            LocalDate endDate,
            String name,
            Integer workStatus
    ) {
        return (root, query, builder) -> {
            // JOIN
            Join<Attendance, User> userJoin = root.join("user", JoinType.LEFT);
            Join<Attendance, Calendar> calendarJoin = root.join("calendar", JoinType.LEFT);

            // DTO用のselect（必須）
            query.multiselect(
                    userJoin.get("name"),
                    calendarJoin.get("dayofweek"),
                    calendarJoin.get("date"),
                    root.get("id"),
                    root.get("workStart"),
                    root.get("workEnd"),
                    root.get("breakStart"),
                    root.get("breakEnd"),
                    root.get("status"),
                    root.get("workStatus"),
                    root.get("user").get("id")
            );

            List<Predicate> predicates = new ArrayList<>();

            // status固定
            predicates.add(root.get("status").in(List.of(1, 2, 3)));

            if (startDate != null && endDate != null) {
                predicates.add(builder.between(calendarJoin.get("date"), startDate, endDate));
            }

            if (StringUtils.hasText(name)) {
                predicates.add(builder.like(userJoin.get("name"), "%" + name + "%"));
            }

            if (workStatus != null) {
                predicates.add(builder.equal(root.get("workStatus"), workStatus));
            }

            query.orderBy(builder.asc(calendarJoin.get("date")));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

