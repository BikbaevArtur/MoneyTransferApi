package ru.bikbaev.moneytransferapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.PhoneNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.PhoneDataService;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.validation.PhoneDataValidator;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.mapper.PhoneDataMapper;
import ru.bikbaev.moneytransferapi.repository.PhoneDataRepository;

import java.util.List;

@Slf4j
@Service
public class PhoneDataServiceImpl implements PhoneDataService {

    private final PhoneDataRepository repository;
    private final UserService userService;
    private final PhoneDataValidator phoneDataValidator;
    private final AccessValidator accessValidator;
    private final PhoneDataMapper mapper;

    public PhoneDataServiceImpl(PhoneDataRepository repository, UserService userService, PhoneDataValidator phoneDataValidator, AccessValidator accessValidator, PhoneDataMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.phoneDataValidator = phoneDataValidator;
        this.accessValidator = accessValidator;
        this.mapper = mapper;
    }


    @Override
    public PhoneData findByPhone(String phone) {
        log.debug("Searching PhoneData by phone={}", phone);
        return repository.findByPhone(phone).orElseThrow(
                () -> new PhoneNotFoundException("Phone " + phone + " not found")
        );
    }


    @Cacheable(value = "phone_numbers", key = "'findAllPhoneNumberByIdUser' + '_' +#idUser")
    @Override
    public List<PhoneNumber> findAllPhoneNumberByIdUser(Long idUser) {
        List<PhoneData> phones = repository.findByUserId(idUser);
        phoneDataValidator.validatePhoneNumberIsNotEmpty(phones);
        return mapper.allToPhoneNumber(phones);
    }


    @Cacheable(value = "phone_numbers_response", key = "'getPhoneNumberUser' + '_' +#userId")
    @Override
    public List<PhoneNumberResponse> getPhoneNumberUser(Long userId) {
        return mapper.allToPhoneNumberResponse(repository.findByUserId(userId));
    }


    @Caching(evict = {
            @CacheEvict(value = "phone_numbers", key = "'findAllPhoneNumberByIdUser_'+ #userId"),
            @CacheEvict(value = "phone_numbers_response", key = "'getPhoneNumberUser_' + #userId")

    })
    @Override
    public UserPhoneResponse addPhoneNumber(Long userId, PhoneNumber phoneNumber) {

        log.info("Adding phone={} to user_id={}", phoneNumber.getPhoneNumber(), userId);

        User user = userService.findEntityUserById(userId);

        phoneDataValidator.validationPhoneUniq(phoneNumber);

        PhoneData phoneData = PhoneData.builder().user(user).phone(phoneNumber.getPhoneNumber()).build();

        log.info("Successfully added phone={} to user_id={}", phoneNumber.getPhoneNumber(), userId);
        return mapper.toDto(repository.save(phoneData));
    }


    @Caching(evict = {
            @CacheEvict(value = "phone_numbers", key = "'findAllPhoneNumberByIdUser_'+ #userId"),
            @CacheEvict(value = "phone_numbers_response", key = "'getPhoneNumberUser_' + #userId")

    })
    @Override
    public UserPhoneResponse updatePhoneNumber(Long userId, Long idPhoneNumber, PhoneNumber newPhoneNumber) {
        log.info("Updating phone_id={} for user_id={} to new phone={}", idPhoneNumber, userId, newPhoneNumber.getPhoneNumber());

        PhoneData phoneData = findEntityById(idPhoneNumber);

        accessValidator.validatePhoneOwnership(phoneData, userId);

        phoneDataValidator.validatePhoneChange(phoneData.getPhone(), newPhoneNumber.getPhoneNumber());
        phoneDataValidator.validationPhoneUniq(newPhoneNumber);

        phoneData.setPhone(newPhoneNumber.getPhoneNumber());
        log.info("Successfully updated phone_id={} for user_id={} to new phone {}", idPhoneNumber, userId, newPhoneNumber.getPhoneNumber());
        return mapper.toDto(repository.save(phoneData));
    }


    @Caching(evict = {
            @CacheEvict(value = "phone_numbers", key = "'findAllPhoneNumberByIdUser_'+ #userId"),
            @CacheEvict(value = "phone_numbers_response", key = "'getPhoneNumberUser_' + #userId")

    })
    @Override
    public void deletePhoneNumber(Long userId, Long idPhoneNumber) {
        log.info("Deleting phone_id={} for user_id={}", idPhoneNumber, userId);

        PhoneData phoneData = findEntityById(idPhoneNumber);

        accessValidator.validatePhoneOwnership(phoneData, userId);

        phoneDataValidator.validateMinimumPhoneCount(userId);

        log.info("Successfully deleted phone_id={} for user_id={}", idPhoneNumber, userId);
        repository.deleteById(idPhoneNumber);

    }


    private PhoneData findEntityById(Long id) {
        log.debug("Search PhoneData by id={}", id);
        return repository.findByIdEntity(id).orElseThrow(
                () -> new PhoneNotFoundException("Phone not found")
        );
    }
}
