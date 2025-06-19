package ru.bikbaev.moneytransferapi.mapper;


import org.springframework.security.core.userdetails.UserDetails;
import ru.bikbaev.moneytransferapi.entity.User;

public interface UserMapper {

    UserDetails toUserDetails(User user);
}
