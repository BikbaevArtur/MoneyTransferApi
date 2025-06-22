package ru.bikbaev.moneytransferapi.service;

import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.service.PhoneDataService;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.PhoneNotFoundException;
import ru.bikbaev.moneytransferapi.mapper.PhoneDataMapper;
import ru.bikbaev.moneytransferapi.repository.PhoneDataRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.validation.PhoneDataValidator;

import java.util.List;

@Service
public class PhoneDataServiceImpl implements PhoneDataService {

    private final PhoneDataRepository repository;
    private final JwtService jwtService;
    private final UserService userService;
    private final PhoneDataValidator phoneDataValidator;
    private final AccessValidator accessValidator;
    private final PhoneDataMapper mapper;

    public PhoneDataServiceImpl(PhoneDataRepository repository, JwtService jwtService, UserService userService, PhoneDataValidator phoneDataValidator, AccessValidator accessValidator, PhoneDataMapper mapper) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.phoneDataValidator = phoneDataValidator;
        this.accessValidator = accessValidator;
        this.mapper = mapper;
    }

    @Override
    public PhoneData findByPhone(String phone) {
        return repository.findByPhone(phone).orElseThrow(
                ()-> new PhoneNotFoundException("Phone "+ phone + " not found")
        );
    }

    @Override
    public List<PhoneNumber> findAllPhoneNumberByIdUser(Long idUser) {
        return mapper.allToPhoneNumber(repository.findByUserId(idUser));
    }

    @Override
    public List<PhoneNumberResponse> getPhoneNumberUser(String token) {
        Long userId = jwtService.extractUserId(token);
        return mapper.allToPhoneNumberResponse(repository.findByUserId(userId));
    }

    @Override
    public UserPhoneResponse addPhoneNumber(String token, PhoneNumber phoneNumber) {
        Long userId = jwtService.extractUserId(token);

        User user = userService.findEntityUserById(userId);

        phoneDataValidator.validationPhoneUniq(phoneNumber);

        PhoneData phoneData = PhoneData.builder().user(user).phone(phoneNumber.getPhoneNumber()).build();

        return mapper.toDto(repository.save(phoneData));
    }

    @Override
    public UserPhoneResponse updatePhoneNumber(String token, Long idPhoneNumber, PhoneNumber newPhoneNumber) {

        Long userId = jwtService.extractUserId(token);

        PhoneData phoneData = findEntityById(idPhoneNumber);

        accessValidator.validatePhoneOwnership(phoneData,userId);

        if(!newPhoneNumber.getPhoneNumber().equals(phoneData.getPhone())){
            phoneDataValidator.validationPhoneUniq(newPhoneNumber);
            phoneData.setPhone(newPhoneNumber.getPhoneNumber());
            return mapper.toDto(repository.save(phoneData));
        }

        return mapper.toDto(phoneData);
    }

    @Override
    public void deletePhoneNumber(String token, Long idPhoneNumber) {

        PhoneData phoneData = findEntityById(idPhoneNumber);

        Long userId = jwtService.extractUserId(token);

        accessValidator.validatePhoneOwnership(phoneData,userId);

        phoneDataValidator.validateMinimumPhoneCount(userId);

        repository.deleteById(idPhoneNumber);

    }


    private PhoneData findEntityById(Long id){
        return repository.findByIdEntity(id).orElseThrow(
                ()-> new PhoneNotFoundException("Phone not found")
        );
    }
}
