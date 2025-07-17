package com.example.nirmal.controller.form;

import com.example.nirmal.groups.ChangePasswordGroup;
import com.example.nirmal.groups.LoginGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;
    private String name;
    @NotBlank(message = "アカウントを入力してください",groups = LoginGroup.class)
    private String account;
    @NotBlank(message = "パスワードを入力してください",groups = {LoginGroup.class, ChangePasswordGroup.class})
    private String password;
    private String anotherPassword;
    private Short systemId;
    private Short approverId;
    private Short isStopped;
    private Date createdDate;
    private Date updatedDate;
}
