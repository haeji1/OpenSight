package org.example.b104.oauth2.repository;

import org.example.b104.domain.user.entity.User;
import org.example.b104.oauth2.UserProfile;
import org.example.b104.oauth2.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUserId(Long userId);

}
