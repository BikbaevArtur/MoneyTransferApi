package ru.bikbaev.moneytransferapi.mapper;


import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.response.PageResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.core.entity.User;

public interface UserMapper {

    UserResponseDto toDto(User user);

    PageResponse<UserResponseDto> toAllPageDto(Page<User> users);

    UserDetails toUserDetails(LoginUserDate loginUserDate);


}
