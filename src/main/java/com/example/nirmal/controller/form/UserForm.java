package com.example.nirmal.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;
    private String name;
    private String account;
    private String password;
    private Short systemId;
    private Short approverId;
    private Short isStopped;
    private Date createdDate;
    private Date updatedDate;
}
