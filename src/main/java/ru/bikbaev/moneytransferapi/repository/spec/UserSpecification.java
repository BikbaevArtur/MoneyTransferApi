package ru.bikbaev.moneytransferapi.repository.spec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {

    public static Specification<User> filter(UserParamsSearch params) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (params.getDateOfBirthAfter() != null) {
                predicates.add(cb.greaterThan(root.get("dateOfBirth"), params.getDateOfBirthAfter()));
            }

            if (params.getNamePrefix() != null) {
                predicates.add(cb.like(root.get("name"), params.getNamePrefix() + "%"));
            }

            if (params.getEmail() != null) {
                Join<User, EmailData> emailJoin = root.join("emails", JoinType.INNER);
                predicates.add(cb.equal(emailJoin.get("email"), params.getEmail()));
                query.distinct(true);
            }

            if (params.getPhone() != null) {
                Join<User, PhoneData> phoneJoin = root.join("phones", JoinType.INNER);
                predicates.add(cb.equal(phoneJoin.get("phone"), params.getPhone()));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
