package ru.bikbaev.moneytransferapi.mapper;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhoneDataMapperImpl implements PhoneDataMapper {

    @Override
    public UserPhoneResponse toDto(PhoneData phoneData) {
        return UserPhoneResponse.builder()
                .userId(phoneData.getUser().getId())
                .userName(phoneData.getUser().getName())
                .idPhone(phoneData.getId())
                .phone(phoneData.getPhone())
                .build();
    }

    @Override
    public List<PhoneNumber> allToPhoneNumber(List<PhoneData> phoneData) {
        return phoneData.stream().map(e->new PhoneNumber(e.getPhone())).collect(Collectors.toList());
    }

    @Override
    public List<PhoneNumberResponse> allToPhoneNumberResponse(List<PhoneData> phoneData) {
        return phoneData.stream().map(e->new PhoneNumberResponse(e.getId(),e.getPhone())).collect(Collectors.toList());
    }
}
