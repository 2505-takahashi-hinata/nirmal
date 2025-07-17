package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("/userEdit/{id}")
    public ModelAndView userManage(@PathVariable String id){
        ModelAndView mav = new ModelAndView();
        //URLチェック
        if(StringUtils.isBlank(id) || !id.matches("^[0-9]*$")){
            session.setAttribute("errors", "不正なパラメータが入力されました");
            return new ModelAndView("redirect:/userManage");
        }
        UserForm userForm = userService.editUser(Integer.parseInt(id));
        if(userForm == null){
            session.setAttribute("errors", "不正なパラメーターが入力されました");
            return new ModelAndView("redirect:/userManage");
        }
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        mav.addObject("loginUser",loginUser.getId());
        mav.addObject("user", userForm);
        mav.setViewName("/userEdit");
        return mav;
    }

    @PostMapping("/userUpdate/{id}")
    public ModelAndView userUpdate(@PathVariable Integer id,
                                   @ModelAttribute("user") @Validated({UserForm.UserEdit.class}) UserForm userForm, BindingResult result) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        int loginUserId = loginUser.getId();;
        userForm.setId(id);
        //エラーメッセージを表示
        if(result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            mav.addObject("errors", errorMessages);
            mav.addObject("loginUser",loginUser.getId());
            mav.setViewName("/userEdit");
            return mav;
        }
        //パスワードの一致確認
        if(!userForm.getPassword().equals(userForm.getAnotherPassword())) {
            errorMessages.add("パスワードと確認用が一致しません");
//            mav.addObject("loginUser",loginUser.getId());
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }
        //アカウント重複用
        if(userService.checkAccount(userForm.getAccount(), userForm.getId())){
            errorMessages.add("アカウントが重複しています");
            mav.addObject("loginUser",loginUserId);
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/userManage");
    }
}
