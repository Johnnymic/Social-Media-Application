package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository

public class UserCriteriaRepository {

    private final EntityManager entityManager;


    private  final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<User>findAllUserWithFilter(UserPage userPage, UserSearchCriteria userSearchCriteria){

        CriteriaQuery<User>criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = getUserPredicated(userSearchCriteria,userRoot);
        criteriaQuery.where(predicate);
        setOrder(userPage,criteriaQuery,userRoot);
        TypedQuery<User> userTypedQuery = entityManager.createQuery(criteriaQuery);
        userTypedQuery.setFirstResult(userPage.getPageNo() * userPage.getPageSize());
        userTypedQuery.setMaxResults(userPage.getPageSize());
        Pageable pageable = getPageable(userPage);

        long userCount = getUserCount(predicate);

        return  new PageImpl<>(userTypedQuery.getResultList(),pageable,userCount);


    }

    private long getUserCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count( countRoot)).where( predicate);
        return  entityManager.createQuery(countQuery).getSingleResult();

    }

    private Pageable getPageable(UserPage userPage) {
        Sort sort = Sort.by(userPage.getSortDir(), userPage.getSortBy());
        return PageRequest.of(userPage.getPageNo(), userPage.getPageSize(), sort);
    }

    private void setOrder(UserPage userPage, CriteriaQuery<User> criteriaQuery, Root<User> userRoot) {
     if (userPage.getSortDir().equals(Sort.Direction.ASC)){
         criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(userPage.getSortBy())));
     }
        criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(userPage.getSortBy())));

    }

    private Predicate getUserPredicated(UserSearchCriteria userSearchCriteria, Root<User> userRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(userSearchCriteria.getUsername())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("username"), "%" + userSearchCriteria.getUsername() + "%" )
            );
        }
        if(Objects.nonNull(userSearchCriteria.getEmail())){
            predicates.add(
                    criteriaBuilder.like(userRoot.get("email"), "%" + userSearchCriteria.getEmail() + "%" )
            );
        }

        return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


}
