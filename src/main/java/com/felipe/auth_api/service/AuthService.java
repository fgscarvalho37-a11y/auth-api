package com.felipe.auth_api.service;

import com.felipe.auth_api.dto.AuthDTO;
import com.felipe.auth_api.dto.UserDTO;
import com.felipe.auth_api.model.User;
import com.felipe.auth_api.repository.UserRepository;
import com.felipe.auth_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    public String register(UserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username já em uso");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já em uso");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of("USER"));

        userRepository.save(user);

        UserDetails userDetails = loadUserByUsername(dto.getUsername());
        return jwtService.generateToken(userDetails);
    }

    public String login(AuthDTO dto) {
        AuthenticationManager authManager = applicationContext.getBean(AuthenticationManager.class);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        UserDetails userDetails = loadUserByUsername(dto.getUsername());
        return jwtService.generateToken(userDetails);
    }
}