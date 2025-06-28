package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.dto.request.PageableRequest;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.PageResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;

public interface UserService {

    /**
     * Поиск пользователя по id
     *
     * @param id id пользователя
     * @return Данные пользователя
     */
    UserResponseDto findById(Long id);

    /**
     * Получение профиля текущего пользователя
     *
     * @param userId id пользователя
     * @return Данные текущего пользователя
     */
    UserResponseDto getUserProfile(Long userId);

    /**
     * Поиск entity пользователя по id
     * Используется при внутренних вызовах, когда нужен именно entity-объект
     *
     * @param id id пользователя
     * @return entity User
     */
    User findEntityUserById(Long id);

    /**
     * Поиск пользователей по параметрам (дата рождения, email, телефон, префикс имени)
     * Используется постраничный поиск
     *
     * @param params   параметры фильтрации
     * @param pageable параметры пагинации (номер страницы, размер, сортировка)
     * @return Страница с найденными пользователями
     */
    PageResponse<UserResponseDto> searchUsers(UserParamsSearch params, PageableRequest pageable);


}
