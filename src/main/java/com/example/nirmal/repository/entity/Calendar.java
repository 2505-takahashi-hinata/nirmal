package com.example.nirmal.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "calendars")
@Getter
@Setter
public class Calendar {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private String dayofweek;

    @Column
    private int holiday;
}
