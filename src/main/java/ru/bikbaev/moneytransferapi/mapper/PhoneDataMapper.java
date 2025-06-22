package ru.bikbaev.moneytransferapi.mapper;

import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;

import java.util.List;

public interface PhoneDataMapper {

    UserPhoneResponse toDto(PhoneData phoneData);

    List<PhoneNumber> allToPhoneNumber(List<PhoneData> phoneData);

    List<PhoneNumberResponse> allToPhoneNumberResponse(List<PhoneData> phoneData);

}
