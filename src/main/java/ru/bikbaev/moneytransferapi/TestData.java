package ru.bikbaev.moneytransferapi;

import org.springframework.context.annotation.Profile;
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("test")
public class TestData {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private EmailDataRepository emailDataRepository;
    private PhoneDataRepository phoneDataRepository;


    private final int COUNT_TEST_USER = 2;

    public TestData(UserRepository userRepository, AccountRepository accountRepository, EmailDataRepository emailDataRepository, PhoneDataRepository phoneDataRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.emailDataRepository = emailDataRepository;
        this.phoneDataRepository = phoneDataRepository;


        for (int i = 0; i < COUNT_TEST_USER; i++) {

            User user = User.builder()
                    .name(nameUser)
                    .dateOfBirth(getRandomDate())
                    .password(encoder.encode(password))
                    .build();
            userList.add(user);

            Account account = Account.builder()
                    .user(user)
                    .balance(balance)
                    .initialBalance(initial)
                    .build();
            accountList.add(account);

            PhoneData phoneData = PhoneData.builder()
                    .user(user)
                    .phone(String.valueOf(phone + i))
                    .build();
            phoneDataList.add(phoneData);

            StringBuilder emailBuild = new StringBuilder(email);
            emailBuild.append(i);
            emailBuild.append("@mail.ru");
            EmailData emailData = EmailData.builder()
                    .user(user)
                    .email(emailBuild.toString())
                    .build();
            emailDataList.add(emailData);

        }
    }

    String nameUser = "Test User";
    String password = "password";
    String email = "test";
    Long phone = 79870000000L;
    BigDecimal balance = BigDecimal.valueOf(20000);
    BigDecimal initial = balance;


    List<User> userList = new ArrayList<>();
    List<Account> accountList = new ArrayList<>();
    List<EmailData> emailDataList = new ArrayList<>();
    List<PhoneData> phoneDataList = new ArrayList<>();


    @PostConstruct
    void createTestUsers() {
        userList = userRepository.saveAll(userList);
        accountList = accountRepository.saveAll(accountList);
        emailDataList = emailDataRepository.saveAll(emailDataList);
        phoneDataList = phoneDataRepository.saveAll(phoneDataList);

    }


    @PreDestroy
    void deleteTestUsers() {
        accountRepository.deleteAll(accountList);
        userRepository.deleteAll(userList);
    }

    private LocalDate getRandomDate() {
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(2010, 1, 1);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

        return startDate.plusDays(randomDays);
    }
}
