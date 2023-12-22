package com.ushan.authDemo.domains.entities;

import com.ushan.authDemo.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_entity_id_seq", sequenceName="user_entity_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_entity_id_seq" )
    private Long id;

    private String userName;
    private String password;
    private LocalDate dob;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
