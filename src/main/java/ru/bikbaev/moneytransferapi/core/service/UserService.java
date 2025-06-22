package ru.bikbaev.moneytransferapi.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.core.entity.User;

public interface UserService {

    UserResponseDto findById(Long id);

    UserResponseDto getUserProfile(String token);

    User findEntityUserById(Long id);

    Page<UserResponseDto> searchUsers(UserParamsSearch params, Pageable pageable);


}
