package org.ecommerce.system.domain.response.user.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;
    private Integer gender;
    private String genderDescription;
    private String address;
    private String dateOfBirth;
    private Integer status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Integer role;
    private String roleDescription;
}
