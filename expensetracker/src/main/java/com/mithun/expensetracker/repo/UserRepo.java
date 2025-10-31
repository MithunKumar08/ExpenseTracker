package com.mithun.expensetracker.repo;

import com.mithun.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query("update User set totalAmount = :totalAmount where userId = :userId")
    void updatingTransactionAmount(@Param("totalAmount") BigDecimal totalAmount,@Param("userId") Long userId);
}
