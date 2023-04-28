package ro.itschool.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.itschool.entity.Post;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query(value = "Select * from post where user_id = ?", nativeQuery = true)
    static List<Post> findByUserId(Integer userId);

    @Query(value = "Select * from post where message LIKE %:keyword%", nativeQuery = true)
    List<Post> getPostsWithMention(String keyword);

    @Transactional
    @Modifying
    @Query(value = "delete from post where user_id = ?", nativeQuery = true)
    void deleteByUserId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO likes(user_id, post_id) VALUES (:userId, :postId)", nativeQuery = true)
    void likePost(@Param("userId") Integer userId, @Param("postId") Integer postId);

    @Transactional
    @Modifying
    @Query(value = "delete from likes where user_id = :userId AND post_id = :postId", nativeQuery = true)
    void unlikePost(@Param("userId") Integer userId, @Param("postId") Integer postId);

    @Transactional
    @Modifying
    @Query(value = "delete from likes where post_id = ?", nativeQuery = true)
    void deleteByPostId(Integer id);
}
