package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    public Optional<User> findByAccountAndPassword(String account, String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id ")
    void savePassword(@Param("id") int id, @Param("password") String password );
    boolean existsByAccount(String account);
    boolean existsByAccountAndIdNot(String account, Integer id);

}
