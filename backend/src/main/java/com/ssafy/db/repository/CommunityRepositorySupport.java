package com.ssafy.db.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.CommunityUpdatePutReq;
import com.ssafy.db.entity.*;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 커뮤니티 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
@Transactional
public class CommunityRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserPost quserPost= QUserPost.userPost;
    QPostFiles qpostFiles= QPostFiles.postFiles;
    QVideoRoom qVideoRoom= QVideoRoom.videoRoom;

    public Optional<UserPost> findDataByPostId(int postId) {
        UserPost userPost = jpaQueryFactory.select(quserPost).from(quserPost)
                .where(quserPost.postId.eq(postId)).fetchOne();
        if(userPost == null) return Optional.empty();
        return Optional.ofNullable(userPost);
    }

    public Optional<Long> updatePostByPostId(CommunityUpdatePutReq updatePutReq) {
        int postId=updatePutReq.getPostId();
        String content= updatePutReq.getContent();
        Long result = jpaQueryFactory.update(quserPost).set(quserPost.content,content)
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }

    public Optional<Long> decreaseLikeByPostId(int postId) {
        Long result = jpaQueryFactory.update(quserPost).set(quserPost.likes,quserPost.likes.castToNum(Integer.class).subtract(1))
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }

    public Optional<Long> isDeletePostByPostId(int postId) {
        Long result = jpaQueryFactory.update(quserPost).set(quserPost.isDelete,true)
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }

    public Optional<Long> deletePostByPostId(int postId) {
        Long result = jpaQueryFactory.delete(quserPost)
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }

    public Optional<Long> updateLikeByPostId(int postId) {
        Long result = jpaQueryFactory.update(quserPost).set(quserPost.likes,quserPost.likes.castToNum(Integer.class).add(1))
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }

    public Optional<Long> updateHitByPostId(int postId) {
        Long result = jpaQueryFactory.update(quserPost).set(quserPost.hits,quserPost.hits.castToNum(Integer.class).add(1))
                .where(quserPost.postId.eq(postId)).execute();
        if(result == 0) return Optional.empty();
        return Optional.ofNullable(result);
    }


    public Optional<Integer> findMaxPostId() {
        Integer max= jpaQueryFactory.select(quserPost.postId.max()).from(quserPost).fetchOne();
        if(max == null) return Optional.ofNullable(0);
        return Optional.ofNullable(max);
    }

    public List<VideoRoom> findVideos() {
        LocalDateTime nineHoursLater = LocalDateTime.now();

        List<VideoRoom> videoRoom = jpaQueryFactory.select(qVideoRoom).from(qVideoRoom)
                .where(
                        qVideoRoom.startTime.goe(String.valueOf(nineHoursLater)),
                        qVideoRoom.isDelete.ne(true)
                )
                .orderBy(qVideoRoom.startTime.asc())
                .fetch();
        return videoRoom;
    }

    public VideoRoom findCurrentVideo() {
        LocalDateTime nineHoursLater = LocalDateTime.now();
        nineHoursLater=nineHoursLater.plusHours(9);

        VideoRoom videoRoom = jpaQueryFactory.select(qVideoRoom).from(qVideoRoom)
                .where(
                        qVideoRoom.startTime.loe(String.valueOf(nineHoursLater)),
                        qVideoRoom.isDelete.ne(true)
                )
                .orderBy(qVideoRoom.startTime.desc())
                .limit(1)
                .fetchOne();
        return videoRoom;
    }



}
