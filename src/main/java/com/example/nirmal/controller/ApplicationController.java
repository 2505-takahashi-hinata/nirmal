package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.dto.AllApplication;
import com.example.nirmal.dto.SelectApproval;
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
     *個人申請承認画面表示
     */
    @GetMapping({"/application/{id}","/application/","/application"})
    public ModelAndView application(@PathVariable(required = false) Integer id,
                                    @RequestParam(name = "start", required = false) LocalDate start,
                                    @RequestParam(name = "end", required = false) LocalDate end,
                                    @RequestParam(name = "status", required = false) Integer status,
                                    @RequestParam(name = "work_status", required = false) Integer work_status){

        ModelAndView mav = new ModelAndView();

        List<AllApplication> application = attendanceService.findAllApplication(id,start, end,status, work_status);

        //自分の申請は承認出来ない様にログインユーザのIdを送る
        //UserForm loginUser =(UserForm) session.getAttribute("loginUser");

        mav.setViewName("/application");
        mav.addObject("records",application);
        mav.addObject("id", id);
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("status",status);
        mav.addObject("work_status",work_status);

        //mav.addObject("loginUserId",loginUser.getId());
        return mav;
    }

    /*
     *申請ステータス変更処理
     */
    @PostMapping("/approval/{id}")
    public ModelAndView approvalApplication(@PathVariable(required = false)int id,
                                     @RequestParam(name = "changeStatus",required = false)int newStatus) {
        ModelAndView mav = new ModelAndView();

        attendanceService.saveStatus(id,newStatus);
        return new ModelAndView("redirect:/application");
    }

    /*
     *申請ステータス一括変更処理
     */
//    @PostMapping("/selectApproval")
//    public ModelAndView selectApproval(@RequestParam(name = "id",required = false)List<Integer> id,
//                                       @RequestParam(name = "changeStatus",required = false)List<Integer> newStatus) {
//        ModelAndView mav = new ModelAndView();
//
//        if(id != null){
//            for (Integer attendanceId : id) {
//                attendanceService.saveStatus(id, newStatus);
//            }
//        }
//
//        return new ModelAndView("redirect:/application");
//    }

    /*
     *勤怠申請処理
     */
    @PostMapping("/workApplication")
    public ModelAndView workApplication(@RequestParam(name = "id", required = false) List<Integer> id) {
        //ステータスを"申請中"に指定
        int status1 = 1;

        if (id != null){
            for (Integer attendanceId : id) {
                // 各IDに対応する申請ステータスを1に更新する
                attendanceService.saveStatus(attendanceId,status1);
            }

        }
        return new ModelAndView("redirect:/nirmal/");
    }

    @PostMapping("/approvalBundle")
    public ModelAndView approvalBundle(@RequestParam(name = "id", required = false) List<Integer> id) {
        //ステータスを"申請中"に指定
        int status2 = 2;

        if (id != null){
            for (Integer attendanceId : id) {
                // 各IDに対応する申請ステータスを2に更新する
                attendanceService.saveStatus(attendanceId,status2);
            }
        }
        return new ModelAndView("redirect:/application");
    }
}
