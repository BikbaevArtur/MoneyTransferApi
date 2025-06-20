package ru.bikbaev.moneytransferapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.entity.User;
import ru.bikbaev.moneytransferapi.exception.UserNotFoundException;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.repository.UserRepository;
import ru.bikbaev.moneytransferapi.repository.spec.UserSpecification;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User findEntityUserById(Long id) {
        return repository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("User not found")
        );
    }

    @Override
    public Page<UserResponseDto> searchUsers(UserParamsSearch params, Pageable pageable) {
        Specification<User> spec = UserSpecification.filter(params);
        return mapper.toAllPageDto(repository.findAll(spec,pageable));
    }
}
