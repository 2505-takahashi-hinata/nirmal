package com.example.nirmal.service;

import org.springframework.util.CollectionUtils;
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

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;




    public UserForm findUser(UserForm loginUser){
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
        //passwordハッシュ化前
//        Optional<User> reqUser = userRepository.findByAccountAndPassword(account,password);
        //passwordハッシュ化後
        String encryptPassword = encrypt(password);
        Optional<User> reqUser = userRepository.findByAccountAndPassword(account,encryptPassword);
        if(reqUser.isEmpty()){
            return  null;
        }
        List<User> results = new ArrayList<>();
        results.add(reqUser.get());
        List<UserForm> userForms = setUserForm(results);
        return userForms.get(0);
    }

   
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
        if(!userForm.getPassword().isEmpty()) {
            String password = encrypt(userForm.getPassword());
            userForm.setPassword(password);
            User user = setUserEntity(userForm);
        }
        User user = setUserEntity(userForm);

        //編集時パスワード未入力の場合
        if(userForm.getPassword().isEmpty()){
            User existingUser = userRepository.findById(userForm.getId()).orElse(null);
            user.setPassword(existingUser.getPassword());
        }
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
    
    

    //Entity→Formにつめかえ
//     private List<UserForm> setUserForm(List<User> results) {
//         List<UserForm> users = new ArrayList<>();

//         for (int i = 0; i < results.size(); i++) {
//             UserForm user = new UserForm();
//             User result = results.get(i);
//             user.setId(result.getId());
//             user.setName(result.getName());
//             user.setAccount(result.getAccount());
//             user.setPassword(result.getPassword());
//             user.setSystemId(result.getSystemId());
//             user.setApproverId(result.getApproverId());
//             user.setIsStopped(result.getIsStopped());

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
