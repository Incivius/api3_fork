package com.khali.api3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.khali.api3.domain.user.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByRegistration(String registration);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    public User findByEmail(String email);
}
