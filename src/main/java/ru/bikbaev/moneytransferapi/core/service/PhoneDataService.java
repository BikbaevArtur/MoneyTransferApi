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
     * Получение всех номеров телефона пользователя по user id
     * @param userId id пользователя
     * @return Данные о пользователе и список его номеров телефона
     */
    List<PhoneNumberResponse> getPhoneNumberUser(Long userId);

    /**
     * Добавление нового номера телефона пользователю
     * Проверяется, существует ли уже такой номер — выбрасывается исключение, если да
     * @param userId id пользователя
     * @param phoneNumber новый номер телефона
     * @return Данные о пользователе и новом номере
     */
    UserPhoneResponse addPhoneNumber(Long userId,PhoneNumber phoneNumber);

    /**
     * Обновление существующего номера телефона по id
     * Загружается номер телефона по id, проверяется принадлежность пользователю
     * Проверяется, существует ли новый номер телефона — выбрасывается исключение
     * Обновляется и сохраняется номер телефона
     * @param userId id пользователя
     * @param idPhoneNumber id обновляемого номера
     * @param newPhoneNumber новый номер телефона
     * @return Данные о пользователе и обновлённом номере
     */
    UserPhoneResponse updatePhoneNumber(Long userId,Long idPhoneNumber, PhoneNumber newPhoneNumber);

    /**
     * Удаление номера телефона пользователя
     * Проверяется принадлежность номера пользователю
     * Проверяется минимальное количество номеров — должно быть хотя бы 1, иначе выбрасывается исключение
     * @param userId id пользователя
     * @param idPhoneNumber id удаляемого номера
     */
    void deletePhoneNumber(Long userId,Long idPhoneNumber);

}
