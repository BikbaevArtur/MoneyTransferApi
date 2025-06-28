package ru.bikbaev.moneytransferapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.UserNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.request.PageableRequest;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.PageResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.mapper.UserMapper;
import ru.bikbaev.moneytransferapi.repository.UserRepository;
import ru.bikbaev.moneytransferapi.repository.spec.UserSpecification;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "users", key = "'findById' + '_' + #id")
    @Override
    public UserResponseDto findById(Long id) {
        return mapper.toDto(findEntityUserById(id));
    }


    @Cacheable(value = "users", key = "'getUserProfile' + '_' + #userId")
    @Override
    public UserResponseDto getUserProfile(Long userId) {
        return mapper.toDto(findEntityUserById(userId));
    }


    @Override
    public User findEntityUserById(Long id) {
        log.debug("Searching User by user_id={}", id);
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
    }

    @Cacheable(
            value = "users_search",
            key = "T(String).format('%s_%s_%s_%s_page=%s_size=%s',#params.dateOfBirthAfter,#params.phone,#params.email,#params.namePrefix,#pageable.pageNumber,#pageable.pageSize)"
    )
    @Override
    public PageResponse<UserResponseDto> searchUsers(UserParamsSearch params, PageableRequest pageable) {
        Specification<User> spec = UserSpecification.filter(params);
        Page<User> page = repository.findAll(spec, pageable.getPageable());
        return mapper.toAllPageDto(page);
    }
}
