package com.practice.authorization.repositories;

import com.practice.authorization.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    Optional<Session> findByUserIdAndId(Long userId,Long id);
}
