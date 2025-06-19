package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.moneytransferapi.entity.PhoneData;

public interface PhoneDataRepository extends JpaRepository<PhoneData,Long> {
}
