package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikbaev.moneytransferapi.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("select distinct a from Account a join fetch a.user where a.user.id =: idUser")
    Optional<Account> findByIdUser(@Param("idUser") Long idUser);


}
