package com.tdcollab.spoterly.repositories;

import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

    @Query(
            "SELECT u FROM UserEntity u WHERE " +
            "(:username IS null OR u.username = :username) AND " +
            "(:surname IS null OR u.firstname = :firstname) AND " +
            "(:name IS null OR u.lastname = :lastname)")
    List<UserEntity> findByAttribute(
            @Param("username") String username,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname);
}
