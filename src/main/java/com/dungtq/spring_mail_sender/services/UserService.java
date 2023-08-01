package com.dungtq.spring_mail_sender.services;

import com.dungtq.spring_mail_sender.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {
    public String register(User user, String siteURL)  throws UnsupportedEncodingException, MessagingException;

    public boolean verify(String verificationCode);
}
