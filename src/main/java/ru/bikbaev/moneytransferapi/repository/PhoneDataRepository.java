package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

import java.util.List;
import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    @Query("SELECT p FROM PhoneData p JOIN FETCH p.user WHERE p.phone = :phone")
    Optional<PhoneData> findByPhone(String phone);

    @Query("SELECT p FROM PhoneData p JOIN FETCH p.user WHERE p.id = :id")
    Optional<PhoneData> findByIdEntity(@Param("id") Long id);

    List<PhoneData> findByUserId(Long userId);

    Long countByUserId(Long userId);

    boolean existsByPhone(String phone);
}
