package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findByAccountAndPassword(String account, String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id ")
    public void savePassword(@Param("id") int id, @Param("password") String password );
    List<User> findAllByNameContainingAndSystemIdAndApproverIdOrderByIsStoppedAsc(String name, Integer systemId, Integer approverId);
    List<User> findAllByNameContainingAndApproverIdOrderByIsStoppedAsc(String name, Integer approverId);
    List<User> findAllByNameContainingAndSystemIdOrderByIsStoppedAsc(String name, Integer systemId);
    List<User> findAllByNameContainingOrderByIsStoppedAsc(String name);
    List<User> findAllBySystemIdAndApproverIdOrderByIsStoppedAsc(Integer systemId, Integer approverId);
    List<User> findAllByApproverIdOrderByIsStoppedAsc(Integer approverId);
    List<User> findAllBySystemIdOrderByIsStoppedAsc(Integer systemId);
    List<User> findAllByOrderByIsStoppedAsc();
    boolean existsByAccount(String account);
    boolean existsByAccountAndIdNot(String account, Integer id);

}
