package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikbaev.moneytransferapi.entity.EmailData;

public interface EmailDataRepository extends JpaRepository<EmailData,Long> {

}
