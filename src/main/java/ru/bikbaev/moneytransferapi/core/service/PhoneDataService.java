package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

import java.util.List;

public interface PhoneDataService {
    /**
     * Поиск entity по номеру телефона
     * @param phone номер телефона
     * @return entity PhoneData
     */
    PhoneData findByPhone(String phone);

    /**
     * Поиск всех номеров телефона по id пользователя
     * @param idUser id пользователя
     * @return список номеров телефона
     */
    List<PhoneNumber> findAllPhoneNumberByIdUser(Long idUser);

    /**
     * Получение всех номеров телефона пользователя по токену авторизации
     * @param token Токен авторизации
     * @return Данные о пользователе и список его номеров телефона
     */
    List<PhoneNumberResponse> getPhoneNumberUser(String token);

    /**
     * Добавление нового номера телефона пользователю
     * Используется токен авторизации для получения пользователя
     * Проверяется, существует ли уже такой номер — выбрасывается исключение, если да
     * @param token Токен авторизации
     * @param phoneNumber новый номер телефона
     * @return Данные о пользователе и новом номере
     */
    UserPhoneResponse addPhoneNumber(String token,PhoneNumber phoneNumber);

    /**
     * Обновление существующего номера телефона по id
     * Парсится id пользователя по токену авторизации
     * Загружается номер телефона по id, проверяется принадлежность пользователю
     * Проверяется, существует ли новый номер телефона — выбрасывается исключение
     * Обновляется и сохраняется номер телефона
     * @param token Токен авторизации
     * @param idPhoneNumber id обновляемого номера
     * @param newPhoneNumber новый номер телефона
     * @return Данные о пользователе и обновлённом номере
     */
    UserPhoneResponse updatePhoneNumber(String token,Long idPhoneNumber, PhoneNumber newPhoneNumber);

    /**
     * Удаление номера телефона пользователя
     * Парсится id пользователя по токену
     * Проверяется принадлежность номера пользователю
     * Проверяется минимальное количество номеров — должно быть хотя бы 1, иначе выбрасывается исключение
     * @param token Токен авторизации
     * @param idPhoneNumber id удаляемого номера
     */
    void deletePhoneNumber(String token,Long idPhoneNumber);

}
