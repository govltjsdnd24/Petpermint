package com.ssafy.db.repository;

import com.ssafy.db.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * 커뮤니티 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface CommentRepository extends JpaRepository<PostComment, Long> {

}