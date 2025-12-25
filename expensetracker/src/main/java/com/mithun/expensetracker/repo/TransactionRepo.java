package com.mithun.expensetracker.repo;

import com.mithun.expensetracker.entity.UserTransactionResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<UserTransactionResponse,Long> {
    List<UserTransactionResponse> findByUserId(Long userId);

    @Query(value = "SELECT * from user_transaction_response " + "WHERE user_id = :userId AND MONTH(date) = :month",nativeQuery = true)
    List<UserTransactionResponse> findTranByMonth(@Param("userId") Long userId, @Param("month") String month);

}
