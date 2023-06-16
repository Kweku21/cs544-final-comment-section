package comment.repo;

import comment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IComment extends JpaRepository<Comment, UUID> {

    @Query("SELECT c FROM Comment c WHERE c.user_id=:user_id")
    List<Comment> findByUser_id(@Param("user_id") UUID user_id);

    @Query("SELECT c FROM Comment c WHERE c.post_id=:post_id")
    List<Comment> findByPost_id(@Param("post_id") UUID post_id);

    @Query("SELECT c FROM Comment c WHERE c.user_id=:user_id and c.post_id=:post_id")
    List<Comment> findByPost_idAndUser_id(@Param("post_id")UUID post_id, @Param("user_id")UUID user_id);

    @Query("SELECT c FROM Comment c WHERE c.id=:id and c.user_id=:user_id")
    Optional<Comment> findByIdAndUser_id(@Param("id") UUID id, @Param("user_id")UUID user_id);
}
