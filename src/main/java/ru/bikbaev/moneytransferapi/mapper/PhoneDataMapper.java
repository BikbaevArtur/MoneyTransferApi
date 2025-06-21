package ru.bikbaev.moneytransferapi.mapper;

import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

public interface PhoneDataMapper {

    UserPhoneResponse toDto(PhoneData phoneData);

}
