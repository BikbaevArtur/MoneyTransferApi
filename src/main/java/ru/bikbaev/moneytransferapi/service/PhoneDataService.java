package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.dto.request.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.entity.PhoneData;

public interface PhoneDataService {

    PhoneData findByPhone(String phone);

    UserPhoneResponse addPhoneNumber(String token,PhoneNumber phoneNumber);

    UserPhoneResponse updatePhoneNumber(String token,Long idPhoneNumber, PhoneNumber newPhoneNumber);

    void deletePhoneNumber(String token,Long idPhoneNumber);

}
