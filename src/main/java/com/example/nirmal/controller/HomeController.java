package com.example.nirmal.controller;


import com.example.nirmal.controller.form.AttendanceForm;
import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.dto.workCalendar;
import com.example.nirmal.service.HomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    HomeService homeService;

    @Autowired
    HttpSession session;

    @GetMapping("/nirmal/")
    public ModelAndView home(@RequestParam(name = "start", required = false) LocalDate start,
                             @RequestParam(name = "end", required = false)LocalDate end) throws ParseException {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");

        int loginUserId = loginUser.getId();

        List<workCalendar> workData = homeService.findAllAttendance(loginUserId,start ,end);

        mav.setViewName("/home");
        mav.addObject("loginUser", loginUserId);
        mav.addObject("workData", workData);
        //エラーをsessionから取得
        List<String> errors = (List<String>) session.getAttribute("errors");
        if (errors != null) {
            mav.addObject("errors", errors);
            session.removeAttribute("errors");
        }
        return mav;
    }


    @PostMapping("/submit")
    public ModelAndView addComment(@ModelAttribute("work") @Validated AttendanceForm attendance, BindingResult result,
                                   @RequestParam(name = "start", required = false) LocalDate start,
                                   @RequestParam(name = "end", required = false)LocalDate end) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if(homeService.checkDate(attendance.getWorkDate())) {
            errorMessages.add("既に登録されている日程です");
            mav.addObject("errors", errorMessages);
            UserForm loginUser = (UserForm) session.getAttribute("loginUser");
            int loginUserId = loginUser.getId();
            List<workCalendar> workData = homeService.findAllAttendance(loginUserId,start ,end);

            mav.addObject("loginUser", loginUserId);
            mav.addObject("workData", workData);
            mav.setViewName("/home");
            return mav;
        }

        if(result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            UserForm loginUser = (UserForm) session.getAttribute("loginUser");
            int loginUserId = loginUser.getId();
            List<workCalendar> workData = homeService.findAllAttendance(loginUserId,start ,end);

            mav.addObject("errors", errorMessages);
            mav.addObject("loginUser", loginUserId);
            mav.addObject("workData", workData);
            mav.setViewName("/home");
            return mav;
        }
        // コメントをテーブルに格納
        homeService.saveAttendance(attendance);
        // rootへリダイレクト
        return new ModelAndView("redirect:/nirmal/");
    }

    @DeleteMapping("/updateWork/{id}")
    public ModelAndView deleteWork(@PathVariable Integer id) {
        homeService.deleteWork(id);
        return new ModelAndView("redirect:/nirmal/");
    }
}
