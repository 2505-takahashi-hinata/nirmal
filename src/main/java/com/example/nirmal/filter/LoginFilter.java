package com.example.nirmal.filter;

import com.example.nirmal.controller.form.UserForm;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse,
                         FilterChain chain) throws IOException, ServletException {

        //引数で渡された型の変換
        HttpServletRequest request = (HttpServletRequest)httpRequest;
        HttpServletResponse response = (HttpServletResponse)httpResponse;

        List<String> errorMessages = new ArrayList<>();
        //ログインチェック
        HttpSession session = request.getSession();
        UserForm user = (UserForm) session.getAttribute("loginUser");
        if (user != null){
            chain.doFilter(request, response);
        } else {
//            session.setAttribute("errors", "ログインしてください");
            errorMessages.add("ログインしてください");
            session.setAttribute("errors", errorMessages);
            response.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }
}
