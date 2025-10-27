package com.mithun.expensetracker.repo;

import com.mithun.expensetracker.entity.UserTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<UserTransaction,Long> {
    List<UserTransaction> findByUserId(Long userId);
}
