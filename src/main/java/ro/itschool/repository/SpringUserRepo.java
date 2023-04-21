package ro.itschool.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.itschool.entity.SpringUser;

import java.util.List;

public interface SpringUserRepo extends JpaRepository<SpringUser, Integer> {

    SpringUser findByUsername(String username);

    @Query(value = "SELECT * FROM spring_user u WHERE u.username LIKE %:keyword% OR u.first_name LIKE %:keyword% OR " +
            "u.last_name LIKE %:keyword% ",
            nativeQuery = true)
    List<SpringUser> searchUser(String keyword);

    @Transactional
    @Modifying
    @Query(value = """
            SELECT b.user_id, b.username, b.last_name, b.first_name, b.email FROM spring_user a INNER JOIN follow
            ON a.user_id = follow.follower_id
            INNER JOIN spring_user b
            ON b.user_id = follow.followed_id
            WHERE a.user_id = ?;
               """, nativeQuery = true)
    List<Object[]> getFollowedUsers(Integer id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE followed_id = ?", nativeQuery = true)
    void deleteFollower(Integer id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE follower_id = :followerId and followed_id = :followedId", nativeQuery = true)
    void deleteFromFollowTable(@Param("followerId") Integer followerId, @Param("followedId") Integer followedId);
}
