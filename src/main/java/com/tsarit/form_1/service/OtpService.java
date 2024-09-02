package com.tsarit.form_1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tsarit.form_1.model.userData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;
    
    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private final Map<String, userData> tempUserDataMap = new HashMap<>();

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    public String sendOtp(String emailid) {
        String otp = generateOtp();
        System.out.println("otp sended successfuly");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailid);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        mailSender.send(message);

        otpMap.put(emailid, otp);
        return otp;
    }
    
    public void saveTemporaryUser(userData ud, String otp) {
        String normalizedEmail = ud.getEmailid().trim().toLowerCase(); // Normalize email
        System.out.println("saving  OTP for email: " + normalizedEmail);
        tempUserDataMap.put(normalizedEmail, ud); // Store the user data
        otpMap.put(normalizedEmail, otp); // Store the OTP
        System.out.println("Stored OTP: " + otp + " for email: " + normalizedEmail);
    }

    public boolean verifyOtp(String emailid, String otp) {
        String normalizedEmail = emailid.trim().toLowerCase(); // Normalize email
        System.out.println("Verifying OTP for email: " + normalizedEmail);
        String storedOtp = otpMap.get(normalizedEmail); // Retrieve stored OTP
        System.out.println("Stored OTP: " + storedOtp);
        System.out.println("Received OTP: " + otp);
        
        return storedOtp != null && storedOtp.equals(otp);
    }
    
    
    // Method to get the temporarily stored user data
    public userData getTemporaryUser(String emailid) {
        return tempUserDataMap.get(emailid); // Retrieve stored user data
    }

    // Optional: Clear temporary data after successful verification or expiration
    public void clearTemporaryData(String emailid) {
        tempUserDataMap.remove(emailid); // Remove user data
        otpMap.remove(emailid); // Remove OTP
    }
}

