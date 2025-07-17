package com.example.nirmal.service;

import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.repository.UserRepository;
import com.example.nirmal.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //ユーザー情報全件取得
    public List<UserForm> findAllUser() {
        //稼働ユーザーが上にくるようにする
        List<User> results = userRepository.findAllByOrderByIsStoppedAsc();
        return setUserForm(results);
    }

    //稼働・停止ステータス変更
    public void updateIsStopped(int id, Short isStopped) {
        User user = userRepository.findById(id).get();
        user.setIsStopped(isStopped);
        userRepository.save(user);
    }

    //ユーザー登録・編集
    public void saveUser(UserForm userForm) throws ParseException{
        //encryptメソッドでパスワード暗号化したものをuserFormにセット
        if(userForm.getPassword() != null){
            String password = encrypt(userForm.getPassword());
            userForm.setPassword(password);
        }
        User user = setUserEntity(userForm);
        userRepository.save(user);
    }

    //編集ユーザー情報取得
    public UserForm editUser(int id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        List<User> results = new ArrayList<>();
        results.add(user);
        List<UserForm> users = setUserForm(results);
        return users.get(0);
    }
    public boolean checkAccount(String account, Integer id) {
        if(id == null) {
            return userRepository.existsByAccount(account);
        } else {
            return  userRepository.existsByAccountAndIdNot(account, id);
        }
    }

    //Entity→Formにつめかえ
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setName(result.getName());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setSystemId(result.getSystemId());
            user.setApproverId(result.getApproverId());
            user.setIsStopped(result.getIsStopped());
            users.add(user);
        }
        return users;
    }
    //Form→entityにつめかえ
    private User setUserEntity(UserForm reqUser) throws ParseException {
        User user = new User();
        user.setId(reqUser.getId());
        user.setName(reqUser.getName());
        user.setAccount(reqUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setSystemId(reqUser.getSystemId());
        user.setApproverId(reqUser.getApproverId());
        user.setIsStopped(reqUser.getIsStopped());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }

    //パスワード暗号化のメソッド
    //SHA-256で暗号化し、バイト配列をBase64エンコーディング。暗号化された文字列をreturn
    //（単純に復号化できない｢SHA-256」=ハッシュアルゴリズムを利用。バイト配列より文字列の方が扱いやすいため、 Base64でエンコードを行う）
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
