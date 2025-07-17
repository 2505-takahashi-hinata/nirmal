package com.example.nirmal.service;


import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.repository.UserRepository;
import com.example.nirmal.repository.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;



    public UserForm findUser(UserForm loginUser){
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
        //passwordハッシュ化前
        Optional<User> reqUser = userRepository.findByAccountAndPassword(account,password);
        //passwordハッシュ化後
        //String encryptPassword = encrypt(password);
        //Optional<User> reqUser = userRepository.findByAccountAndPassword(account,encryptPassword);
        if(reqUser.isEmpty()){
            return  null;
        }
        List<User> results = new ArrayList<>();
        results.add(reqUser.get());
        List<UserForm> userForms = setUserForm(results);
        return userForms.get(0);
    }

    private List<UserForm> setUserForm(List<User> results){
        List<UserForm> users = new ArrayList<>();
        for (User value : results) {
            UserForm user = new UserForm();
            user.setId(value.getId());
            user.setName(value.getName());
            user.setAccount(value.getAccount());
            user.setPassword(value.getPassword());
            user.setSystemId(value.getSystemId());
            user.setApproverId(value.getApproverId());
            user.setIsStopped(value.getIsStopped());
            user.setCreatedDate(value.getCreatedDate());
            user.setUpdatedDate(value.getUpdatedDate());
            users.add(user);
        }
        return users;
    }

    /*
     * パスワード変更処理
     */
    public void savePassword(String newPassword,int id) {
        //パスワードをハッシュ化
        String encryptPassword = encrypt(newPassword);
        //ユーザー情報を登録/更新
        userRepository.savePassword(id,encryptPassword);
    }


    public String encrypt(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Password.getBytes());
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
