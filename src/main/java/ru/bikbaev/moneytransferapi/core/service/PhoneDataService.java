package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

import java.util.List;

public interface PhoneDataService {

    PhoneData findByPhone(String phone);

    List<PhoneNumber> findAllPhoneNumberByIdUser(Long idUser);

    List<PhoneNumberResponse> getPhoneNumberUser(String token);

    UserPhoneResponse addPhoneNumber(String token,PhoneNumber phoneNumber);

    UserPhoneResponse updatePhoneNumber(String token,Long idPhoneNumber, PhoneNumber newPhoneNumber);

    void deletePhoneNumber(String token,Long idPhoneNumber);

}
