package comment.controller;

import comment.models.Comment;
import comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vi/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable UUID id){
        return commentService.getComment(id);
    }

    @GetMapping("/post/{post_id}")
    public List<Comment> getPostComments(@PathVariable UUID post_id){
        return commentService.getPostComment(post_id);
    }

    @GetMapping("/{post_id}/user/{user_id}")
    public List<Comment> getPostUserComments(@PathVariable UUID post_id, @PathVariable UUID user_id){
        return commentService.getPostUserComment(post_id, user_id);
    }

    @PostMapping("/create/{post_id}/user/{user_id}")
    public Comment createComment(@PathVariable UUID post_id, @PathVariable UUID user_id, @RequestBody Comment comment){
        comment.setPost_id(post_id);
        comment.setUser_id(user_id);
        return commentService.createComment(comment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable UUID id, @RequestBody Comment comment){
        commentService.checkUserOwnsComment(comment.getUser_id(), id);
        return new ResponseEntity<>(commentService.updateComment(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID id, @PathVariable UUID user_id){
        commentService.checkUserOwnsComment(user_id, id);
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
