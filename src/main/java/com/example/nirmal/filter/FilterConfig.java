package com.example.nirmal.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoginFilter());
        //ログインフィルターが必要なURLテキストブロックに置換
        bean.addUrlPatterns("/nirmal/");//ホーム画面
        bean.addUrlPatterns("/changePassword");//パスワード変更画面
        bean.addUrlPatterns("/application");//承認者画面
        bean.addUrlPatterns("/userEdit/*");//ユーザー編集画面
        bean.addUrlPatterns("/userManage");//ユーザー管理画面
        bean.addUrlPatterns("/signUp");//ユーザー登録画面
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<SystemFilter> SystemFilter() {
        FilterRegistrationBean<SystemFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new SystemFilter());
        //システム管理者権限フィルターが必要なURLテキストブロックに置換
        bean.addUrlPatterns("/userEdit/*");//ユーザー編集画面
        bean.addUrlPatterns("/userManage");//ユーザー管理画面
        bean.addUrlPatterns("/signUp");//ユーザー登録画面
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ApproverFilter> ApproverFilter() {
        FilterRegistrationBean<ApproverFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new ApproverFilter());
        //承認者権限フィルターが必要なURLテキストブロックに置換
        bean.addUrlPatterns("/application");//承認者画面
        bean.setOrder(2);
        return bean;
    }
}