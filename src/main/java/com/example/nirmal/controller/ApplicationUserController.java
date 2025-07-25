package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.dto.ApplicationUser;
import com.example.nirmal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ApplicationUserController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    /*
     *ユーザ一覧画面表示
     */
    @GetMapping("/applicationUser")
    public ModelAndView application(@RequestParam(name = "name", required = false) String name){

        ModelAndView mav = new ModelAndView();

        List<ApplicationUser> list = userService.findApplicationUser(name);

        //自分の申請は表示出来ない様にログインユーザのIdを送る
        UserForm loginUser =(UserForm) session.getAttribute("loginUser");

        mav.setViewName("/applicationUser");
        mav.addObject("lists",list);
        mav.addObject("name",name);
        mav.addObject("loginUserId",loginUser.getId());
        return mav;
    }

}
