package com.pratikbhattacharjee.springjwtauth.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "api_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getPassword() {
        // Implement the logic for getting the user's password.
        return password;
    }

    @Override
    public boolean isEnabled() {
        // Implement the logic for determining if the user is enabled.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement the logic for determining if the user's credentials are non-expired.
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Implement the logic for getting the user's authorities.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement the logic for determining if the user's account is non-expired.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement the logic for determining if the user's account is non-locked.
        return true;
    }
}
