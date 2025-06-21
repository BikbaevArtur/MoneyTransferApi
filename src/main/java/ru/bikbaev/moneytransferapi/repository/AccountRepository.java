package ru.bikbaev.moneytransferapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bikbaev.moneytransferapi.core.entity.Account;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a")
    Page<Account> findAllWithLock(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a join fetch a.user where a.user.id = :id")
    Optional<Account> findByIdUserWithLock(@Param("id") Long id);

}
