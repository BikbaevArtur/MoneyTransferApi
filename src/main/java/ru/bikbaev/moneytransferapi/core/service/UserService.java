package ru.bikbaev.moneytransferapi.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.core.entity.User;

public interface UserService {

    /**
     * Поиск пользователя по id
     * @param id id пользователя
     * @return Данные пользователя
     */
    UserResponseDto findById(Long id);

    /**
     * Получение профиля текущего пользователя по токену авторизации
     * Парсится id пользователя из токена
     * @param token Токен авторизации
     * @return Данные текущего пользователя
     */
    UserResponseDto getUserProfile(String token);

    /**
     * Поиск entity пользователя по id
     * Используется при внутренних вызовах, когда нужен именно entity-объект
     * @param id id пользователя
     * @return entity User
     */
    User findEntityUserById(Long id);

    /**
     * Поиск пользователей по параметрам (дата рождения, email, телефон, префикс имени)
     * Используется постраничный поиск
     * @param params параметры фильтрации
     * @param pageable параметры пагинации (номер страницы, размер, сортировка)
     * @return Страница с найденными пользователями
     */
    Page<UserResponseDto> searchUsers(UserParamsSearch params, Pageable pageable);


}
