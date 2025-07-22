package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.service.UserService;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("/userManage")
    public ModelAndView userManage(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "systemId", required = false) Integer systemId, @RequestParam(name = "approverId", required = false) Integer approverId){
        ModelAndView mav = new ModelAndView();
        List<UserForm> users = userService.findAllUser(name, systemId, approverId);
        mav.addObject("name",name);
        mav.addObject("systemId",systemId);
        mav.addObject("approverId",approverId);
        mav.addObject("users",users);
        mav.addObject("loginUser",session.getAttribute("loginUser"));
        mav.setViewName("/userManage");
        return mav;
    }

    //ステータス変更
    @PutMapping("/updateIsStopped/{id}")
    public ModelAndView updateIsStopped(@PathVariable Integer id, @RequestParam(name = "isStopped", required = false) Short isStopped){
        userService.updateIsStopped(id, isStopped);
        return new ModelAndView("redirect:/userManage");
    }
}

