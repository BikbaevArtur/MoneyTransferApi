package ru.bikbaev.moneytransferapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.bikbaev.moneytransferapi.MoneyTransferApiApplication;
import ru.bikbaev.moneytransferapi.core.entity.Account;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.AccountNotFoundException;
import ru.bikbaev.moneytransferapi.dto.ErrorResponse;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;
import ru.bikbaev.moneytransferapi.integration.testUtil.TestAuthUtil;
import ru.bikbaev.moneytransferapi.integration.testUtil.TestDataInitializerUtil;
import ru.bikbaev.moneytransferapi.repository.AccountRepository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoneyTransferApiApplication.class)
@AutoConfigureMockMvc
public class TransferMoneyControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Autowired
    private TestDataInitializerUtil dataInitializer;
    @Autowired
    private TestAuthUtil testAuthUtil;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;


    Account getUpdateAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Test account not found")
        );
    }


    @Test
    @Transactional
    @Rollback
    public void transferMoneyTest() throws Exception {
        User fromUser = dataInitializer.createUserDB(1);
        Account fromAccount = dataInitializer.createAccountDB(fromUser);
        EmailData fromUserEmail = dataInitializer.createEmailDB(1, fromUser);
        LoginRequest fromUserLogin = new LoginRequest(fromUserEmail.getEmail(), "password");
        BigDecimal fromAccountBalance = fromAccount.getBalance();

        User toUser = dataInitializer.createUserDB(2);
        Account toAccount = dataInitializer.createAccountDB(toUser);
        BigDecimal toAccountBalance = toAccount.getBalance();


        BigDecimal amount = BigDecimal.valueOf(100);
        TransferMoneyRequest request = new TransferMoneyRequest(toUser.getId(), amount);

        String token = testAuthUtil.loginAndGetToken(fromUserLogin);


        MvcResult result = mockMvc.perform(
                post("/api/v1/transfers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();

        TransferMoneyResponse transferMoneyResponse = objectMapper.readValue(responseBody, TransferMoneyResponse.class);

        entityManager.flush();
        entityManager.clear();

        Account updateAccountFromUser = getUpdateAccount(fromAccount.getId());
        Account updateAccountToUser = getUpdateAccount(toAccount.getId());


        BigDecimal expectedUpdateFromAccount = fromAccountBalance.subtract(request.getAmount());
        BigDecimal expectedUpdateToAccount = toAccountBalance.add(request.getAmount());

        Assertions.assertEquals(fromUser.getName(), transferMoneyResponse.getFromUserName());
        Assertions.assertEquals(toUser.getName(), transferMoneyResponse.getToUserName());
        Assertions.assertEquals(0, transferMoneyResponse.getAmountTransfer().compareTo(request.getAmount()));
        Assertions.assertEquals(0, transferMoneyResponse.getCurrentBalance().compareTo(expectedUpdateFromAccount));

        Assertions.assertEquals(0, updateAccountFromUser.getBalance().compareTo(expectedUpdateFromAccount));
        Assertions.assertEquals(0, updateAccountToUser.getBalance().compareTo(expectedUpdateToAccount));

    }


    @Test
    @Transactional
    @Rollback
    public void transferMoneyTest_whenTransferMoneyToSelf() throws Exception {
        User fromUser = dataInitializer.createUserDB(1);
        Account fromAccount = dataInitializer.createAccountDB(fromUser);
        EmailData fromUserEmail = dataInitializer.createEmailDB(1, fromUser);
        LoginRequest fromUserLogin = new LoginRequest(fromUserEmail.getEmail(), "password");
        BigDecimal fromAccountBalance = fromAccount.getBalance();

        BigDecimal amount = BigDecimal.valueOf(100);
        TransferMoneyRequest request = new TransferMoneyRequest(fromUser.getId(), amount);

        String token = testAuthUtil.loginAndGetToken(fromUserLogin);

        MvcResult result = mockMvc.perform(
                post("/api/v1/transfers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpect(status().isBadRequest()).andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

        entityManager.flush();
        entityManager.clear();


        Account updateAccountFromUser = getUpdateAccount(fromAccount.getId());


        Assertions.assertEquals(0, updateAccountFromUser.getBalance().compareTo(fromAccountBalance));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Cannot transfer money to the same account.", errorResponse.getMessage());
    }


    @Test
    @Transactional
    @Rollback
    public void transferMoneyTest_whenTransferAmountIsNegative() throws Exception {
        User fromUser = dataInitializer.createUserDB(1);
        Account fromAccount = dataInitializer.createAccountDB(fromUser);
        EmailData fromUserEmail = dataInitializer.createEmailDB(1, fromUser);
        LoginRequest fromUserLogin = new LoginRequest(fromUserEmail.getEmail(), "password");
        BigDecimal fromAccountBalance = fromAccount.getBalance();


        User toUser = dataInitializer.createUserDB(2);
        Account toAccount = dataInitializer.createAccountDB(toUser);
        BigDecimal toAccountBalance = toAccount.getBalance();


        BigDecimal amount = BigDecimal.valueOf(-100);
        TransferMoneyRequest request = new TransferMoneyRequest(toUser.getId(), amount);

        String token = testAuthUtil.loginAndGetToken(fromUserLogin);


        MvcResult result = mockMvc.perform(
                post("/api/v1/transfers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpect(status().isBadRequest()).andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

        entityManager.flush();
        entityManager.clear();


        Account updateAccountFromUser = getUpdateAccount(fromAccount.getId());
        Account updateAccountToUser = getUpdateAccount(toAccount.getId());


        Assertions.assertEquals(0, updateAccountFromUser.getBalance().compareTo(fromAccountBalance));
        Assertions.assertEquals(0, updateAccountToUser.getBalance().compareTo(toAccountBalance));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Transfer amount must be greater than zero.", errorResponse.getMessage());

    }


    @Test
    @Transactional
    @Rollback
    public void transferMoneyTest_accountNotFound() throws Exception {
        User fromUser = dataInitializer.createUserDB(1);
        Account fromAccount = dataInitializer.createAccountDB(fromUser);
        EmailData fromUserEmail = dataInitializer.createEmailDB(1, fromUser);
        LoginRequest fromUserLogin = new LoginRequest(fromUserEmail.getEmail(), "password");
        BigDecimal fromAccountBalance = fromAccount.getBalance();


        BigDecimal amount = BigDecimal.valueOf(100);
        TransferMoneyRequest request = new TransferMoneyRequest(2L, amount);

        String token = testAuthUtil.loginAndGetToken(fromUserLogin);


        MvcResult result = mockMvc.perform(
                post("/api/v1/transfers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpect(status().isNotFound()).andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

        entityManager.flush();
        entityManager.clear();


        Account updateAccountFromUser = getUpdateAccount(fromAccount.getId());


        Assertions.assertEquals(0, updateAccountFromUser.getBalance().compareTo(fromAccountBalance));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, errorResponse.getHttpStatus());
        Assertions.assertEquals("Account not found", errorResponse.getMessage());

    }


    @Test
    @Transactional
    @Rollback
    public void transferMoneyTest_whenTransferAmountExceedsBalance() throws Exception {
        User fromUser = dataInitializer.createUserDB(1);
        Account fromAccount = dataInitializer.createAccountDB(fromUser, BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        EmailData fromUserEmail = dataInitializer.createEmailDB(1, fromUser);
        LoginRequest fromUserLogin = new LoginRequest(fromUserEmail.getEmail(), "password");
        BigDecimal fromAccountBalance = fromAccount.getBalance();


        User toUser = dataInitializer.createUserDB(2);
        Account toAccount = dataInitializer.createAccountDB(toUser);
        BigDecimal toAccountBalance = toAccount.getBalance();


        BigDecimal amount = BigDecimal.valueOf(200);
        TransferMoneyRequest request = new TransferMoneyRequest(toUser.getId(), amount);

        String token = testAuthUtil.loginAndGetToken(fromUserLogin);


        MvcResult result = mockMvc.perform(
                post("/api/v1/transfers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpect(status().isConflict()).andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);

        entityManager.flush();
        entityManager.clear();


        Account updateAccountFromUser = getUpdateAccount(fromAccount.getId());
        Account updateAccountToUser = getUpdateAccount(toAccount.getId());


        Assertions.assertEquals(0, updateAccountFromUser.getBalance().compareTo(fromAccountBalance));
        Assertions.assertEquals(0, updateAccountToUser.getBalance().compareTo(toAccountBalance));
        Assertions.assertEquals(HttpStatus.CONFLICT, errorResponse.getHttpStatus());
        Assertions.assertEquals("Insufficient funds for transfer.", errorResponse.getMessage());
    }


}
