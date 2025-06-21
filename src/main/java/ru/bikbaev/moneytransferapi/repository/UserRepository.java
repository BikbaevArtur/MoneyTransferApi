package ru.bikbaev.moneytransferapi.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bikbaev.moneytransferapi.core.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    @NonNull
    @EntityGraph(attributePaths = {"phones", "emails"})
    Page<User> findAll(Specification<User> spec, @NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths ={"account"})
    Optional<User> findById(@NonNull Long id);

}
