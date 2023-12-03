package com.mjc.school.controller.authentication;

import com.mjc.school.service.authentication.JpaUserDetailsService;
import com.mjc.school.service.authentication.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagement {
    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/api/signup")
    @PreAuthorize("hasRole('ANONYMOUS')")
    public String createUser(@RequestBody UserRequest userRequest) {
        var user = User.withUsername(userRequest.getUsername())
                .password(userRequest.getPassword())
                .passwordEncoder(str -> passwordEncoder.encode(str))
                .roles("USER")
                .build();

        jpaUserDetailsService.createUser(user);
        return "success";
    }
}
