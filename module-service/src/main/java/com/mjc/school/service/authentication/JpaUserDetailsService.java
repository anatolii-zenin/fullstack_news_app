package com.mjc.school.service.authentication;

import com.mjc.school.repository.authentication.RoleRepository;
import com.mjc.school.repository.authentication.UserRepository;
import com.mjc.school.repository.authentication.model.RoleEntity;
import com.mjc.school.repository.authentication.model.UserEntity;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public JpaUserDetailsService(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    @Transactional
    public void createRole(String roleName) {
        var newRole = new RoleEntity();
        newRole.setName(roleName);
        roleRepository.save(newRole);
    }

    @Transactional
    public void createUser(UserDetails user) {
        var newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        var roles = new ArrayList<RoleEntity>();
        for (var authority : user.getAuthorities())
            roles.add(roleRepository.findByName(authority.getAuthority())
                    .orElseThrow(() -> new NotFoundException("no such role: " + authority.getAuthority())));
        newUser.setRoles(roles);
        userRepository.save(newUser);
    }
}
