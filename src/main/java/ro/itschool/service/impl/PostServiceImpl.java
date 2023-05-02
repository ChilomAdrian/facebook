package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;
import ro.itschool.entity.SpringUser;
import ro.itschool.repository.PostRepo;
import ro.itschool.repository.SpringUserRepo;
import ro.itschool.service.PostService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final SpringUserRepo springUserRepo;

    @Override
    public List<Post> getPostsFromFollowedUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepo.findByUsername(authentication.getName());
        List<SpringUser> springUsers = springUserRepo.getFollowedUsers(loggedInUser.getId())
                .stream()
                .map(elem -> new SpringUser(
                        elem[0].toString(),
                        elem[1].toString(),
                        elem[2].toString(),
                        elem[3].toString(),
                        elem[4].toString()))
                .toList();
        return springUsers.stream()
                .map(user -> postRepo.findByUserId(user.getId()))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Post> post = postRepo.findById(id);
        post.ifPresent(p -> {
            p.setSpringUser(null);
            postRepo.save(p);
            postRepo.deleteByPostId(id);
            postRepo.deleteById(id);
        });
    }

    @Override
    public List<Post> getMyPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepo.findByUsername(authentication.getName());
        return postRepo.findByUserId(loggedInUser.getId());
    }

    @Override
    public void save(Post post) {
        post.setTimestamp(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepo.findByUsername(authentication.getName());
        post.setSpringUser(loggedInUser);
        postRepo.save(post);
    }

    @Override
    public void copyPost(Integer id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        optionalPost.ifPresent(p -> {
            Post post = new Post();
            post.setTimestamp(p.getTimestamp());
            post.setMessage(p.getMessage());
            save(post);
        });
    }

    @Override
    public List<Post> getPostsWithMention() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return postRepo.getPostsWithMention(authentication.getName());
    }

    @Override
    public void likePost(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepo.findByUsername(authentication.getName());
        postRepo.likePost(loggedInUser.getId(), postId);
    }

    @Override
    public void unlikePost(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepo.findByUsername(authentication.getName());
        postRepo.unlikePost(loggedInUser.getId(), postId);
    }
}
