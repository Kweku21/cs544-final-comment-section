package comment.controller;

import comment.models.Comment;
import comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/{post_id}/comment")
public class CommentController {

    @Autowired
    OAuth2RestTemplate restTemplate;

    public String getUserName(){
        return restTemplate.getForObject("http://localhost:8088/user/id", String.class);
    }

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id, @PathVariable String post_id){
        return commentService.getComment(id);
    }

    @GetMapping("")
    public List<Comment> getPostComments(@PathVariable Long post_id){
        return commentService.getPostComment(post_id);
    }

    @GetMapping("/user")
    public List<Comment> getPostUserComments(@PathVariable Long post_id){
        String user_id = this.getUserName();
        return commentService.getPostUserComment(post_id, user_id);
    }

    @PostMapping("/create")
    public Comment createComment(@PathVariable Long post_id, @RequestBody Comment comment){
        String user_id = this.getUserName();
        comment.setPost_id(post_id);
        comment.setUser_id(user_id);
        return commentService.createComment(comment);
    }

    @PutMapping("/update/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable Long comment_id, @RequestBody Comment comment){
        String user_id = this.getUserName();
        commentService.checkUserOwnsComment(user_id, comment_id);
        return new ResponseEntity<>(commentService.updateComment(comment), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long comment_id, @PathVariable String post_id){
        String user_id = this.getUserName();
        commentService.checkUserOwnsComment(user_id, comment_id);
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
