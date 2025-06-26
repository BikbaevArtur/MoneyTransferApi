package ru.bikbaev.moneytransferapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.validation.EmailDataValidator;
import ru.bikbaev.moneytransferapi.dto.EmailUser;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.mapper.EmailDataMapper;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;

import java.util.List;

@Slf4j
@Service
public class EmailDataServiceImpl implements EmailDataService {

    private final EmailDataRepository repository;
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailDataValidator emailDataValidator;
    private final AccessValidator accessValidator;
    private final EmailDataMapper mapper;


    public EmailDataServiceImpl(EmailDataRepository repository, JwtService jwtService, UserService userService, EmailDataValidator emailDataValidator, AccessValidator accessValidator, EmailDataMapper mapper) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.emailDataValidator = emailDataValidator;
        this.accessValidator = accessValidator;
        this.mapper = mapper;
    }

    @Override
    public EmailData findByEmail(String email) {
        log.debug("Searching EmailData by email={}", email);
        return repository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("EmailUser " + email + " not found")
        );
    }

    @Override
    public List<EmailUser> findAllEmailByIdUser(Long idUser) {
        return mapper.allToEmail(repository.findByUserId(idUser));
    }

    @Override
    public List<EmailResponse> getEmailUser(String token) {
        Long userId = jwtService.extractUserId(token);
        return mapper.allToEmailResponse(repository.findByUserId(userId));
    }


    @Override
    public UserEmailResponse addEmail(String token, EmailUser email) {

        Long userId = jwtService.extractUserId(token);

        log.info("Adding email={} to user_id={}", email.getEmail(), userId);

        User user = userService.findEntityUserById(userId);

        emailDataValidator.validationEmailUniq(email);

        EmailData emailData = EmailData.builder().email(email.getEmail()).user(user).build();

        log.info("Successfully added email={} to user_id={}", email.getEmail(), userId);
        return mapper.toDto(repository.save(emailData));
    }

    @Override
    public UserEmailResponse updateEmail(String token, Long idEmail, EmailUser newEmail) {
        Long userId = jwtService.extractUserId(token);

        log.info("Updating email_id={} for user_id={} to new email={}", idEmail, userId, newEmail.getEmail());

        EmailData emailData = findByEntityById(idEmail);

        accessValidator.validateEmailOwnership(emailData, userId);

        emailDataValidator.validateEmailChange(emailData.getEmail(), newEmail.getEmail());
        emailDataValidator.validationEmailUniq(newEmail);

        emailData.setEmail(newEmail.getEmail());
        log.info("Successfully updated email_id={} for user_id={} to new email {}", idEmail, userId, newEmail.getEmail());
        return mapper.toDto(repository.save(emailData));
    }

    @Override
    public void deleteEmail(String token, Long idEmail) {
        Long userId = jwtService.extractUserId(token);

        log.info("Deleting email_id={} for user_id={}", idEmail, userId);

        EmailData emailData = findByEntityById(idEmail);

        accessValidator.validateEmailOwnership(emailData, userId);

        emailDataValidator.validateMinimumEmailCount(userId);

        log.info("Successfully deleted email_id={} for user_id={}", idEmail, userId);
        repository.deleteById(idEmail);

    }


    private EmailData findByEntityById(Long idEmail) {
        log.debug("Search EmailData by id={}",idEmail);
        return repository.findByIdEntity(idEmail).orElseThrow(
                () -> new EmailNotFoundException("EmailUser not found")
        );
    }


}
