package com.example.nirmal.controller;


import com.example.nirmal.controller.form.AttendanceForm;
import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.dto.workCalendar;
import com.example.nirmal.service.HomeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
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
        mav.addObject("MapYear", MapYear());
        mav.addObject("MapMonth", MapMonth());
        mav.addObject("workData", workData);
        return mav;
    }

    private Map<Integer, String> MapYear() {
        Map<Integer, String> map = new HashMap<>();
        map.put(null, "選択");
        map.put(2025, "2025年");
        map.put(2026, "2026年");
        map.put(2027, "2027年");
        return map;
    }

    private Map<Integer, String>MapMonth(){
        Map<Integer, String> map = new HashMap<>();
        map.put(null, "選択");
        map.put(1, "1月");
        map.put(2, "2月");
        map.put(3, "3月");
        map.put(4, "4月");
        map.put(5, "5月");
        map.put(6, "6月");
        map.put(7, "7月");
        map.put(8, "8月");
        map.put(9, "9月");
        map.put(10, "10月");
        map.put(11, "11月");
        map.put(12, "12月");
        return map;
    }

    @PostMapping("/submit")
    public ModelAndView addComment(@ModelAttribute("work")AttendanceForm attendance) throws ParseException {
        ModelAndView mav = new ModelAndView();
//        if(result.hasErrors()) {
//        }
        // コメントをテーブルに格納
        homeService.saveAttendance(attendance);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}
