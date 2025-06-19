package ru.bikbaev.moneytransferapi.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.entity.EmailData;
import ru.bikbaev.moneytransferapi.entity.PhoneData;
import ru.bikbaev.moneytransferapi.entity.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(UserParamsSearch params, Pageable pageable) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(params.getDateOfBirthAfter() != null){
                predicates.add(criteriaBuilder.greaterThan(root.get("dateOfBirth"), params.getDateOfBirthAfter()));
            }

            if(params.getNamePrefix() != null){
                predicates.add(criteriaBuilder.like(root.get("name"), params.getNamePrefix()+"%"));
            }

            if(params.getEmail() != null){
                Join<User, EmailData> emailJoin = root.join("emails", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(emailJoin.get("email"),params.getEmail()));
                query.distinct(true);
            }

            if(params.getPhone() != null){
                Join<User, PhoneData> phoneJoin = root.join("phones",JoinType.INNER);
                predicates.add(criteriaBuilder.equal(phoneJoin.get("phone"),params.getPhone()));
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
