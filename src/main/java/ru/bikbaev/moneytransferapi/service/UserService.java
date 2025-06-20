package ru.bikbaev.moneytransferapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.entity.User;

public interface UserService {

    User findEntityUserById(Long id);

    Page<UserResponseDto> searchUsers(UserParamsSearch params, Pageable pageable);


}
