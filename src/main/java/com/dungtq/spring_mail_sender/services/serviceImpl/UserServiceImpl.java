package com.dungtq.spring_mail_sender.services.serviceImpl;

import com.dungtq.spring_mail_sender.entity.User;
import com.dungtq.spring_mail_sender.repository.UserRepository;
import com.dungtq.spring_mail_sender.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public String register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnable(false);

        userRepository.save(user);
        return sendVerificationEmail(user, siteURL);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnable()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnable(true);
            userRepository.save(user);
            return true;
        }
    }

    private String sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "dungtqcode@gmail.com";
        String senderName = "DungTQ Dang Di Code";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        return user.getVerificationCode();
    }
}
