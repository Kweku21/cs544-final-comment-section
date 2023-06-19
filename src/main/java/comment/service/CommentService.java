package comment.service;

import comment.exception.UserNotOwnerException;
import comment.models.Comment;
import comment.repo.IComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private IComment commentRepo;

    public List<Comment> getComments(){
        return commentRepo.findAll();
    }

    public void checkUserOwnsComment(String user_id, Long comment_id){
        commentRepo.findByIdAndUser_id(comment_id, user_id).orElseThrow(
                () -> new UserNotOwnerException("User is not the owner of this blog")
        );
    }

    public List<Comment> getPostComment(Long post_id){ return commentRepo.findByPost_id(post_id);}

    public List<Comment> getPostUserComment(Long post_id, String user_id) {
        return commentRepo.findByPost_idAndUser_id(post_id, user_id);
    }

    public List<Comment> getUserComment(String id){
        return commentRepo.findByUser_id(id);
    }

    public Comment createComment(Comment comment){
        return commentRepo.save(comment);
    }

    public Comment updateComment(Comment comment){
        return commentRepo.save(comment);
    }

    public Comment getComment(Long id){
        return commentRepo.findById(id).get();
    }

    public void deleteComment(Long id){
        commentRepo.deleteById(id);
    }
}
