package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.dto.AllApplication;
import com.example.nirmal.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    HttpSession session;


    /*
     *申請承認画面表示
     */
    @GetMapping("/application")
    public ModelAndView application(@RequestParam(name = "start", required = false) LocalDate start,
                                    @RequestParam(name = "end", required = false) LocalDate end,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "work_status", required = false) Integer work_status){

        ModelAndView mav = new ModelAndView();

        List<AllApplication> application = attendanceService.findAllApplication(start, end, name, work_status);

        //自分の申請は承認出来ない様にログインユーザのIdを送る
//        UserForm loginUser =(UserForm) session.getAttribute("loginUser");

        mav.setViewName("/application");
        mav.addObject("records",application);
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("name",name);
        mav.addObject("work_status",work_status);
//        mav.addObject("loginUserId",loginUser.getId());
        return mav;
    }

    /*
     *申請ステータス変更処理
     */
    @PostMapping("/approval/{id}")
    public ModelAndView editPassword(@PathVariable(required = false)int id,
                                     @RequestParam(name = "changeStatus",required = false)int newStatus) {
        ModelAndView mav = new ModelAndView();

        attendanceService.saveStatus(id,newStatus);
        return new ModelAndView("redirect:/application");
    }
}
