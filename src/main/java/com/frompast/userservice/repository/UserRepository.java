package com.frompast.userservice.repository;

import com.frompast.userservice.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usr, Long> {
    Optional<Usr> findUserByEmail(String email);
}
