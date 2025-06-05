package org.ecommerce.system.application.service.Implements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.infrastructure.EntityProperties;
import org.ecommerce.system.application.service.AuthenticationService;
import org.ecommerce.system.application.service.BaseRedisService;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.request.user.authentication.RegisterRequest;
import org.ecommerce.system.domain.response.user.authentication.AuthenticationResponse;
import org.ecommerce.system.domain.entity.UserEntity;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.exception.ApiException;
import org.ecommerce.system.exception.DuplicateResourceException;
import org.ecommerce.system.repository.UserRepository;
import org.ecommerce.system.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    //    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final BaseRedisService redisService;

    //    private final OtpService otpService;
    @Override
    public ResponseCommon<AuthenticationResponse> registerAccount(RegisterRequest request) {
        try {
            Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
            if (user.isPresent()) {
                throw new ApiException(ResponseCode.EMAIL_EXISTS);
            }
            UserEntity newUser = new UserEntity();
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setUsername(genUserFromEmail(request.getEmail()));
            String hassPass = passwordService.hashPassword(request.getPassword());
            newUser.setPassword(hassPass);
            newUser.setEmail(request.getEmail());
            newUser.setPhoneNumber(request.getPhoneNumber());
            newUser.setRole(request.getRole());
            newUser.setFullName(request.getFullName());
            newUser.setGender(request.getGender());
            newUser.setStatusUser(2);
            UserEntity savedCustomer = userRepository.save(newUser);
            String accessToken = jwtService.generateToken(savedCustomer);
            String refreshToken = jwtService.createRefreshToken(savedCustomer);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            return ResponseCommon.success(response);

        } catch (DuplicateResourceException e) {
            log.error("Error during registration: {}", e.getMessage());
            throw new ApiException(ResponseCode.EMAIL_EXISTS);
        } catch (Exception e) {
            log.error("Unexpected error during registration: {}", e.getMessage());
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String genUserFromEmail(String email) {
        String username = email.substring(0, email.indexOf("@"));
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Số ngẫu nhiên từ 0 đến 9
            randomNumber.append(digit);
        }
        String result = username + randomNumber.toString();
        return result;
    }

//    @Override
//    public AuthenticationResponse verify(String email, String otp) {
//
//        verifyOTP(email, otp);
//
//        User user = userRepository.findByEmail(email);
//
//        user.setVerified(true);
//        userRepository.save(user);
//
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//
//    }
//
//    @Override
//    public boolean verifyOTP(String email, String otpCode) {
//        Otp otp = otpRepository.findByEmail(email);
//
//        if (otp == null || otp.getOtp() == null) {
//            throw new InvalidOTPException("OTP đã được sử dụng hoặc đã hết hạn");
//        } else if (!otp.getOtp().equals(otpCode)) {
//            throw new InvalidOTPException("Mã OTP không hợp lệ");
//        } else if (otp.getExpirationTime().isBefore(LocalDateTime.now())) {
//            throw new InvalidOTPException("Mã OTP đã hết hạn");
//        } else {
//            otpRepository.delete(otp);
//            return true;
//        }
//    }
//
//
//    @Override
//    public void sendOtp(String email) {
//        otpService.deleteOtpByEmail(email);
//        otpService.generateAndSendOTP(email);
//    }
//
//    @Override
//    public boolean checkEmailExist(String email) {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new ResourceNotFoundException("Email không tồn tại");
//        }
//        return true;
//    }
//
//
//    @Override
//    public void changePassword(String otp, String email, String newPassword) {
//        log.info("Changing password with otp: {}, email: {}, newPassword: {}", otp, email, newPassword);
//
//        verifyOTP(email, otp);
//
//        User user = userRepository.findByEmail(email);
//        if (user != null) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//            userRepository.save(user);
//        }
//
//    }
//
//    @Override
//    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );
//
//            User user = userRepository.findByEmail(loginRequest.getEmail());
//            System.out.println(user.getStatus());
//
//            System.out.println(user.getVerified());
//
//            if (!user.getVerified()) {
//                throw new UserDisabledException("Email chưa được xác thực");
//            } else if (user.getStatus() == EnumUserStatus.INACTIVE) {
//                System.out.println("Tài khoản đã bị vô hiệu hóa");
//                throw new AccountLockedException("Tài khoản đã bị vô hiệu hóa");
//            }
//
//            String jwtToken = jwtService.generateToken(user);
//
//            return AuthenticationResponse.builder()
//                    .token(jwtToken)
//                    .build();
//        } catch (UserDisabledException | AccountLockedException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new InvalidCredentialsException("Email hoặc mật khẩu không hợp lệ");
//        }
//    }


}
