package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

import java.util.List;
import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData,Long> {

    @Query("SELECT e FROM EmailData e JOIN FETCH e.user WHERE e.email = :email")
    Optional<EmailData> findByEmail(String email);

    @Query("SELECT e FROM EmailData e JOIN FETCH e.user WHERE e.id = :id")
    Optional<EmailData> findByIdEntity(@Param("id") Long id);

    List<EmailData> findByUserId(Long userId);

    Long countByUserId(Long userId);

    boolean existsByEmail(String email);

}
