package com.example.nirmal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendance {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "work_start")
    private String workStart;

    @Column(name = "work_end")
    private String workEnd;

    @Column(name = "break_start")
    private String breakStart;

    @Column(name = "break_end")
    private String breakEnd;

    @Column(name = "work_status")
    private Short workStatus;

    @Column
    private Short status;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name = "work_date")
    private Date workDate;
}
