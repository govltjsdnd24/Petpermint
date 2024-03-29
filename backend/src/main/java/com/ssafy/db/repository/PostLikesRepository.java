package com.ssafy.db.repository;

import com.ssafy.db.entity.PostLikes;
import com.ssafy.db.entity.UserPost;
import com.ssafy.db.join.ReservationExpertUserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 파일 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    @Query(value = "select * from PostLikes p " +
            "where userId = :userId and postId = :postId" , nativeQuery = true)
    PostLikes find(String userId, int postId);

    @Transactional
    long deleteByPostIdAndUserId(
            @Param("postId") int postId,
            @Param("userId") String userId);
}