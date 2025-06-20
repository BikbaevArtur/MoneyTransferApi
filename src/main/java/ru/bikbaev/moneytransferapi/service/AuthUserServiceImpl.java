package ru.bikbaev.moneytransferapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessToken;
import ru.bikbaev.moneytransferapi.entity.EmailData;
import ru.bikbaev.moneytransferapi.entity.PhoneData;
import ru.bikbaev.moneytransferapi.entity.User;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.security.JwtService;

@Service
public class AuthUserServiceImpl implements AuthUserService {


    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final EmailDataService emailService;
    private final PhoneDataService phoneService;

    public AuthUserServiceImpl(AuthenticationManager authenticationManager, UserMapper userMapper, JwtService jwtService, EmailDataService emailService, PhoneDataService phoneService) {
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    @Override
    public AccessToken authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(), request.getPassword()
        ));

        User user = findByEmailOrPhone(authentication.getName());

        UserDetails userDetails = userMapper.toUserDetails(user);

        String token = jwtService.generateToken(userDetails, user.getId());

        return new AccessToken(token);
    }


    @Override
    public User findByEmailOrPhone(String login) {

        if (login.contains("@")) {
            EmailData emailData = emailService.findByEmail(login);
            return emailData.getUser();
        } else {
            PhoneData phoneData = phoneService.findByPhone(login);
            return phoneData.getUser();
        }
    }
}
