package ru.bikbaev.moneytransferapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.UserNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.repository.UserRepository;
import ru.bikbaev.moneytransferapi.repository.spec.UserSpecification;
import ru.bikbaev.moneytransferapi.security.JwtService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, JwtService jwtService) {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponseDto findById(Long id) {
        return mapper.toDto(findEntityUserById(id));
    }

    @Override
    public UserResponseDto getUserProfile(String token) {
        Long userId = jwtService.extractUserId(token);
        return findById(userId);
    }

    @Override
    public User findEntityUserById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
    }

    @Override
    public Page<UserResponseDto> searchUsers(UserParamsSearch params, Pageable pageable) {
        Specification<User> spec = UserSpecification.filter(params);
        return mapper.toAllPageDto(repository.findAll(spec, pageable));
    }
}
