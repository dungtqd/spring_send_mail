package com.dungtq.spring_mail_sender.controller;

import com.dungtq.spring_mail_sender.entity.User;
import com.dungtq.spring_mail_sender.services.UserService;
import jakarta.mail.Header;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String processRegister(@RequestBody User user)
            throws UnsupportedEncodingException, MessagingException {
        log.info("register");
        String header = "http://localhost:8081/api/user";
        log.info("header: " + header);
        String activeCode = service.register(user, header);
        String activeUrl = "http://localhost:8081/api/user/verify?code=" + activeCode;
        return activeUrl;
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (service.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
