package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

import java.util.List;

public interface EmailDataService {

    /**
     * Поиск entity по email
     * @param email email
     * @return entity EmailData
     */
    EmailData findByEmail(String email);

    /**
     * Поиск всех Email по id пользователя
     * @param idUser id пользователя
     * @return список email
     */
    List<Email> findAllEmailByIdUser(Long idUser);

    /**
     * Получение всех email пользователя по токену авторизации
     * @param userId id пользователя
     * @return Данные о пользователе и список email
     */
    List<EmailResponse> getEmailUser(Long userId);

    /**
     * Добавление новго email пользователя
     * если такой email уже существует, выбрасывается исключения
     * @param userId id пользователя
     * @param email email
     * @return данные о пользователе, и данные нового email
     */
    UserEmailResponse addEmail(Long userId, Email email);

    /**
     * Обнавление существующего email по id email
     * Загружается email по id email, проверяется, принадлежит ли email пользователю -> выбрасывается исключение
     * Проверяется, существует ли новый email -> выбрасывается исключение
     * Обнавляется email
     * Сохраняется в базе данных
     * @param userId id пользователя
     * @param idEmail id обнавляемаго email
     * @param newEmail новый email
     * @return данные о пользователе и email
     */
    UserEmailResponse updateEmail(Long userId, Long idEmail, Email newEmail);

    /**
     * Удаление email у пользователя по email
     * Проверяется, принадлежит ли email пользователю
     * Проверяется минимальное количество email — должно быть хотя бы 1, иначе выбрасывается исключение
     * @param userId id пользователя
     * @param idEmail id Email
     */
    void deleteEmail(Long userId, Long idEmail);
}
