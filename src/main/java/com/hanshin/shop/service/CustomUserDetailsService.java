package com.hanshin.shop.service;

import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userMapper.findByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("%s -> 데이터베이스에서 찾을 수 없습니다.", username));
        }
        return createUser(user);
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {

        List<GrantedAuthority> grantedAuthorities = user.getUserRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().name()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}
