package org.ecommerce.system.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.system.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "gender", nullable = false)
    private Integer gender;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "status_user")
    private Integer statusUser;
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "CreatedAt")
    private LocalDateTime CreatedAt;

    @Column(name = "role")
    private Integer role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CartEntity cart;
}
