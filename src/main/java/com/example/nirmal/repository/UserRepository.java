package com.example.nirmal.repository;

import com.example.nirmal.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    @Query("SELECT u.id,u.name,u.account,COUNT(a.id) " +
            "FROM User u LEFT JOIN Attendance a ON a.userId = u.id AND a.status = 1 " +
            "WHERE u.name LIKE %:name% " +
            "GROUP BY u.id, u.name, u.account " +
            "ORDER BY u.name ASC ")
    public List<Object[]> findNameApplication(@Param("name")String name);

    @Query("SELECT u.id,u.name,u.account,COUNT(a.id) " +
            "FROM User u LEFT JOIN Attendance a ON a.userId = u.id AND a.status = 1 " +
            "GROUP BY u.id, u.name, u.account " +
            "ORDER BY u.name ASC ")
    public List<Object[]> findApplication();

}
