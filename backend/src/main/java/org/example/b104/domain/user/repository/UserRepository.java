package org.example.b104.domain.user.repository;

import org.example.b104.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauthId);
    User findByEmail(String email);

    User findByUniqueFaceId(String uniqueFaceId);
    Optional<User> findByEmailAndPhoneNumber(String email, String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
