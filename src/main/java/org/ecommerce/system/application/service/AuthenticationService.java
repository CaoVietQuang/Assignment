package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.request.user.authentication.RegisterRequest;
import org.ecommerce.system.domain.response.user.authentication.AuthenticationResponse;

public interface AuthenticationService {
    //    private final OtpService otpService;
    ResponseCommon<AuthenticationResponse> registerAccount(RegisterRequest request);

//    AuthenticationResponse authenticate(LoginRequest loginRequest);

//    AuthenticationResponse verify(String email, String otp);

//    boolean verifyOTP(String email, String otpCode);

//    void sendOtp(String email);

//    boolean checkEmailExist(String email);

//    void changePassword(String otp, String email, String newPassword);
}
