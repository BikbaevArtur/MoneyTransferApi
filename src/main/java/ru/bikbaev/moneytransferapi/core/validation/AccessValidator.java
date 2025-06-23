package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

public interface AccessValidator {

    /**
     * Проверка, принадлежит ли ресурс текущему пользователю.
     * Если пользователь не владелец — выбрасывается исключение AccessDeniedException.
     *
     * @param resourceOwnerId id владельца ресурса
     * @param currentUserId id текущего пользователя
     */
    void validateOwnership(Long resourceOwnerId, Long currentUserId);


    /**
     * Проверка, принадлежит ли email текущему пользователю.
     * Обёртка над validateOwnership.
     *
     * @param emailData объект email
     * @param userId id текущего пользователя
     */
    void validateEmailOwnership(EmailData emailData, Long userId);


    /**
     * Проверка, принадлежит ли телефон текущему пользователю.
     * Обёртка над validateOwnership.
     *
     * @param phoneData объект phone
     * @param userId id текущего пользователя
     */
    void validatePhoneOwnership(PhoneData phoneData, Long userId);

}
