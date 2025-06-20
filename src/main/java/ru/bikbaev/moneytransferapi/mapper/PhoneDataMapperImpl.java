package ru.bikbaev.moneytransferapi.mapper;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.entity.PhoneData;

@Component
public class PhoneDataMapperImpl implements PhoneDataMapper{

    @Override
    public UserPhoneResponse toDto(PhoneData phoneData) {
        return UserPhoneResponse.builder()
                .userId(phoneData.getUser().getId())
                .userName(phoneData.getUser().getName())
                .idPhone(phoneData.getId())
                .phone(phoneData.getPhone())
                .build();
    }
}
