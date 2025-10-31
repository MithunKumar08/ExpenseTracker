package com.mithun.expensetracker.controller;

import com.mithun.expensetracker.entity.UserTransactionRequest;
import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.entity.UserTransactionResponse;
import com.mithun.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("add")
    public ResponseEntity<UserTransactionResponse> makeTransaction(@RequestBody @Valid UserTransactionRequest transaction, @AuthenticationPrincipal User user){
        try {
            return new ResponseEntity<>(transactionService.makeTransaction(transaction, user), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getAllTransaction")
    public ResponseEntity<List<UserTransactionResponse>> getAllTransactions(@AuthenticationPrincipal User user){
        System.out.println(user.getUsername());
        return new ResponseEntity<>(transactionService.getAllTransactions(),HttpStatus.OK);
    }

    @GetMapping("getUserTransaction")
    public ResponseEntity<List<UserTransactionResponse>> getUserTransaction(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(transactionService.getUserTransaction(user.getUserId()),HttpStatus.OK);
    }

//    @PutMapping("update")
//    public ResponseEntity<UserTransactionResponse> updateTransaction(UserTransactionRequest request, @AuthenticationPrincipal User user) {
//
//    }

    @DeleteMapping("delete/{tranId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long tranId){
        return new ResponseEntity<>(transactionService.deleteTransaction(tranId),HttpStatus.OK);
    }

}
