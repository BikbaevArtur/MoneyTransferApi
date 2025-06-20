package ru.bikbaev.moneytransferapi.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.service.AuthUserService;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthUserService authUserService;
    private final UserMapper userMapper;

    public CustomUserDetailService(AuthUserService authUserService, UserMapper userMapper) {
        this.authUserService = authUserService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDate loginUserDate = authUserService.findByEmailOrPhone(username);
        return userMapper.toUserDetails(loginUserDate);
    }


}
