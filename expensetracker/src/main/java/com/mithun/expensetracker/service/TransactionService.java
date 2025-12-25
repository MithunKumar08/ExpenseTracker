package com.mithun.expensetracker.service;

import com.mithun.expensetracker.entity.*;
import com.mithun.expensetracker.exception.NotFoundException;
import com.mithun.expensetracker.exception.UnsufficientException;
import com.mithun.expensetracker.repo.TransactionRepo;
import com.mithun.expensetracker.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
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
                    UserTransactionResponse newTransaction = new UserTransactionResponse(userRequest.getUserId(), transaction.getType(), transaction.getCategory(), transaction.getAmount(), transaction.getDescription(), transaction.getDate(), totalAmount);

                    this.updatingTransactionAmount(totalAmount,userRequest.getUserId());

                    return transactionRepo.save(newTransaction);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updatingTransactionAmount(BigDecimal totalAmount, Long userId) {
        userRepo.updatingTransactionAmount(totalAmount,userId);
    }

//    private LocalDateTime getDateTime() {
//        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
//    }

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

    @Transactional
    public UserTransactionResponse updateTransaction(Long tranId, UserTransactionRequest request, User user) {
        try {
            Optional<UserTransactionResponse> existingDataOpt = transactionRepo.findById(tranId);
            if (existingDataOpt.isPresent()) {
                UserTransactionResponse existingData = existingDataOpt.get();

                BigDecimal totalAmount = user.getTotalAmount();

                // Undo old transaction effect
                if (existingData.getType().equalsIgnoreCase("income")) {
                    totalAmount = totalAmount.subtract(existingData.getAmount());
                } else {
                    totalAmount = totalAmount.add(existingData.getAmount());
                }

                // Apply new transaction effect
                if (request.getType().equalsIgnoreCase("income")) {
                    totalAmount = totalAmount.add(request.getAmount());
                } else if (totalAmount.compareTo(request.getAmount()) >= 0) {
                    totalAmount = totalAmount.subtract(request.getAmount());
                } else {
                    throw new UnsufficientException("Don't have sufficient Amount");
                }

                // Update transaction fields
                existingData.setType(request.getType());
                existingData.setCategory(request.getCategory());
                existingData.setAmount(request.getAmount());
                existingData.setDescription(request.getDescription());
                existingData.setDate(request.getDate());
                existingData.setTotalAmount(totalAmount);

                // Update user balance
                user.setTotalAmount(totalAmount);
                updatingTransactionAmount(totalAmount, user.getUserId());

                return transactionRepo.save(existingData);
            } else {
                throw new NotFoundException("Transaction not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating transaction", e);
        }
    }

    public UserResponse getLoginUserDetails(User user) {
        try{
            Optional<User> fetchData = userRepo.findByUserId(user.getUserId());
            if(fetchData.isPresent()){
                User data = fetchData.get();
                return new UserResponse(data.getUserId(),data.getUsername(),data.getEmail(),data.getTotalAmount());
            }else throw new NotFoundException("User does Not Exist");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Kolkata")
    public void resetTotalAmountOnEveryMonth(){
        try{
            List<User> allUsers = userRepo.findAll();
            for(User users : allUsers){
                users.setTotalAmount(BigDecimal.ZERO);
            }
            userRepo.saveAll(allUsers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ReturnResponse monthlyTransaction(User user, String month) {
        List<UserTransactionResponse> data = transactionRepo.findTranByMonth(user.getUserId(),month);
        BigDecimal totalIncome = BigDecimal.ZERO,totalExpense = BigDecimal.ZERO,remaining=BigDecimal.ZERO;

        try{
            for(UserTransactionResponse datas : data){
                if(datas.getType().equalsIgnoreCase("income")){
                    totalIncome = totalIncome.add(datas.getAmount());
                }else totalExpense = totalExpense.add(datas.getAmount());

                remaining = totalIncome.subtract(totalExpense);
            }
            return new ReturnResponse(totalIncome,totalExpense,remaining);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserTransactionResponse> getAllTransactionByMonth(User user, String month) {
        return transactionRepo.findTranByMonth(user.getUserId(),month);
    }

    public UserTransactionResponse getTranById(Long tranID) {
        try{
            Optional<UserTransactionResponse> response = transactionRepo.findById(tranID);
            if(response.isPresent()) return response.get();
            else throw new NotFoundException("Transaction with Id: "+tranID + " not found");
        } catch (Exception e) {
            throw new NotFoundException("Transaction with Id: "+tranID + " not found");
        }
    }
}
