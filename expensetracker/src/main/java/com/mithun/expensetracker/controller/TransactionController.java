package com.mithun.expensetracker.controller;

import com.mithun.expensetracker.entity.*;
import com.mithun.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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

    @PutMapping("update/{tranId}")
    public ResponseEntity<UserTransactionResponse> updateTransaction(@PathVariable Long tranId,@RequestBody UserTransactionRequest request, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(transactionService.updateTransaction(tranId,request,user),HttpStatus.OK);
    }

    @DeleteMapping("delete/{tranId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long tranId){
        return new ResponseEntity<>(transactionService.deleteTransaction(tranId),HttpStatus.OK);
    }

    @GetMapping("getUser")
    public ResponseEntity<UserResponse> getLoginUserDetails(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(transactionService.getLoginUserDetails(user),HttpStatus.OK);
    }

    @GetMapping("monthTran/{month}")
    public ResponseEntity<ReturnResponse> monthlyTransaction(@AuthenticationPrincipal User user,@PathVariable String month){
        return new ResponseEntity<>(transactionService.monthlyTransaction(user,month),HttpStatus.OK);
    }

    @GetMapping("getAllTranMonth/{month}")
    public ResponseEntity<List<UserTransactionResponse>> getAllTransactionByMonth(@AuthenticationPrincipal User user, @PathVariable String month){
        return  new ResponseEntity<>(transactionService.getAllTransactionByMonth(user,month),HttpStatus.OK);
    }

    @GetMapping("getTranById/{tranId}")
    public ResponseEntity<UserTransactionResponse> getTranById(@PathVariable Long tranId){
        return new ResponseEntity<>(transactionService.getTranById(tranId),HttpStatus.OK);
    }

}
