package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bikbaev.moneytransferapi.entity.EmailData;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData,Long> {
    Optional<EmailData> findByEmail(String email);

    @Query("")
    Optional<EmailData> findByIdEntity(Long id);

    Long countByUserId(Long userId);

}
