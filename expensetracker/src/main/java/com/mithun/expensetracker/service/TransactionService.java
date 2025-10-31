package com.mithun.expensetracker.service;

import com.mithun.expensetracker.entity.UserTransactionRequest;
import com.mithun.expensetracker.entity.UserTransactionResponse;
import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.exception.UnsufficientException;
import com.mithun.expensetracker.repo.TransactionRepo;
import com.mithun.expensetracker.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;

    @Transactional
    public UserTransactionResponse makeTransaction(@Valid UserTransactionRequest transaction, User userRequest) {
        try{
            User user = userRepo.findById(userRequest.getUserId()).orElseThrow(()-> new RuntimeException("User not Registered"));


                BigDecimal totalAmount = userRequest.getTotalAmount();
                    if (transaction.getType().equalsIgnoreCase("income"))
                        totalAmount = userRequest.getTotalAmount().add(transaction.getAmount());
                    else if (userRequest.getTotalAmount().compareTo(transaction.getAmount()) >= 0) {
                        totalAmount = userRequest.getTotalAmount().subtract(transaction.getAmount());
                    }else {
                        throw new UnsufficientException("Don't have sufficient Amount");
                    }
                    UserTransactionResponse newTransaction = new UserTransactionResponse(userRequest.getUserId(), transaction.getType(), transaction.getCategory(), transaction.getAmount(), transaction.getDescription(), getDateTime(), totalAmount);

                    this.updatingTransactionAmount(totalAmount,userRequest.getUserId());

                    return transactionRepo.save(newTransaction);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updatingTransactionAmount(BigDecimal totalAmount, Long userId) {
        userRepo.updatingTransactionAmount(totalAmount,userId);
    }

    private LocalDateTime getDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
    }

    public List<UserTransactionResponse> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<UserTransactionResponse> getUserTransaction(Long userId) {
         return transactionRepo.findByUserId(userId);
    }

    public String deleteTransaction(Long tranId) {
        try{
            Optional<UserTransactionResponse> tranData = transactionRepo.findById(tranId);
            if(tranData.isPresent()) {
                transactionRepo.deleteById(tranId);
                return "Transaction Deleted Successfully!! :TranId: "+ tranId;
            }else {
                return "Transaction doesn't exist with Id: " + tranId;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
