package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessTokenResponse;

public interface AuthUserService {

    /**
     * Метод для авторизации пользователя
     * @param request login=email/phone , password
     * @return AccessTokenResponse - токен авторизации
     */
    AccessTokenResponse authenticate(LoginRequest request);

    /**
     * метод для поиска в базе данных emailDate или phoneDate пользователя, который использовал
     * email или phone для входа в сервис, при вводе логина, определяет login=email || phone и
     * после нахождения нужной сущности email/phone в бд, загружает пользователя, маппит в userDetails
     * и оборачивает в LoginUserDate
     * @param login логин пользователя email/phone
     * @return Обертку с login и userDetails
     */
    LoginUserDate findByEmailOrPhone(String login);
}
