package ru.bikbaev.moneytransferapi.integration.testUtil;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.entity.Account;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.repository.AccountRepository;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;
import ru.bikbaev.moneytransferapi.repository.PhoneDataRepository;
import ru.bikbaev.moneytransferapi.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TestDataInitializerUtil {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataInitializerUtil(UserRepository userRepository, AccountRepository accountRepository, EmailDataRepository emailDataRepository, PhoneDataRepository phoneDataRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.emailDataRepository = emailDataRepository;
        this.phoneDataRepository = phoneDataRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<TestDataDTO> createFullDataUsers(int countUsers) {
        List<TestDataDTO> dataList = new ArrayList<>();

        List<User> userList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        List<PhoneData> phoneDataList = new ArrayList<>();
        List<EmailData> emailDataList = new ArrayList<>();

        for (int i = 0; i < countUsers; i++) {
            User user = createUser(i);
            userList.add(user);

            Account account = createAccount(user);
            accountList.add(account);

            PhoneData phoneData = createPhone(i, user);
            phoneDataList.add(phoneData);

            EmailData emailData = createEmail(i, user);
            emailDataList.add(emailData);

            TestDataDTO dataDTO = TestDataDTO.builder()
                    .user(user)
                    .account(account)
                    .emailData(emailData)
                    .phoneData(phoneData)
                    .build();

            dataList.add(dataDTO);
        }

        userRepository.saveAll(userList);
        accountRepository.saveAll(accountList);
        phoneDataRepository.saveAll(phoneDataList);
        emailDataRepository.saveAll(emailDataList);

        return dataList;
    }

    public User createUserDB(int numberUser) {
        return userRepository.save(createUser(numberUser));
    }

    public Account createAccountDB(User user) {
        return accountRepository.save(createAccount(user));
    }

    public Account createAccountDB(User user,BigDecimal initialBalance, BigDecimal balance) {
        return accountRepository.save(createAccount(user,initialBalance,balance));
    }

    public EmailData createEmailDB(int numberUser, User user) {
        return emailDataRepository.save(createEmail(numberUser, user));
    }

    public PhoneData createPhoneDB(int numberUser, User user) {
        return phoneDataRepository.save(createPhone(numberUser, user));
    }


    private User createUser(int numberUser) {

        return User.builder()
                .name("Test User" + numberUser)
                .dateOfBirth(getRandomDate())
                .password(passwordEncoder.encode("password"))
                .build();
    }


    private Account createAccount(User user) {
        return Account.builder()
                .user(user)
                .initialBalance(BigDecimal.valueOf(10000))
                .balance(BigDecimal.valueOf(10000))
                .build();
    }

    private Account createAccount(User user,BigDecimal initialBalance, BigDecimal balance) {
        return Account.builder()
                .user(user)
                .initialBalance(initialBalance)
                .balance(balance)
                .build();
    }



    private PhoneData createPhone(int numberUser, User user) {
        long phone = 79870000000L + (long) numberUser;
        return PhoneData.builder()
                .user(user)
                .phone(String.valueOf(phone))
                .build();
    }


    private EmailData createEmail(int numberUser, User user) {
        return EmailData.builder()
                .user(user)
                .email("test" + numberUser + "@mail.ru")
                .build();
    }


    private LocalDate getRandomDate() {
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(2010, 1, 1);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

        return startDate.plusDays(randomDays);
    }


    @Data
    @Builder
    public static class TestDataDTO {
        private User user;
        private Account account;
        private EmailData emailData;
        private PhoneData phoneData;
    }
}
