package com.springreact.backend.controller;

import com.springreact.backend.constant.Link;
import com.springreact.backend.constant.Message;
import com.springreact.backend.dto.request.ChangePassword;
import com.springreact.backend.dto.request.ResetPassword;
import com.springreact.backend.entity.Employee;
import com.springreact.backend.services.EmployeeServices;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * ForgotPasswordController
 * <p>
 * Version 1.0
 * <p>
 * Date: 30-05-2021
 * <p>
 * Copyright By Thanh
 * <p>
 * Modification Logs:
 * DATE             AUTHOR              DESCRIPTION
 * -------------------------------------------------
 * 07-06-2021       ThanhBT11           Create
 */
@CrossOrigin(origins = Link.REACT_URL)
@RestController
@RequestMapping(Link.BASE_URL)
public class ForgotPasswordController {

    private final static String SENDING_MAIL = "mailbesthelper2021@gmail.com";
    private final static String MAIL_SUBJECT = "Here's the link to reset your password";
    private final static String ERROR_SENDING_EMAIL = "Error while sending email!";
    private final static Boolean USING_HTML_TEXT = true;

    private final JavaMailSender mailSender;

    private final EmployeeServices employeeServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ForgotPasswordController constructor(JavaMailSender, EmployeeServices)
     *
     * @param mailSender       mailSender
     * @param employeeServices employeeServices
     */
    @Autowired
    public ForgotPasswordController(JavaMailSender mailSender,
                                    EmployeeServices employeeServices) {
        this.mailSender = mailSender;
        this.employeeServices = employeeServices;
    }

    /**
     * Sending an email contains a link and reset password token
     *
     * @param email receive link contains token
     * @return message sent mail
     */
    @PostMapping(Link.FORGOT_PASSWORD_EMAIL_PATH_URL)
    public ResponseEntity<String> processForgotPassword(@PathVariable String email) {
        String token = RandomString.make(30);

        try {
            //save reset token to database to check with token from email
            employeeServices.updateResetPasswordToken(token, email);
            //sending link to email contains reset password token
            String resetPasswordLink = Link.REACT_URL +
                    "/reset_password/" + token;
            sendEmail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            logger.error("An error occurred: " + ERROR_SENDING_EMAIL);
        }

        return new ResponseEntity<>(Message.SENT_EMAIL, HttpStatus.OK);
    }

    /**
     * Sending email process
     *
     * @param recipientEmail email to receive link and reset password token
     * @param link           contain frontend link and reset password token
     * @throws MessagingException           throw messaging exception
     * @throws UnsupportedEncodingException throw un support encoding exception
     */
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Sending mail from this email
        helper.setFrom(SENDING_MAIL,
                "Mail Support");
        //Send to this email
        helper.setTo(recipientEmail);

        //Content of email and frontend link contains reset password token
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(MAIL_SUBJECT);
        helper.setText(content, USING_HTML_TEXT);
        mailSender.send(message);
    }

    /**
     * Process reset password
     *
     * @param resetPassword contains new password and confirm password
     * @param token         to check reset password token in database
     * @return message if reset password success or not
     */
    @PostMapping(Link.RESET_PASSWORD_TOKEN_PATH_URL)
    public ResponseEntity<String> processResetPassword(
            @RequestBody ResetPassword resetPassword,
            @PathVariable String token) {

        //Get employee has reset password token
        Employee employee = employeeServices.getByResetPasswordToken(token);

        if (employee != null) {
            //Check if password equals to confirm password or not
            if (resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
                //If equals, update new password and return change password success message
                employeeServices.updatePassword(employee, resetPassword.getPassword());
                return new ResponseEntity<>(Message.CHANGE_PASSWORD_SUCCESS, HttpStatus.OK);
            } else {
                //Else return message confirm password must equal to password
                return new ResponseEntity<>(Message.VALID_CONFIRM_PASSWORD,
                        HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(Message.CHANGE_PASSWORD_FAIL, HttpStatus.BAD_REQUEST);
    }

    /**
     * Process change password
     *
     * @param changePassword contains old password, new password, and confirm password
     * @param email          to get current user by email
     * @return message if change password success or not
     */
    @PostMapping(Link.CHANGE_PASSWORD_EMAIL_PATH_URL)
    public ResponseEntity<String> processChangePassword(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email) {

        Employee employee = employeeServices.findEmployeeByEmail(email);

        //Check old password is valid or not
        if (!employeeServices.checkValidOldPassword(employee, changePassword.getOldPassword())) {
            return new ResponseEntity<>(Message.INCORRECT_OLD_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }

        //Check new password is equal to confirm password or not
        if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
            return new ResponseEntity<>(Message.VALID_CONFIRM_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }

        employeeServices.changeUserPassword(employee, changePassword.getNewPassword());
        return new ResponseEntity<>(Message.CHANGE_PASSWORD_SUCCESS, HttpStatus.OK);
    }

}









