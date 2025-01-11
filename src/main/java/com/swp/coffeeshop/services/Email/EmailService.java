package com.swp.coffeeshop.services.Email;

import com.swp.coffeeshop.dto.UserUpdateRequest;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.services.Role.RoleService;
import com.swp.coffeeshop.services.User.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    static RoleService roleService;
    static UserService userService;

    public EmailService(RoleService roleService, UserService userService) {
        EmailService.roleService = roleService;
        EmailService.userService = userService;
    }

    public static void changeProfile(UserUpdateRequest after) {

        final User before = userService.findById(after.getId());
        final String from = "duongminhson1601@gmail.com"; //email of sender
        final String passwordEmail = "govc qfcq hsbl igjc"; // password
        final String recipient = before.getEmail(); //email of reciver

        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");

        // get Session
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, passwordEmail);
            }
        };

        Session session = Session.getInstance(props, auth);

        //set up content of email
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject("Test");
            msg.setSentDate(new Date());
            msg.setContent("<DOCTYPE html>\r\n"
                    + "<html>\r\n"
                    + "<body>\r\n"
                    + "\r\n"
                    + "<h1>Chúng tôi đã thay đổi một số thông tin tài khoản của bạn</h1>"
                    + "<h2>Đây là thông tin hiện tại của bạn:</h2>"
                    + "<p>ID: " + before.getId() + "</p>"
                    + "<p>Tên đăng nhập: " + before.getUsername() + "</p>"
                    + (after.getPassword() == null ? "" : ("<p>Mật khẩu: " + after.getPassword() + "</p>"))
                    + (before.getFirstName().equals(after.getFirstName()) ? "" : ("<p>Tên: " + before.getFirstName() + " -> " + after.getFirstName() + "</p>"))
                    + (before.getLastName().equals(after.getLastName()) ? "" : ("<p>Họ: " + before.getLastName() + " -> " + after.getLastName() + "</p>"))
                    + (before.getEmail().equals(after.getEmail()) ? "" : ("<p>Email: " + before.getEmail() + " -> " + after.getEmail() + "</p>"))
                    + (before.getPhone().equals(after.getPhone()) ? "" : ("<p>Số điện thoại: " + before.getPhone() + " -> " + after.getPhone() + "</p>"))
                    + (before.getGender().equals(after.getGender()) ? "" : ("<p>Giới tính: " + before.getGender() + " -> " + after.getGender() + "</p>"))
                    + (before.getDob().equals(after.getDob()) ? "" : ("<p>Ngày sinh: " + before.getDob() + " -> " + after.getDob() + "</p>"))
                    + (before.getRole().getId().equals(after.getRoleId()) ? "" : ("<p>Vai trò: " + before.getRole() + " -> " + roleService.getRoleById(after.getRoleId()) + "</p>"))
                    + (Objects.equals(before.getActive(), after.getActive()) ? "" : ("<p>Trạng thái: " + (before.getActive() == 1 ? "Active" : "Inactive") + " -> " + (after.getActive() == 1 ? "Active" : "Inactive") + "</p>"))
                    + "<body>"
                    + "<html>", "text/html;charset=UTF-8");
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
