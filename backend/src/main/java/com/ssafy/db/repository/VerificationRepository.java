package com.ssafy.db.repository;

import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Long countByUserIdAndVerificationCode(String userId, String verificationCode);
}