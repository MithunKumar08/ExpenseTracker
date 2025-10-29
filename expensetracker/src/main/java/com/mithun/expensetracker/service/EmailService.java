//package com.mithun.expensetracker.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendMail(String toEmail,String userName){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("mithunkumarmysore08@gmail.com");
//        message.setTo(toEmail);
//        message.setSubject("Test Mail");
//        message.setText("Thank "+ userName + " for Registering");
//
//        javaMailSender.send(message);
//    }
//}
