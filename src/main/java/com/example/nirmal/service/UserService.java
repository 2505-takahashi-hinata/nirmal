package com.example.nirmal.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import com.example.nirmal.controller.form.UserForm;
import com.example.nirmal.repository.UserRepository;
import com.example.nirmal.repository.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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


    //ログイン処理
    public UserForm findUser(UserForm loginUser){
        String account = loginUser.getAccount();
        String password = loginUser.getPassword();
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

    //ユーザー一覧取得・Specificationを使用し動的クエリ作成
    public List<UserForm> findAllUser(String name, Integer systemId, Integer approverId) {
        List<User> results = new ArrayList<>();
        results = userRepository.findAll(
                Specification.where(nameContains(name))
                .and(systemIdContains(systemId))
                .and(approverIdContains(approverId)),
                Sort.by(Sort.Direction.ASC, "isStopped"));
        return setUserForm(results);
    }
    private Specification<User> nameContains(String name) {
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(name)){
                return null;
            }
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }
    private Specification<User> systemIdContains(Integer systemId) {
        return (root, query, builder) -> {
            if (systemId == null){
                return null;
            }
            return builder.equal(root.get("systemId"),systemId);
        };
    }
    private Specification<User> approverIdContains(Integer approverId) {
        return (root, query, builder) -> {
            if (approverId == null){
                return null;
            }
            return builder.equal(root.get("approverId"),approverId);
        };
    }
}
