package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByOrderByIsStoppedAsc();
    boolean existsByAccount(String account);
    boolean existsByAccountAndIdNot(String account, Integer id);
}
