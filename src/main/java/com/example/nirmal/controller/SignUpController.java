package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SignUpController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    //新規ユーザー登録画面表示
    @GetMapping("/signUp")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = new UserForm();
        mav.addObject("userForm", userForm);
        mav.setViewName("/signUp");
        return mav;
    }

    //新規ユーザー登録処理
    @PostMapping("/signUp")
    public ModelAndView signUpUser(@ModelAttribute("formModel") @Validated({UserForm.UserSignUp.class}) UserForm userForm, BindingResult result)throws ParseException{
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            mav.addObject("errors", errorMessages);
            mav.addObject("userForm", userForm);
            mav.setViewName("/signUp");
            return mav;
        } else if(userService.checkAccount(userForm.getAccount(), userForm.getId())){
            errorMessages.add("アカウントが重複しています");
            mav.addObject("errors", errorMessages);
            mav.addObject("userForm", userForm);
            mav.setViewName("/signUp");
            return mav;
        } else if (!userForm.getPassword().equals(userForm.getAnotherPassword())) {
            errorMessages.add("パスワードと確認用が一致しません");
            mav.addObject("errors", errorMessages);
            mav.addObject("userForm", userForm);
            mav.setViewName("/signUp");
            return mav;
        }
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/userManage");
    }
}
