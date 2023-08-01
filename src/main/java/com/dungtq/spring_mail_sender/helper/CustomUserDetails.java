package com.dungtq.spring_mail_sender.helper;

import com.dungtq.spring_mail_sender.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }
}
