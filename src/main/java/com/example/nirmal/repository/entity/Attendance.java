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
    private Date workStart;

    @Column(name = "work_end")
    private Date workEnd;

    @Column(name = "break_start")
    private Date breakStart;

    @Column(name = "break_end")
    private Date breakEnd;

    @Column(name = "work_status")
    private Date workStatus;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    @UpdateTimestamp
    private Date updatedDate;
}
