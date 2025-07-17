package com.example.nirmal.controller.form;

import com.example.nirmal.validator.PasswordValidator;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class UserForm {
    public interface UserEdit{}
    public interface UserSignUp{}

    private int id;
    @NotBlank(message ="氏名を入力してください", groups = {UserEdit.class, UserSignUp.class})
    @Size(max = 10, message = "氏名は10文字以下で入力してください", groups = {UserEdit.class, UserSignUp.class})
    private String name;
    @NotBlank(message ="アカウントを入力してください" ,groups = {UserEdit.class, UserSignUp.class})
    @Pattern(regexp="^[a-z0-9]{6,20}$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = {UserEdit.class, UserSignUp.class})
    private String account;
    @NotBlank(message ="パスワードを入力してください", groups = {UserSignUp.class})
    @PasswordValidator(message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserEdit.class, UserSignUp.class})
    private String password;
    private String anotherPassword;
    private Short systemId;
    private Short approverId;
    private Short isStopped;
    private Date createdDate;
    private Date updatedDate;
}
