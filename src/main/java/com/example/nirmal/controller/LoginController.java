package com.example.nirmal.controller;


import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.groups.LoginGroup;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    /*
     *ログイン画面表示処理
     */
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = new UserForm();

        if(session.getAttribute("loginFilter") != null ) {
            String LoginFilter =(String)session.getAttribute("LoginFilter");
            List<String>errorMessages = new ArrayList<>();
            errorMessages.add(LoginFilter);
            mav.addObject("errorMessages",errorMessages);
            session.removeAttribute("loginFilter");
        }

        mav.addObject("loginUser",loginUser);
        mav.setViewName("/login");
        return mav;
    }

    /*
     *ログイン処理
     */
    @PostMapping("/loginProcess")
    public ModelAndView loginProcess(@Validated(LoginGroup.class) @ModelAttribute("loginUser")UserForm loginUser, BindingResult result){
        ModelAndView mav = new ModelAndView();
        //エラーメッセージ用のリスト作成
        List<String>errorMessages = new ArrayList<>();
        //必須チェック
        if(result.hasErrors()){
            //要素を一つずつ取り出して、エラーの数だけリストに詰める
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
                //エラーメッセージが詰まったリストをviewに送る
            }
            //エラーメッセージを詰めたリストを送る
            mav.addObject("errorMessages",errorMessages);
            //画面遷移先指定
            mav.setViewName("/login");
            return mav;
        }
        UserForm reqUser = userService.findUser(loginUser);

        if(reqUser == null || reqUser.getIsStopped()== 1){
            errorMessages.add("ログインに失敗しました");
            mav.addObject("errorMessages",errorMessages);
            //画面遷移先指定
            mav.setViewName("/login");
            return mav;
        }
        session.setAttribute("loginUser",reqUser);
        return new ModelAndView("redirect:/nirmal/");
    }

    /*
     * ログアウト機能
     */
    @PostMapping("/logout")
    public ModelAndView logoutContent() {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}
