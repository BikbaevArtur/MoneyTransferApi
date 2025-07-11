package ru.bikbaev.moneytransferapi.mapper;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PageResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.core.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toDto(User user) {
        List<PhoneNumber> phones = user.getPhones().stream()
                .map(e -> new PhoneNumber(e.getPhone()))
                .collect(Collectors.toList());

        List<Email> emails = user.getEmails()
                .stream()
                .map(e -> new Email(e.getEmail()))
                .collect(Collectors.toList());

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .phones(phones)
                .emails(emails)
                .build();
    }

    @Override
    public PageResponse<UserResponseDto> toAllPageDto(Page<User> users) {
        List<UserResponseDto> userDtoList = users.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return PageResponse.<UserResponseDto>builder()
                .content(userDtoList)
                .pageNumber(users.getNumber())
                .pageSize(users.getSize())
                .last(users.isLast())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .numberOfElements(users.getNumberOfElements())
                .build();
    }

    @Override
    public UserDetails toUserDetails(LoginUserDate loginUserDate) {
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(loginUserDate.getLogin())
                .password(loginUserDate.getUser().getPassword())
                .authorities("ROLE_USER")
                .build();
    }
}
