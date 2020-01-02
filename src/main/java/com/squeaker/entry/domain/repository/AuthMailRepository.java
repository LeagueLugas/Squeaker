package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthMailRepository extends JpaRepository<EmailAuth, Object> {
    EmailAuth findByAuthEmail(String mail);
    EmailAuth findByAuthEmailAndAuthCode(String email, String code);
}
