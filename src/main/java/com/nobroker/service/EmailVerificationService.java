package com.nobroker.service;

import com.nobroker.entity.User;
import com.nobroker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class EmailVerificationService {

    //This is static in nature so, we need to do static import of this object/variable.
    static final Map<String, String> emailOtpMapping = new HashMap<>();

    @Autowired
    private Userservice userservice;

    public Map<String, String> verifyOtp(String email, String otp) {//otp ending to the url
        String storedOtp = emailOtpMapping.get(email);
//        String storedOtp = emailOtpMapping.get(email);//it takes email and sends otp after sending to url

        Map<String, String> response = new HashMap<>();

        if (storedOtp != null && storedOtp.equals(otp)) {
            User user = userservice.getUserByEmail(email);
            if (user != null) {
                userservice.verifyEmail(user);
                response.put("status", "Success");
                response.put("message", "Email verified Sucessfully");
            } else {
                response.put("status", "error");
                response.put("message", "User not found");
            }
        }else{
            response.put("status", "error");
            response.put("message", "Invalid Otp");
        }
        return response;
    }
}
