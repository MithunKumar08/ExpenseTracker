package com.mithun.expensetracker.service;

import com.mithun.expensetracker.entity.UserTransaction;
import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.repo.TransactionRepo;
import com.mithun.expensetracker.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;

    public UserTransaction makeTransaction(@Valid UserTransaction transaction, User userRequest) {
        try{
            User user = userRepo.findById(userRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not Registered"));

            try {
                BigDecimal totalAmount = userRequest.getTotalAmount();
                    if (transaction.getType().equalsIgnoreCase("income"))
                        totalAmount = userRequest.getTotalAmount().add(transaction.getAmount());
                    else if (userRequest.getTotalAmount().compareTo(transaction.getAmount()) >= 0) {
                        totalAmount = userRequest.getTotalAmount().subtract(transaction.getAmount());
                    }
                    UserTransaction newTransaction = new UserTransaction(transaction.getTransactionId(), userRequest.getUserId(), transaction.getType(), transaction.getCategory(), transaction.getAmount(), transaction.getDescription(), getDateTime(), totalAmount);
                    return transactionRepo.save(newTransaction);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
    }

    public List<UserTransaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<UserTransaction> getUserTransaction(Long userId) {
         return transactionRepo.findByUserId(userId);
    }
}
