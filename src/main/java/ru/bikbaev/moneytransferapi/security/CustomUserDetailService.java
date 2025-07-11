package ru.bikbaev.moneytransferapi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotFoundException;
import ru.bikbaev.moneytransferapi.core.exception.PhoneNotFoundException;
import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.core.service.AuthUserService;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthUserService authUserService;
    private final UserMapper userMapper;

    public CustomUserDetailService(AuthUserService authUserService, UserMapper userMapper) {
        this.authUserService = authUserService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        log.debug("Attempting to load user by username: {}", username);
        try{
            LoginUserDate loginUserDate = authUserService.findByEmailOrPhone(username);
            log.debug("User found: id={}, login={}", loginUserDate.getUser().getId(), loginUserDate.getLogin());
            return userMapper.toUserDetails(loginUserDate);
        }catch (EmailNotFoundException | PhoneNotFoundException e){
            log.warn("User not found by username: {}", username);
            throw new UsernameNotFoundException(e.getMessage());
        }
    }


}
