package ru.bikbaev.moneytransferapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.validation.EmailDataValidator;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.mapper.EmailDataMapper;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;

import java.util.List;

@Slf4j
@Service
public class EmailDataServiceImpl implements EmailDataService {

    private final EmailDataRepository repository;
    private final UserService userService;
    private final EmailDataValidator emailDataValidator;
    private final AccessValidator accessValidator;
    private final EmailDataMapper mapper;


    public EmailDataServiceImpl(EmailDataRepository repository, UserService userService, EmailDataValidator emailDataValidator, AccessValidator accessValidator, EmailDataMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.emailDataValidator = emailDataValidator;
        this.accessValidator = accessValidator;
        this.mapper = mapper;
    }

    @Override
    public EmailData findByEmail(String email) {
        log.debug("Searching EmailData by email={}", email);
        return repository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Email " + email + " not found")
        );
    }


    @Cacheable(value = "emails", key = "'findAllEmailByIdUser' + '_' + #idUser")
    @Override
    public List<Email> findAllEmailByIdUser(Long idUser) {
        return mapper.allToEmail(repository.findByUserId(idUser));
    }


    @Cacheable(value = "emails_response", key = "'getEmailUser' + '_' +  #userId")
    @Override
    public List<EmailResponse> getEmailUser(Long userId) {
        return mapper.allToEmailResponse(repository.findByUserId(userId));
    }



    @Caching(evict = {
            @CacheEvict(value = "emails_response", key = "'getEmailUser_' + #userId"),
            @CacheEvict(value = "emails", key = "'findAllEmailByIdUser_'+ #userId")
    })
    @Override
    public UserEmailResponse addEmail(Long userId, Email email) {

        log.info("Adding email={} to user_id={}", email.getEmail(), userId);

        User user = userService.findEntityUserById(userId);

        emailDataValidator.validationEmailUniq(email);

        EmailData emailData = EmailData.builder().email(email.getEmail()).user(user).build();

        log.info("Successfully added email={} to user_id={}", email.getEmail(), userId);
        return mapper.toDto(repository.save(emailData));
    }


    @Caching(evict = {
            @CacheEvict(value = "emails_response", key = "'getEmailUser_' + #userId"),
            @CacheEvict(value = "emails", key = "'findAllEmailByIdUser_'+ #userId")
    })
    @Override
    public UserEmailResponse updateEmail(Long userId, Long idEmail, Email newEmail) {
        log.info("Updating email_id={} for user_id={} to new email={}", idEmail, userId, newEmail.getEmail());

        EmailData emailData = findByEntityById(idEmail);

        accessValidator.validateEmailOwnership(emailData, userId);

        emailDataValidator.validateEmailChange(emailData.getEmail(), newEmail.getEmail());
        emailDataValidator.validationEmailUniq(newEmail);

        emailData.setEmail(newEmail.getEmail());
        log.info("Successfully updated email_id={} for user_id={} to new email {}", idEmail, userId, newEmail.getEmail());
        return mapper.toDto(repository.save(emailData));
    }

    
    @Caching(evict = {
            @CacheEvict(value = "emails_response", key = "'getEmailUser_' + #userId"),
            @CacheEvict(value = "emails", key = "'findAllEmailByIdUser_'+ #userId")
    })
    @Override
    public void deleteEmail(Long userId, Long idEmail) {
        log.info("Deleting email_id={} for user_id={}", idEmail, userId);

        EmailData emailData = findByEntityById(idEmail);

        accessValidator.validateEmailOwnership(emailData, userId);

        emailDataValidator.validateMinimumEmailCount(userId);

        log.info("Successfully deleted email_id={} for user_id={}", idEmail, userId);
        repository.deleteById(idEmail);

    }


    private EmailData findByEntityById(Long idEmail) {
        log.debug("Search EmailData by id={}", idEmail);
        return repository.findByIdEntity(idEmail).orElseThrow(
                () -> new EmailNotFoundException("Email not found")
        );
    }


}
