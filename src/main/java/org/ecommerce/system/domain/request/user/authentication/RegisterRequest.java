package org.ecommerce.system.domain.request.user.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer role;
    private Integer gender;
}
