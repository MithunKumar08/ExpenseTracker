package com.mithun.expensetracker.repo;

import com.mithun.expensetracker.entity.UserTransactionResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<UserTransactionResponse,Long> {
    List<UserTransactionResponse> findByUserId(Long userId);
}
