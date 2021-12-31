package com.example.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("postService")
public class PostService {
    @Autowired
    private PostDao postDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private AttachDao attachDao;


    // 게시글 정보를 등록하다.
    public int registerPost(PostVo post) {
        int no = this.postDao.insertPost(post);
        return no;
    }

    //게시글 전체 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<PostVo> retrieveAllPosts(int boardNo) {
        return postDao.selectAllPosts(boardNo);
    }

    // 게시글 상세정보를 조회하다.
    public PostVo retrievePost(int no) {
        postDao.upHitcount(no);
        return postDao.selectPost(no);
    }

    //게시글 정보를 변경하다.
    public void modifyPost(PostVo post) {
        this.postDao.updatePost(post);
    }

    public void removePostAttach(int attachNo) {
        attachDao.deletePostAttach(attachNo);
    }

    public void removePost(int postNo) {
        attachDao.deleteAttachbyPost(postNo);
        reviewDao.deleteReview(postNo);
        postDao.deletePost(postNo);
    }

    public List<Integer> retrieveBoardNo() {
        return this.postDao.selectBoardNo();
    }

    public List<String> retrieveBoardName() {
        return this.postDao.selectBoardName();
    }

    public PostVo retrieveDetailBoard(int postNo) {
        return this.postDao.selectDetailPost(postNo);
    }

    public List<PostVo> retrieveMyPosts(int MemNo) {
        return this.postDao.selectMyPosts(MemNo);
    }
}






















