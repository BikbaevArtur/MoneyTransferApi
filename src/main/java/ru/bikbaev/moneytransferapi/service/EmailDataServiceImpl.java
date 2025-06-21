package ru.bikbaev.moneytransferapi.service;

import org.springframework.stereotype.Service;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.request.Email;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotFoundException;
import ru.bikbaev.moneytransferapi.mapper.EmailDataMapper;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.validation.EmailDataValidator;

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
        return repository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("Email " + email + " not found")
        );
    }


    @Override
    public UserEmailResponse addEmail(String token, Email email) {

        Long userId = jwtService.extractUserId(token);

        User user = userService.findEntityUserById(userId);

        emailDataValidator.validationEmailUniq(email);

        EmailData emailData = EmailData.builder().email(email.getEmail()).user(user).build();

        return mapper.toDto(repository.save(emailData));
    }

    @Override
    public UserEmailResponse updateEmail(String token, Long idEmail, Email newEmail) {
        Long userid = jwtService.extractUserId(token);

        EmailData emailData = findByEntityById(idEmail);

        accessValidator.validateEmailOwnership(emailData, userid);

        if (!newEmail.getEmail().equals(emailData.getEmail())) {
            emailDataValidator.validationEmailUniq(newEmail);
            emailData.setEmail(newEmail.getEmail());
            return mapper.toDto(repository.save(emailData));
        }

        return mapper.toDto(emailData);
    }

    @Override
    public void deleteEmail(String token, Long idEmail) {
        EmailData emailData = findByEntityById(idEmail);

        Long userId = jwtService.extractUserId(token);

        accessValidator.validateEmailOwnership(emailData, userId);

        emailDataValidator.validateMinimumEmailCount(userId);

        repository.deleteById(idEmail);

    }


    private EmailData findByEntityById(Long idEmail) {
        return repository.findByIdEntity(idEmail).orElseThrow(
                () -> new EmailNotFoundException("Email not found")
        );
    }


}
