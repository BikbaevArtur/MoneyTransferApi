package ru.bikbaev.moneytransferapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.service.AuthUserService;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.PhoneDataService;
import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessTokenResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
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
    public AccessTokenResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(), request.getPassword()
        ));

        LoginUserDate loginUserDate = findByEmailOrPhone(authentication.getName());

        UserDetails userDetails = userMapper.toUserDetails(loginUserDate);

        String token = jwtService.generateToken(userDetails, loginUserDate.getUser().getId());

        return new AccessTokenResponse(token);
    }


    @Override
    public LoginUserDate findByEmailOrPhone(String login) {

        if (login.contains("@")) {
            EmailData emailData = emailService.findByEmail(login);
            return new LoginUserDate(login, emailData.getUser());
        } else {
            PhoneData phoneData = phoneService.findByPhone(login);
            return new LoginUserDate(login, phoneData.getUser());
        }
    }
}
