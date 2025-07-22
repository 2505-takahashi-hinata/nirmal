package com.example.nirmal.controller;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.groups.ChangePasswordGroup;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChangePasswordController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     *パスワード変更画面表示処理
     */
    @GetMapping({"/changePassword"})
    public ModelAndView changePassword() {

        ModelAndView mav = new ModelAndView();
        UserForm password = new UserForm();
        //エラーメッセージを入れる用のリストを作っておく
        List<String> errorMessages = new ArrayList<String>();
        mav.addObject("password", password);
        mav.setViewName("/changePassword");
        return mav;
    }

    /*
     *パスワード変更処理
     */
    @PutMapping("/changePasswordProcess")
    public ModelAndView editPassword(@Validated({ChangePasswordGroup.class}) @ModelAttribute("password") UserForm password, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //エラーメッセージ用のリスト作成
        List<String> errorMessages = new ArrayList<String>();
        //必須チェック
        if (result.hasErrors()) {
            //要素を一つずつ取り出して、エラーの数だけリストに詰める
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
                //エラーメッセージが詰まったリストをviewに送る
            }
            mav.addObject("errors", errorMessages);
            mav.setViewName("/changePassword");
            return mav;
        }
        //パスワードの入力チェック
        String newPassword = password.getPassword();
        if (!StringUtils.isEmpty(newPassword) &&
                ((newPassword.length() < 6 || newPassword.length() > 20) || !newPassword.matches("^[!-~]+$"))) {
            errorMessages.add("パスワードは半角文字かつ6文字以上20文字以下で入力してください");

            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errors", errorMessages);
            mav.setViewName("/changePassword");
            return mav;
        }

        //妥当性チェック①パスワードと確認用パスワードが異なる時にエラーメッセージ

        if (!password.getPassword().equals(password.getAnotherPassword())) {
            errorMessages.add("パスワードと確認用パスワードが一致しません");

            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errors", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/changePassword");
            return mav;
        }
        UserForm loginUser =(UserForm) session.getAttribute("loginUser");
        userService.savePassword(newPassword, loginUser.getId());

        return new ModelAndView("redirect:/nirmal/");
    }
}