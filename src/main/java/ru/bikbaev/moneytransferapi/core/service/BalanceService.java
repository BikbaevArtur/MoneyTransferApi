package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserBalanceResponse;

public interface BalanceService {

    /**
     * метод для начисления процентов всех зарегеистрированных пользователей
     */
    void accruePercentageToBalances();

    /**
     * Метод для перевода денежных средств указанному пользователю
     * С токена парситься id юзера
     * Проверяется на положительность суммы, перевод выполняется не по тому же id -> иначе выбрасываются исключения
     * Происходит поиск аккаунта по id с токена отпарвителя, и поиск аккаунта получателя по id с request
     * Проверяется наличие денежных средств у отправителя -> иначе выбрасывается исключения
     * Вычитается сумма перевода в лицевом счету  отправителя, и начисляется получателю
     * Сохраняются новые данные аккаунтов отправителя и получателя
     * @param token Токен авторизации
     * @param request Данные о переводе
     * @return Информация о переводе в случае успеха
     */
    TransferMoneyResponse transferMoney(String token, TransferMoneyRequest request);


    /**
     * Проверка баланса лицевого счета пользователя
     * @param token Токен авторизации
     * @return Баланс пользователя
     */
    UserBalanceResponse getUserBalance(String token);
}
