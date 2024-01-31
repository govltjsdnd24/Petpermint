package com.ssafy.api.service;

import com.ssafy.api.request.*;
import com.ssafy.db.entity.PostComment;
import com.ssafy.db.entity.PostFiles;
import com.ssafy.db.repository.*;
import com.ssafy.db.entity.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommunityRepositorySupport communityRepositorySupport;
    @Autowired
    CommentRepositorySupport commentRepositorySupport;

    @Autowired
    PostFilesRepository postFilesRepository;

    @Override
    public UserPost writePost(CommunityWritePostReq writeInfo) {

        UserPost userPost = new UserPost();
        userPost.setUserId(writeInfo.getUserId());
        userPost.setContent(writeInfo.getContent());


        //userPost.setImages(writeInfo.getImages());
        return communityRepository.save(userPost);

    }

    @Override
    public UserPost dataPost(int postId) {
        UserPost userPost = communityRepositorySupport.findDataByPostId(postId).get();
        return userPost;
    }

    @Override
    public Long updatePost(CommunityUpdatePutReq updateInfo) {
        Long result=communityRepositorySupport.updatePostByPostId(updateInfo).get();
        return result;
    }

    @Override
    public Long isDeletePost(int postId) {
        Long result=communityRepositorySupport.isDeletePostByPostId(postId).get();
        return result;
    }

    @Override
    public Long deletePost(int postId) {
        Long result=communityRepositorySupport.deletePostByPostId(postId).get();
        return result;
    }

    @Override
    public PostComment createComment(CommentCreatePostReq createInfo) {
        PostComment postComment= new PostComment();
        postComment.setPostId(createInfo.getPostId());
        postComment.setUserId(createInfo.getUserId());
        postComment.setContent(createInfo.getContent());
        return commentRepository.save(postComment);
    }

    @Override
    public PostComment dataComment(int postId) {
        PostComment postComment = commentRepositorySupport.findCommentByPostId(postId).get();
        return postComment;
    }

    @Override
    public Long isDeleteComment(CommentDeleteDeleteReq deleteInfo) {
        Long result=commentRepositorySupport.isDeleteCommentByDeleteInfo(deleteInfo).get();
        return result;
    }

    @Override
    public Long deleteComment(CommentDeleteDeleteReq deleteInfo) {
        Long result=commentRepositorySupport.deleteCommentByDeleteInfo(deleteInfo).get();
        return result;
    }

    @Override
    public Long increaseLike(int postId) {
        Long result=communityRepositorySupport.updateLikeByPostId(postId).get();
        return result;
    }

    @Override
    public Long increaseHit(int postId) {
        Long result=communityRepositorySupport.updateHitByPostId(postId).get();
        return result;
    }

    @Override
    public int findPostCount() {
        int count=communityRepositorySupport.findMaxPostId().get();
        return count+1;
    }

    @Override
    @Transactional

    public int writeUrl(int postId, List<String> fileNames,List<String> urls) {
        int i;
        for(i=0;i<urls.size(); i++) {
            PostFiles postFiles = new PostFiles();
            postFiles.setPostId(postId);
            postFiles.setUrl(urls.get(i));
            postFiles.setFileName(fileNames.get(i));
            try {
                System.out.println("posing: "+ postFiles.getFileName());

                postFilesRepository.save(postFiles);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        return i+1;
    }

    @Override
    public List<String> getUrl(int postId) {
        List<PostFiles> postFiles=postFilesRepository.findByPostId(postId);
        List<String> urls=new ArrayList<String>();
        for(PostFiles postFile: postFiles)
            urls.add(postFile.getUrl());
        return urls;
    }


    @Override
    @Transactional
    public int deleteUrl(int postId) {
        int delete_result= postFilesRepository.deleteByPostId(postId);
        return delete_result;
    }


}